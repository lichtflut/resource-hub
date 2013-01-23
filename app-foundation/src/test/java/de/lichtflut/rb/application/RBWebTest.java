/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.Localizer;
import org.apache.wicket.Session;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
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
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.DomainManager;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.config.QueryServicePathBuilder;

/**
 * <p>
 * Base class for Brouker Web-Tests
 * </p>
 * Created: Jan 23, 2013
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

	protected WicketTester tester;

	protected Localizer localizer;

	private ApplicationContextMock applicationContextMock;

	// --------- Services -----------------------------------

	@Mock
	protected AuthenticationService authenticationService;

	@Mock
	protected AuthModule authModule;

	@Mock
	protected DomainManager domainManager;

	@Mock
	protected DomainOrganizer domainOrganizer;

	@Mock
	protected EntityManager entityManager;

	@Mock
	protected FileService fileService;

	@Mock
	protected Conversation conversation;

	@Mock
	protected QueryServicePathBuilder pathBuilder;

	@Mock
	protected ResourceLinkProvider resourceLinkProvider;

	@Mock
	protected SchemaManager schemaManager;

	@Mock
	protected SecurityService securityService;

	@Mock
	protected SemanticNetworkService networkService;

	@Mock
	protected ServiceContext serviceContext;

	@Mock
	protected TypeManager typeManager;

	@Mock
	protected ViewSpecificationService viewSpecificationService;

	// ------------- SetUp & tearDown -----------------------

	@Before
	public void setUp() throws Exception {
		applicationContextMock = new ApplicationContextMock();
		earlyInitialize();
		RBApplication application = new RBApplication() {
			@Override
			public Session newSession(final Request request, final Response response) {
				return getRBWebSession(request);
			}

			@Override
			public void init() {
				// Overwrite so that SpringComponentInjector(this) will not be called from super!
				// Instead it will be added a few lines below.
				getComponentInstantiationListeners().add(new SpringComponentInjector(this, applicationContextMock));
			}
		};
		registerMocks();
		tester = new WicketTester(application);
		tester.getSession().setLocale(Locale.ENGLISH);
		localizer = tester.getApplication().getResourceSettings().getLocalizer();
		setupTest();
	}

	// ------------------------------------------------------

	protected void earlyInitialize() {

	}

	/**
	 * Override this method to add some custom configuration.
	 */
	protected void setupTest() {
	}


	protected void assertRenderedPanel(final Class<? extends Component> panelClass, final String path) {
		if (!getLastRenderedComponent(path).getClass().isAssignableFrom(panelClass)) {
			assertThat(panelClass.getSimpleName(), equalTo(getLastRenderedComponent(path).getClass().getSimpleName()));
		}
	}

	protected void initNeccessaryPageData() {
		RBDomain domain = createTestDomain();
		when(authModule.getDomainManager()).thenReturn(domainManager);
		when(serviceContext.getDomain()).thenReturn(domain.getQualifiedName().toURI());
		when(pathBuilder.queryEntities(anyString(), anyString())).thenReturn("some entities");
		when(domainManager.findDomain(anyString())).thenReturn(domain);
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

	/**
	 * @param request
	 * @return the {@link RBWebSession}
	 */
	protected Session getRBWebSession(final Request request) {
		return new RBWebSession(request);
	}

	// ------------------------------------------------------

	private void registerMocks() {
		addMock("authenticationService", authenticationService);
		addMock("authModule", authModule);
		addMock("conversation", conversation);
		addMock("domainManager", domainManager);
		addMock("domainOrganizer", domainOrganizer);
		addMock("entityManager", entityManager);
		addMock("fileService", fileService);
		addMock("networkService", networkService);
		addMock("pathbuilder", pathBuilder);
		addMock("resourceLinkProvider", resourceLinkProvider);
		addMock("schemaManager", schemaManager);
		addMock("securityService", securityService);
		addMock("serviceContext", serviceContext);
		addMock("typeManager", typeManager);
		addMock("viewSpecificationService", viewSpecificationService);
	}

	private Component getLastRenderedComponent(final String path) {
		return tester.getLastRenderedPage().get(path);
	}

	/**
	 * @return a customized {@link RBDomain} for testing purposes.
	 */
	private RBDomain createTestDomain() {
		RBDomain domain = new RBDomain(DOMAIN_ID.getQualifiedName());
		domain.setDescription("This domain ist for testing purposes only");
		domain.setDomainNamespace(DOMAIN_ID.toURI());
		domain.setName(DOMAIN_NAME);
		domain.setTitle(DOMAIN_NAME);
		return domain;
	}

}
