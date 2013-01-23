/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.custom;

import org.junit.Test;

import de.lichtflut.rb.application.RBWebTest;

/**
 * <p>
 * Testclass for {@link RequestAccountPage}.
 * </p>
 * Created: Jan 23, 2013
 *
 * @author Ravi Knox
 */
public class RequestAccountPageTest extends RBWebTest {

	/**
	 * Test method for {@link de.lichtflut.rb.application.custom.RequestAccountPage#RequestAccountPage()}.
	 */
	@Test
	public void testRequestAccountPage() {
		tester.startPage(RequestAccountPage.class);

		tester.assertRenderedPage(RequestAccountPage.class);
	}

}
