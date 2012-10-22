/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.writer;

import java.io.IOException;
import java.io.OutputStream;

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

	void write(final OutputStream out, final OutputElements elements) throws IOException;
	
}
