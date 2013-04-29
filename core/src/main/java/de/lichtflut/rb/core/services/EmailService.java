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
package de.lichtflut.rb.core.services;

import java.util.Locale;

import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.security.RBUser;

/**
 * <p>
 *  This service provides way to transmit messages via email.
 * </p><p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public interface EmailService {

	/**
	 * Sends an email containing a {@link RBUser}s password.
	 * @param user
	 * @param password
	 * @param locale
	 * @throws RBException 
	 */
	void sendPasswordInformation(RBUser user, String password, Locale locale) throws RBException;

	/**
	 * Sends an email to inform a {@link RBUser} of a successful account creation.
	 * @param user
	 * @param locale 
	 * @throws RBException 
	 */
	void sendRegistrationConfirmation(RBUser user, Locale locale) throws RBException;

	/**
	 * Sends an email to inform a {@link RBUser} that his account has been activated.
	 * @param user
	 * @param locale
	 * @throws RBException 
	 */
	void sendAccountActivatedInformation(RBUser user, Locale locale) throws RBException;

}
