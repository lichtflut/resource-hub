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
package de.lichtflut.rb.webck.common;

/**
 * <p>
 *  Enumeration of query modes.
 * </p>
 *
 * <p>
 * 	Created Feb 28, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public enum QueryMode {
	
	UNKNOWN(null),
	
	URI("uri"),
	
	VALUES("values"),
	
	RELATION("relation"),
	
	ENTITY("entity"),
	
	PROPERTY("property"),
	
	SUB_CLASS("subclass");
	
	// ----------------------------------------------------
	
	private final String path;
	
	// ----------------------------------------------------

	private QueryMode(String path) {
		this.path = path;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the uri
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @return the uri
	 */
	public String getPath(String context) {
		return context + path;
	}

}
