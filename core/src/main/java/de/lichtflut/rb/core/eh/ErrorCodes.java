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

	public static final Long UNKNOWN_ERROR = 0L;
	
	public static final Long INVALID_PASSWORD = 100L;
	
	public static final Long SECURITYSERVICE_EMAIL_ALREADY_IN_USE = 1201L;
	public static final Long SECURITYSERVICE_USERNAME_ALREADY_IN_USE = 1202L;
	
	public static final Long SECURITYSERVICE_DOMAIN_NOT_FOUND = 1301L;
	
	public static final Long EMAIL_SERVICE_EXCEPTIO = 3000L;
	
}
