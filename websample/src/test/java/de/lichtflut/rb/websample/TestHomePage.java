/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.websample;

import junit.framework.TestCase;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Ignore;

import de.lichtflut.rb.websample.DashboardPage;
import de.lichtflut.rb.websample.WebSampleApplication;

/**
 * Simple test using the WicketTester.
 */
public class TestHomePage {
	private WicketTester tester;

	@Before
	public void setUp(){

		final WebSampleApplication app = new WebSampleApplication() {
			@Override
			protected void init() {
				initWebsampleApp();
			}
		};
		
		ApplicationContextMock appctx=new ApplicationContextMock();
		app.getComponentInstantiationListeners().add(new SpringComponentInjector(app, appctx ));
		
		tester = new WicketTester(app);
	}

	@Ignore
	public void testRenderMyPage(){
		//start and render the test page
		tester.startPage(DashboardPage.class);

		//assert rendered page class
		tester.assertRenderedPage(DashboardPage.class);

		//assert rendered label component
		tester.assertLabel("title", "Dashboard");
	}
}
