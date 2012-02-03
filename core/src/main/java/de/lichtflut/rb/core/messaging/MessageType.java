/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.messaging;

/**
 * <p>
 *  A {@link MessageType} defines what kind of massage will be send.
 *  <br />like:
 *  <ul>
 *  	<li>Passwort reset</li>
 *  	<li>Confirmation mail</li>
 *  </ul>
 * </p>
 *
 * <p>
 * 	Created Jan 26, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public enum MessageType {

	PASSWORD_INFORMATION_MAIL,
	REGISTRATION_INFORMATION_MAIL,
	UNDEFINED
	
}
