package de.lichtflut.rb.core.api;

import java.io.File;
import java.io.InputStream;

import de.lichtflut.rb.core.schema.parser.RSFormat;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;

/**
* Created: Sep. 13, 2011
*
* @author Raphael Esterle
*/

public interface SchemaImporter {

	/**
	 * Stores the schemaRepresentation for a given format.
	 * @param representation - the textstyle schema representation
	 * @param format - {@link RSFormat} to describe the format of the representation
	 */
	void storeSchemaRepresentation(String representation, RSFormat format);

	// -----------------------------------------------------

	/**
	  * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	  * What does "resolve" in this context mean? It's the primary process of interpreting and parsing the result,
	  * connecting ResourceSchmema to PropertyDeclarations over PropertyAssertion, check for consistency,
	  * converting the given constraints or human readable semantics into
	  * a system readable style and check the availability of requested resource-types.
	  * @param is - the {@link InputStream} which contains the ResourceSchema in the given {@link RSFormat}
	  * @return the {@link RSParsingResult}
	 */
	 RSParsingResult generateAndResolveSchemaModelThrough(InputStream is);

	// -----------------------------------------------------

	/**
	  * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	  * What does "resolve" in this context mean? It's the primary process of interpreting and parsing the result,
	  * connecting ResourceSchmema to PropertyDeclarations over PropertyAssertion, check for consistency,
	  * converting the given constraints or human readable semantics into
	  * a system readable style and check the availability of requested resource-types.
	  * @param file - the {@link File} which contains the ResourceSchema in the given {@link RSFormat}
	  * @return the {@link RSParsingResult}
	 */
	RSParsingResult generateAndResolveSchemaModelThrough(File file);

	// -----------------------------------------------------

	/**
	  * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	  * What does "resolve" in this context mean? It's the primary process of interpreting and parsing the result,
	  * connecting ResourceSchmema to PropertyDeclarations over PropertyAssertion, check for consistency,
	  * converting the given constraints or human readable semantics into
	  * a system readable style and check the availability of requested resource-types.
	  * @param s - the {@link String} which contains the ResourceSchema in the given {@link RSFormat}
	  * @return the {@link RSParsingResult}
	 */
	RSParsingResult generateAndResolveSchemaModelThrough(String s);

	// -----------------------------------------------------

	/**
	 * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	 * There will be with the exclusion of some grammar-syntax errors no further resolution and reasoning/interpreting-processes.
	 * @param is - the {@link InputStream} which contains the ResourceSchema in the given {@link RSFormat}
	 * @return the {@link RSParsingResult}
	 */
	RSParsingResult generateSchemaModelThrough(InputStream is);

	// -----------------------------------------------------

	/**
	 * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	 * There will be with the exclusion of some grammar-syntax errors no further resolution and reasoning/interpreting-processes.
	 * @param file - the {@link File} which contains the ResourceSchema in the given {@link RSFormat}
	 * @return the {@link RSParsingResult}
	 */
	RSParsingResult generateSchemaModelThrough(File file);

	// -----------------------------------------------------

	/**
	 * A ResourceSchema is being parsed and interpreted for a given input, based by an already defined RSFormat
	 * There will be with the exclusion of some grammar-syntax errors no further resolution and reasoning/interpreting-processes.
	 * @param s - the {@link String} which contains the ResourceSchema in the given {@link RSFormat}
	 * @return the {@link RSParsingResult}
	 */
	RSParsingResult generateSchemaModelThrough(String s);

	// -----------------------------------------------------


}
