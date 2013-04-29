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

	ACCOUNT_ACTIVATED_MAIL,
	PASSWORD_INFORMATION_MAIL,
	REGISTRATION_CONFIRMATION_MAIL,
	
}
