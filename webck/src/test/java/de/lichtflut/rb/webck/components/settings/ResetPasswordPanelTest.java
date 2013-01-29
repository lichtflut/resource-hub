/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.settings;

import org.junit.Test;

import de.lichtflut.rb.webck.RBWebTest;

/**
 * <p>
 * Testclass for {@link ResetPasswordPanel}.
 * </p>
 * Created: Jan 23, 2013
 *
 * @author Ravi Knox
 */
public class ResetPasswordPanelTest extends RBWebTest {

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.settings.ResetPasswordPanel#ResetPasswordPanel(java.lang.String)}.
	 */
	@Test
	public void testResetPasswordPanel() {
		ResetPasswordPanel panel = new ResetPasswordPanel("panel");

		tester.startComponentInPage(panel);

		assertRenderedPanel(ResetPasswordPanel.class, "panel");
	}

}
