/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import junit.framework.TestCase;

import org.apache.wicket.util.tester.WicketTester;

/**
 * This test assures the propper rendering of the BasicLogin.class.
 *
 * Created: Aug 9, 2011
 *
 * @author Ravi Knox
 */
public class BasicRegistrationTest extends TestCase {
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
		tester.startPage(BasicRegistrationTestPage.class);

		//assert rendered page class
		tester.assertRenderedPage(BasicRegistrationTestPage.class);

		//assert all components were rendered
		tester.assertContains("wicket:id=\"registration\"");
		tester.assertContains("wicket:id=\"id\"");
		tester.assertContains("wicket:id=\"password\"");

		String body = tester.getLastResponse().getDocument();
	}
}
