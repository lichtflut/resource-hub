/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.arastreju.sge.ModelingConversation;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.DomainManager;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
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
public abstract class AbstractRBWebTest {

	private ApplicationContextMock applicationContextMock;

	protected WicketTester tester;

	// --------- Services -----------------------------------

	@Mock
	protected SemanticNetworkService networkService;

	@Mock
	protected ModelingConversation conversation;

	@Mock
	protected ResourceLinkProvider resourceLinkProvider;

	@Mock
	protected EntityManager entityManager;

	@Mock
	protected SchemaManager schemaManager;

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

		tester = new WicketTester();
		tester.getApplication().getComponentInstantiationListeners()
		.add(new SpringComponentInjector(tester.getApplication(), applicationContextMock));
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
	protected abstract void setupTest();

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
	}

	private Panel getLastRenderedPanel(final String path) {
		return (Panel) tester.getLastRenderedPage().get(path);
	}

}
