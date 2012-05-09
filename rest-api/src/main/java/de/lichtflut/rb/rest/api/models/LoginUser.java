/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * Simple POJO to represent a system user for authentication and authorization
 * purposes.
 * </p>
 * <p>
 * This model can be seen as bean, flagged with jaxb annotations to get some
 * automated marshalling stuff within the webservice communication
 * </p>
 * 
 * 
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * 
 */
@XmlRootElement
public class LoginUser {

	/**
	 * userID - nothing special about it
	 */
	
	private String id;
	/**
	 * password - nothing special about it
	 */
	private String password;

	// On the following lines you'll see all the setters and getters, huh!

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
