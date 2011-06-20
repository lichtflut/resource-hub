/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import junit.framework.TestCase;

import org.apache.wicket.util.tester.WicketTester;

/**
 * Simple test using the WicketTester.
 */
public class TestHomePage extends TestCase{
	private WicketTester tester;

	@Override
	public void setUp(){
		tester = new WicketTester(new RBApplication());
	}

	/**
	 *
	 */
	public void testRenderMyPage(){
		//start and render the test page
		tester.startPage(RSPage.class);

		//assert rendered page class
		tester.assertRenderedPage(RSPage.class);

		//assert rendered label component
		tester.assertLabel("title", "Resource Schema");
	}
}
