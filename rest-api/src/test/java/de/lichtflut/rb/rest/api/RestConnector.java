/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.WebAppDescriptor.Builder;

/**
 * <p>
 *  Connector to encapsulate the JerseyTest-Framework
 * </p>
 * 
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created May 10, 2012
 */
public class RestConnector extends JerseyTest {

	@Override
	public AppDescriptor configure(){
		 WebAppDescriptor descriptor;
		// Init WebAppDescriptor
		Builder builder = new WebAppDescriptor.Builder();
		builder.initParam("com.sun.jersey.config.property.packages", "de.lichtflut.rb.rest.api");
		builder.contextPath("/");
		System.setProperty("jersey.test.port", "1337");
		builder.contextParam("contextConfigLocation", "applicationContext.xml");
		builder.contextListenerClass(org.springframework.web.context.ContextLoaderListener.class);
		builder.servletClass(SpringServlet.class);
		builder.servletPath("/service");
		descriptor = builder.build();
		return descriptor;
	}
}
