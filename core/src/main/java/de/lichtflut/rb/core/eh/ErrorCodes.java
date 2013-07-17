/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    public static final int LOGIN_USER_NOT_FOUND = 1101;
    public static final int LOGIN_USER_CREDENTIAL_NOT_MATCH = 1102;
    public static final int LOGIN_INVALID_DATA = 1103;

    public static final int SECURITY_EMAIL_ALREADY_IN_USE = 1201;
	public static final int SECURITY_USERNAME_ALREADY_IN_USE = 1202;

	public static final int SECURITY_DOMAIN_NOT_FOUND = 1301;

    public static final int NOT_AUTHORIZED = 1401;

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
