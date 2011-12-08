/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import org.apache.wicket.util.tester.WicketTester;

import junit.framework.TestCase;

/**
 * This test assures the propper rendering of the Login.class.
 *
 * Created: Aug 9, 2011
 *
 * @author Ravi Knox
 */
public class LoginTest extends TestCase {
	private WicketTester tester;

	@Override
	public void setUp(){
		tester = new WicketTester();
	}

	//--------------------------------------

    /**
     *
     */
	@SuppressWarnings("unused")
	public void testRenderMyPage(){
		//start and render the test page
		tester.startPage(LoginTestPage.class);

		//assert rendered page class
		tester.assertRenderedPage(LoginTestPage.class);

		//assert all components were rendered
		tester.assertContains("wicket:id=\"login\"");
		tester.assertContains("wicket:id=\"id\"");
		tester.assertContains("wicket:id=\"password\"");
		tester.assertContains("wicket:id=\"submitButton\"");
		tester.assertContains("wicket:id=\"registerLink\"");


		String body = tester.getLastResponse().getDocument();
	}
}
