/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import junit.framework.TestCase;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;

/**
 * Simple test using the WicketTester.
 */
public class TestHomePage extends TestCase{
	private WicketTester tester;

	@Override
	public void setUp(){

		final WebsampleApplication app = new WebsampleApplication() {
			@Override
			protected void init() {
				initWebsampleApp();
			}
		};
		
		ApplicationContextMock appctx=new ApplicationContextMock();
		app.getComponentInstantiationListeners().add(new SpringComponentInjector(app, appctx ));
		
		tester = new WicketTester(app);
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
