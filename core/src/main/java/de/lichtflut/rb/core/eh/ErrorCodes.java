/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.eh;

/**
 * <p>
 *  Error codes for RB-Core
 * </p>
 *
 * <p>
 * 	Created Jan 23, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public interface ErrorCodes {

	public static final int UNKNOWN_ERROR = 0;
	
	public static final int INVALID_PASSWORD = 100;


    public static final int SECURITY_EXCEPTION = 1000;

    public static final int SECURITY_UNAUTHENTICATED_USER = 1100;
	
	public static final int SECURITY_EMAIL_ALREADY_IN_USE = 1201;
	public static final int SECURITY_USERNAME_ALREADY_IN_USE = 1202;
	
	public static final int SECURITY_DOMAIN_NOT_FOUND = 1301;


	
	public static final int EMAIL_SERVICE_EXCEPTIO = 3000;
	
}
