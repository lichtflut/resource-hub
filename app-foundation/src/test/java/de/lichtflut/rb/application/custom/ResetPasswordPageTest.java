/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.custom;

import org.junit.Test;

import de.lichtflut.rb.application.RBWebTest;


/**
 * <p>
 * Tesclass for {@link ResetPasswordPage}.
 * </p>
 * Created: Jan 23, 2013
 *
 * @author Ravi Knox
 */
public class ResetPasswordPageTest extends RBWebTest{

	@Test
	public void testResetPasswordPage(){

		tester.startPage(ResetPasswordPage.class);

		tester.assertRenderedPage(ResetPasswordPage.class);
	}
}
