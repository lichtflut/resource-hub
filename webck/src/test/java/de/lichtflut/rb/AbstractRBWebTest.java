/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb;

import java.util.Locale;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;

/**
 * <p>
 * Base class for all wicket-tests.
 * This class does:
 * <ul><li>
 * add SpringBean annotation support in class under test
 * </li><li>
 * enable you to inject mock objects
 * </li><li>
 * instantiates {@link WicketTester}
 * </li><li>
 * lets you do further configuration by overriding <code>setUpTest</code> -method
 * </li>
 * </ul>
 * The {@link Locale} is set to {@link Locale#ENGLISH} by default.
 * </p>
 * Created: Sep 19, 2012
 *
 * @author Ravi Knox
 */
public abstract class AbstractRBWebTest {

	private ApplicationContextMock applicationContextMock;

	private WicketTester tester;

	// ------------------------------------------------------

	@Before
	public void setUp() throws Exception {
		applicationContextMock = new ApplicationContextMock();

		tester = new WicketTester();
		tester.getApplication().getComponentInstantiationListeners().add(new SpringComponentInjector(tester.getApplication(), applicationContextMock));
		tester.getSession().setLocale(Locale.ENGLISH);
		setupTest();
	}

	/**
	 * Override this method to add some custom configuration.
	 */
	protected abstract void setupTest();

	/**
	 * Add mock-objects as a replacement for {@link SpringBean}s.
	 * @param name  - The name of the mock bean.
	 * @param mock  - The mock object.
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
	 * @return the {@link WicketTester}
	 */
	protected WicketTester getTester() {
		return tester;
	}
}
