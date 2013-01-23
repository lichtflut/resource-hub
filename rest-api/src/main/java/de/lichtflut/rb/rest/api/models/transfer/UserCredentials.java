/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.models.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 *  TODO: To document
 * </p>
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created Jan 22, 2013
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserCredentials {

	private String id;
	private String password;
	/**
	 * @return the passwd
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the passwd to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
}
