/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import java.io.IOException;
import java.io.OutputStream;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;

/**
 * <p>
 *  Base interface for writers of Resource Schemas in different formats.
 * </p>
 *
 * <p>
 * 	Created Oct 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ResourceSchemaWriter {

	void write(final OutputStream out, final ResourceSchema... schemas) throws IOException;
	
	void write(final OutputStream out, final TypeDefinition... typeDefinitions) throws IOException;
	
}
