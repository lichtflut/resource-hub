package de.lichtflut.rb.core.schema.parser.exception;


/**
 * <p>
 *  For exceptions during parsing of schemas.
 * </p>
 *
 * <p>
 * Created 19.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class SchemaParsingException extends Exception {

	public SchemaParsingException() {
	}

	public SchemaParsingException(final String message) {
		super(message);
	}

	public SchemaParsingException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SchemaParsingException(final Throwable cause) {
		super(cause);
	}

}
