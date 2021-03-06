/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.rest.api;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.StringKeyObjectValueIgnoreCaseMultivaluedMap;
import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBCrypt;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.rest.api.models.generate.ObjectFactory;
import de.lichtflut.rb.rest.api.models.generate.SystemDomain;
import de.lichtflut.rb.rest.api.models.generate.SystemIdentity;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Stack;

/**
 * <p>
 *  This is the base class for all REST service-dependent test cases.
 *  Each class have to inherit from this one.
 * 
 *  Please note the following conventions and behaviors:
 *  There is no need to add a tear-down oder setup method.
 *  The database will be destroyed after each test.
 *  There is a default behavior in loading fixtures before each test
 *  after calling the initTestCase Method.
 *  A fixture file, named "fixtures.rdf.xml" is expected under the following path:
 *  src/test/resources/fixtures/TestClassName/fixtures.rdf.xml.
 *  If this file is not present, the default src/test/resources/fixtures/TestClassName/fixtures.rdf.xml is taken.
 * </p>
 * 
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created May 10, 2012
 */
@RunWith(CustomizedSpringClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@Component
public abstract class TestBase extends junit.framework.TestCase {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass().getName());

	private RestConnector connector;

	private String authToken;

	@Autowired
	private TestServiceProvider provider;

	@Autowired
	private AuthModule authmodule;

	private final Stack<SystemIdentity> identityStack = new Stack<SystemIdentity>();

	private SystemIdentity currentIdentity;

	private SystemDomain currentDomain;

	private static final String FIXTURE_FILE = "fixtures.rdf.xml";
	private static final String FIXTURE_DIRECTORY = "/fixtures";


	/**
	 * Override and implement this method
	 * to do some initialization stuff like registering or activate
	 * an an user or a system domain.
	 */
	public abstract void initTestCase();

	@Before
	public void init(){
		//Initializing gate
		initTestCase();
		loadFixtures();
	}


	/**
	 * 
	 */
	@Override
	@After
	public void tearDown() {
		try {
			connector.tearDown();
			getProvider().closeGate();
			Thread.sleep(1000);
			//Lets delete all systemIdentities
			while(!identityStack.empty()){
				deleteSystemUser(identityStack.pop());
			}
		} catch (Exception any) {
			throw new RuntimeException(any);
		}
	}

	public WebResource getWebResource(final boolean addAuthToken) {
		WebResource resource = connector.resource();
		if (addAuthToken) {
			// Get Token
			String token = getAuthToken();
			if (token != null) {
				resource = resource.queryParam("TOKEN", token);
			}
		}
		return resource;
	}



	/**
	 * 
	 */
	private void loadFixtures() {
		SemanticGraphIO semanticIO = new RdfXmlBinding();
		// declare fixture reference obj
		File fixture = null;
		// Lets see if there is a special fixture file present
		if (!getClass().getName().equals(TestBase.class.getName())) {
			fixture = getFileFromClasspath(FIXTURE_DIRECTORY + File.separatorChar  + getClass().getName() + File.separatorChar + FIXTURE_FILE);
			// If this file does not exists, that the reference to null and try
			// to load the default one
			if (fixture!=null && !fixture.exists()) {
				fixture = null;
			}
		}
		if (fixture == null) {
			fixture = getFileFromClasspath(FIXTURE_DIRECTORY + File.separatorChar + FIXTURE_FILE);
		}
		if (fixture!=null && fixture.exists()) {
			try {
				FileInputStream stream = new FileInputStream(fixture);
				getProvider().getConversation().attach(semanticIO.read(stream));
				return;
			} catch (Exception e) {
				throw new RuntimeException("could not load fixtures " + (fixture==null ?  "" : fixture.getAbsolutePath()), e);
			}
		}
		throw new RuntimeException("could not load fixtures " + (fixture==null ? "" : fixture.getAbsolutePath()));
	}


	public WebResource getWebResource() {
		return getWebResource(false);
	}


	/**
	 * Checks if the required webservice resource is well protected.
	 * Only if the AuthToken and the referenced user is well known, a success is expected
	 * @param resource
	 * @param method
	 * @return
	 */
	public boolean doesTokenAuthWorksAsIntended(WebResource resource,
			final HttpMethod method) {
		Response rsp = null;
		//If no token is given, lets check if the token login does work as specified
		if (resource.getURI().getQuery() == null || !resource.getURI().getQuery().contains("TOKEN")) {

			//If there is no token given, the request should fail
			rsp = requestEndpoint(resource, method);
			if (rsp.getStatus() != Status.UNAUTHORIZED.getStatusCode()) {
				return false;
			}
			// Next step try it with a wrong token, the request should also fail
			rsp = requestEndpoint(
					resource.queryParam("TOKEN", getAuthToken() + "x"), method);
			if (rsp.getStatus() != Status.UNAUTHORIZED.getStatusCode()) {
				return false;
			}
			// Next step: Try it with the right token, should work
			resource = resource.queryParam("TOKEN", getAuthToken());

		}
		/*Lets assume that given token is correct - The request should succeed when:
		 * 	- The user does have the required privileges
		 */
		rsp = requestEndpoint(resource, method);
		if (rsp.getStatus() == Status.UNAUTHORIZED.getStatusCode()) {
			return false;
		}

		return true;
	}

	private Response requestEndpoint(final WebResource resource, final HttpMethod method) {
		Response rsp = null;
		try {
			final StringBuffer s = new StringBuffer();
			switch (method) {
			case GET:
				s.append(resource.get(String.class));
				break;
			case POST:
				s.append(resource.post(String.class));
				break;
			case DELETE:
				s.append(resource.delete(String.class));
				break;
			case PUT:
				s.append(resource.put(String.class));
				break;
			}
			rsp = new Response() {

				@Override
				public Object getEntity() {
					return s.toString();
				}

				@Override
				public int getStatus() {
					return Status.OK.getStatusCode();
				}

				@Override
				public MultivaluedMap<String, Object> getMetadata() {
					return new StringKeyObjectValueIgnoreCaseMultivaluedMap();

				}

			};
		} catch (UniformInterfaceException e) {
			final ClientResponse cRsp = e.getResponse();
			rsp = new Response() {

				@Override
				public Object getEntity() {
					return cRsp.getEntity(Object.class);
				}

				@Override
				public int getStatus() {
					return cRsp.getStatus();
				}

				@Override
				public MultivaluedMap<String, Object> getMetadata() {
					return new StringKeyObjectValueIgnoreCaseMultivaluedMap();

				}
			};
		}
		return rsp;
	}

	/**
	 * 
	 */
	@Override
	@Before
	public void setUp() {
		connector = new RestConnector();
	}

	/**
	 * Default Constructor
	 */
	public TestBase() {
		// Inititalize default root system domain
		currentDomain = new SystemDomain();
		currentDomain.setDescription("root");
		currentDomain.setDomainIdentifier("root");
		currentDomain.setDomainIdentifier("root");
	}

	/**
	 * @return the authToken of the current user
	 */
	protected String getAuthToken() {
		if (authToken == null) {
			WebResource webResource = connector.resource().path("auth");
			String rsp = webResource
					.accept(MediaType.TEXT_PLAIN)
					.entity(new ObjectFactory()
					.createSystemIdentity(currentIdentity))
					.type(MediaType.APPLICATION_JSON).post(String.class);
			assertNotNull(rsp, "Authtoken has to be not null");
			setAuthToken(URLEncoder.encode(rsp));
		}
		return authToken;
	}

	/**
	 * @param authToken
	 *            the authToken to set
	 */
	private void setAuthToken(final String authToken) {
		this.authToken = authToken;
	}

	/**
	 * @return the provider
	 */
	protected TestServiceProvider getProvider() {
		return provider;
	}

	protected AuthModule getAuthModule(){
		return authmodule;
	}

	/**
	 * 
	 * @param identity
	 */
	public void setCurrentSystemUser(final SystemIdentity identity) {
		this.authToken = null;
		this.currentIdentity = identity;
	}

	/**
	 * The current domain which is used for operations like loading fixtures, registering a user
	 * or generating an auth-token but
	 * also to determine the current serviceProvider and its context.
	 * If no current domain is set, the default root domain will be used
	 * @param domain - the given domain
	 */
	public void setCurrentSystemDomain(final SystemDomain domain) {
		this.currentDomain = domain;
	}

	private void deleteSystemUser(final SystemIdentity identity){
		final RBUser rbUser = new RBUser(
				new SimpleResourceID().getQualifiedName());
		rbUser.setEmail(identity.getId());
		rbUser.setUsername(identity.getUsername());
		authmodule.getUserManagement().deleteUser(rbUser);
	}

	public void registerSystemUser(final SystemIdentity identity) {
		final RBUser rbUser = new RBUser(
				new SimpleResourceID().getQualifiedName());
		rbUser.setEmail(identity.getId());
		rbUser.setUsername(identity.getUsername());
		try {
			authmodule.getUserManagement().registerUser(rbUser,
					RBCrypt.encryptWithRandomSalt(identity.getPassword()),
					currentDomain.getDomainIdentifier());
		} catch (RBAuthException e) {
			throw new RuntimeException("Wasnt able to create the user: " + identity.toString(), e);
		}
		identityStack.push(identity);
	}

	public void registerDomain(final SystemDomain domain) {
		RBDomain rbDomain = new RBDomain();
		rbDomain.setDescription(domain.getDescription());
		rbDomain.setName(domain.getTitle());
		rbDomain.setTitle(domain.getTitle());
		if(authmodule.getDomainManager().findDomain(domain.getDomainIdentifier())==null){
			authmodule.getDomainManager().registerDomain(rbDomain);
		}
	}

	private File getFileFromClasspath(final String path){
		URL url = getClass().getResource(path);
		if(url!=null){
			return new File(url.getFile());
		}
		return null;
	}

	// ----------------------------------------------------

	protected List<ResourceNode> findResourcesByType(final Conversation conversation, final ResourceID type) {
		final Query query = conversation.createQuery();
		query.addField(RDF.TYPE, type);
		return query.getResult().toList(2000);
	}

}
