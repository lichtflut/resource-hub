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
package de.lichtflut.rb.webck;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.apache.wicket.Localizer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.DomainManager;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.config.QueryPath;
import de.lichtflut.rb.webck.config.QueryServicePathBuilder;

/**
 * <p>
 * Base class for all wicket-tests. This class does:
 * <ul>
 * <li>
 * add SpringBean annotation support in class under test</li>
 * <li>
 * enable you to inject mock objects (services are injected by default)</li>
 * <li>
 * instantiates {@link WicketTester}</li>
 * <li>
 * lets you do further configuration by overriding <code>setUpTest</code> -method</li>
 * </ul>
 * The {@link Locale} is set to {@link Locale#ENGLISH} by default.
 * </p>
 * Created: Sep 19, 2012
 * 
 * @author Ravi Knox
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class RBWebTest {

	/**
	 * The name for the test domain.
	 */
	protected static final String DOMAIN_NAME = "testDomain";

	/**
	 * The uri for the test domain.
	 */
	protected static final ResourceID DOMAIN_ID = new SimpleResourceID("http://glasnost.lichtflut.de/test#", DOMAIN_NAME);

	// ------------------------------------------------------

	private ApplicationContextMock applicationContextMock;

	protected Localizer localizer;

	protected WicketTester tester;

	// --------- Services -----------------------------------

	@Mock
	protected SemanticNetworkService networkService;

	@Mock
	protected Conversation conversation;

	@Mock
	protected ResourceLinkProvider resourceLinkProvider;

	@Mock
	protected EntityManager entityManager;

	@Mock
	protected SchemaManager schemaManager;

	@Mock
	protected SecurityService securityService;

	@Mock
	protected TypeManager typeManager;

	@Mock
	protected DomainManager domainManager;

	@Mock
	protected QueryServicePathBuilder pathBuilder;

	@Mock
	protected ServiceContext serviceContext;

	@Mock
	protected FileService fileService;

	@Mock
	protected AuthModule authModule;


	// ------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		applicationContextMock = new ApplicationContextMock();
		MockApplication application = new MockApplication(){
			@Override
			public org.apache.wicket.Session newSession(final org.apache.wicket.request.Request request, final org.apache.wicket.request.Response response) {
				return new RBWebSession(request);
			}

			@Override
			public void init() {
				// Overwrite so that SpringComponentInjector(this) will not be called from super!
				// Instead it will be added a few lines below.
				getComponentInstantiationListeners().add(new SpringComponentInjector(this, applicationContextMock));
			}
		};
		tester = new WicketTester(application);
		//		tester.getApplication().getComponentInstantiationListeners()
		//		.add(new SpringComponentInjector(tester.getApplication(), applicationContextMock));
		tester.getSession().setLocale(Locale.ENGLISH);
		registerMocks();
		setupTest();
	}

	public void assertRenderedPanel(final Class<? extends Panel> panelClass, final String path) {
		if (!getLastRenderedPanel(path).getClass().isAssignableFrom(panelClass)) {
			assertThat(panelClass.getSimpleName(), equalTo(getLastRenderedPanel(path).getClass().getSimpleName()));
		}
	}

	// ------------------------------------------------------

	/**
	 * Override this method to add some custom configuration.
	 */
	protected void setupTest() {
	}

	/**
	 * Add mock-objects as a replacement for {@link SpringBean}s.
	 * 
	 * @param name - The name of the mock bean.
	 * @param mock - The mock object.
	 */
	protected void addMock(final String name, final Object mock) {
		applicationContextMock.putBean(name, mock);
	}

	/**
	 * @return the {@link ApplicationContextMock}
	 */
	protected ApplicationContextMock getApplicationContextMock() {
		return applicationContextMock;
	}

	protected void simulatePathbuilder() {
		QueryPath path = new QueryPath("http://example.org/");
		when(pathBuilder.create(anyString())).thenReturn(path);

		when(pathBuilder.queryEntities(anyString(), anyString())).thenReturn("some entities");
		when(pathBuilder.queryClasses(anyString(), anyString())).thenReturn("some entities");
		when(pathBuilder.queryProperties(anyString(), anyString())).thenReturn("some entities");
		when(pathBuilder.queryResources(anyString(), anyString())).thenReturn("some entities");
		when(pathBuilder.queryUsers(anyString())).thenReturn("some entities");
	}

	// ------------------------------------------------------

	private void registerMocks() {
		addMock("networkService", networkService);
		addMock("conversation", conversation);
		addMock("resourceLinkProvider", resourceLinkProvider);
		addMock("entityManager", entityManager);
		addMock("pathbuilder", pathBuilder);
		addMock("serviceContext", serviceContext);
		addMock("typeManager", typeManager);
		addMock("fileService", fileService);
		addMock("authModule", authModule);
		addMock("domainManager", domainManager);
		addMock("schemaManager", schemaManager);
		addMock("securityService", securityService);
	}

	private Panel getLastRenderedPanel(final String path) {
		return (Panel) tester.getLastRenderedPage().get(path);
	}

}
