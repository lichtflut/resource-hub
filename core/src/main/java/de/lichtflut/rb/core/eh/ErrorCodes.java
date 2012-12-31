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

    // -------- Configuration -----------------------------

    public static final int DOMAIN_INFO_COULD_NOT_BE_READ = 2001;

    // -------- Messaging ---------------------------------

	public static final int EMAIL_SERVICE_EXCEPTION = 3000;

	// -------- RBEntity validation -----------------------

	public static final int CARDINALITY_EXCEPTION = 4000;

	// -------- Schema validation -------------------------

	public static final int SCHEMA_NULL_EXCEPTION = 5000;

	public static final int SCHEMA_CONSTRAINT_EXCEPTION = 5001;
}
