/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import de.lichtflut.rb.core.schema.parser.exception.SchemaParsingException;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 *  Interface for all parsers of Resource Schemas. 
 * </p>
 *
 * <p>
 * 	Created Oct 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ResourceSchemaParser {

	ParsedElements parse(InputStream in) throws IOException, SchemaParsingException;
	
}
