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
 *  Basic exception for Resource Browser.
 * </p>
 *
 * <p>
 * 	Created Jan 23, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class RBException extends Exception {

	private int errorCode;

	// ---------------- Constructor -------------------------

	public RBException(final String msg){
		super(msg);
	}

	/**
	 * @param errorCode
	 * @param msg
	 */
	public RBException(int errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}
	
	public RBException(int errorCode, String msg, Throwable e){
		super(msg, e);
		this.errorCode = errorCode;
	}

	// ------------------------------------------------------

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}
	
	
}
