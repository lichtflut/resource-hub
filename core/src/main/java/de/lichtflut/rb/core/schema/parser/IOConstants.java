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
package de.lichtflut.rb.core.schema.parser;

/**
 * <p>
 *  Common constants for parsers / generators of all formats.
 * </p>
 *
 * <p>
 * 	Created Oct 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface IOConstants {
	
	public static final String NAMESPACE_DECLS = "namespace-declarations";
	
	public static final String PREFIX = "prefix";
	
	public static final String NAMESPACE = "namespace";
	
	// ----------------------------------------------------
	
	public static final String ID = "id";
	
	public static final String DEFAULT = "default";
	
	// ----------------------------------------------------
	
	public static final String RESOURCE_SCHEMAS = "resource-schemas";
	
	public static final String SCHEMA = "schema";
	
	public static final String FOR_TYPE = "for-type";

	public static final String DATATYPE = "datatype";

	public static final String LITERAL = "literal";

	public static final String NAME = "name";

	public static final String RESOURCE_TYPE = "resource-type";

	public static final String TYPE_DEFINITION = "type-definition";

	public static final String RESOURCE_CONSTRAINT = "resource-constraint";
	
	public static final String LITERAL_CONSTRAINT = "literal-constraint";
	
	public static final String CONSTRAINT_REFERENCE = "reference-constraint";
	
	public static final String PUBLIC_CONSTRAINTS = "public-constraints";

	public static final String APPLICABLE_DATATYPES = "applicable-datatypes";
	
	public static final String MAX = "max";

	public static final String MIN = "min";

	public static final String CARDINALITY = "cardinality";
	
	public static final String TYPE_REFERENCE = "type-reference";

	public static final String PROPERTY_TYPE = "property-type";
	
	public static final String FIELD_LABEL = "field-label";

	public static final String PROPERTY_DECLARATION = "property-declaration";
	
	public static final String CONSTRAINT = "constraint";
	
	public static final String LABEL_RULE = "label-rule";

}
