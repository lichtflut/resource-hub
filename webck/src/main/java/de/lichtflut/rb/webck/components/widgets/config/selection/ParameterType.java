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
package de.lichtflut.rb.webck.components.widgets.config.selection;

/**
 * <p>
 *  Enumeration of parameter types.
 * </p>
 *
 * <p>
 * 	Created Jan 27, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public enum ParameterType {
	
	// Convenience for field with predicate rdf:type 
	RESOURCE_TYPE,
	
	// a special field.
	SPECIAL_FIELD_VALUE,
	
	// 
	SPECIAL_FIELD_RELATION,
	
	// any field.
	ANY_FIELD_VALUE,
	
	// any field relation.
	ANY_FIELD_RELATION;

}
