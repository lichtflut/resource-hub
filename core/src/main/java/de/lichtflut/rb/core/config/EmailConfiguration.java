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
package de.lichtflut.rb.core.config;

import de.lichtflut.rb.core.services.EmailService;

/**
 * <p>
 *  This interface provides the {@link EmailService} with all neccessary informations concerning Email 
 * </p>
 *
 * <p>
 * 	Created Jan 23, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public interface EmailConfiguration {

	/**
	 * @return the smtpServer
	 */
	String getSmtpServer();

	/**
	 * @return the smtpUser
	 */
	String getSmtpUser();

	/**
	 * @return the smtpPassword
	 */
	String getSmtpPassword();
	
	String getApplicationSupportName();
	
	String getApplicationSupportEmail();
	
	String getApplicationName();
	
	String getApplicationEmail();
}
