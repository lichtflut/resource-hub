package de.lichtflut.rb.core.api;

import java.util.Collection;

import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;

/**
 * 	 Created Sep. 14, 2011
 *
 * @author Raphael Esterle
 */
public interface SchemaExporter {
	
	RSParsingResult convertToParsingResult(final Collection<ResourceSchemaType> types);
	
}
