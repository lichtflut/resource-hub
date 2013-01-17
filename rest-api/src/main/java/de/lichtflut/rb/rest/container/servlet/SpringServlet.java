/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.container.servlet;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Properties;


/**
 * <p>
 * This class is just an empty subclass of {@link com.sun.jersey.spi.spring.container.servlet.SpringServlet}
 * to prevent additional jersey-dependencies in projects which are using rb.rest including a default configuration
 * <br />
 * No further functionality can be expected here
 * </p>
 * 
 * 
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 *
 */
public class SpringServlet extends com.sun.jersey.spi.spring.container.servlet.SpringServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4594732563059056583L;
	
	private Properties properties = new Properties();
	
	public SpringServlet(){
		super();
		initParams();
	}
		
	private void initParams(){
		properties.put("com.sun.jersey.config.property.packages", "de.lichtflut.rb.rest");
		properties.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
		
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration getInitParameterNames() {
		final Enumeration additionalNames = properties.keys();
		final Enumeration names = super.getInitParameterNames();
		return new Enumeration<String>() {

			@Override
			public boolean hasMoreElements() {
				if(names.hasMoreElements()){
					return true;
				}
				return additionalNames.hasMoreElements();
			}

			@Override
			public String nextElement() {
				if(names.hasMoreElements()){
					return names.nextElement().toString();
				}
				if(additionalNames.hasMoreElements()){
					return additionalNames.nextElement().toString();
				}
				throw new NoSuchElementException();
			}
		};
	}
	
	
	@Override
	public String getInitParameter(String name) {
		String value = super.getInitParameter(name);
		if(value==null){
			return properties.getProperty(name);
		}
		return value;
	}
	
	
}
