/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

/**
 * <p>
 *  User of Arastreju.
 * </p>
 *
 * <p>
 * 	Created Jan 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface User extends Identity {

	String getEmail();

}
