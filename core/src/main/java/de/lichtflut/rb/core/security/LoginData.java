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
package de.lichtflut.rb.core.security;

import java.io.Serializable;

/**
 * <p>
 * Represents a user's login data.
 * </p>
 * 
 * Created: Aug 10, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class LoginData implements Serializable {

	private String password;
	private String id;
	private boolean stayLoggedIn;

	// ----------------------------------------------------

	/**
	 * Default Constructor.
	 */
	public LoginData() {
	}

	// ----------------------------------------------------

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getLoginID() {
		return id;
	}

	public void setLoginID(final String id) {
		this.id = id;
	}

	public boolean getStayLoggedIn() {
		return stayLoggedIn;
	}

	public void setStayLoggedIn(final boolean stayLoggedIn) {
		this.stayLoggedIn = stayLoggedIn;
	}

}
