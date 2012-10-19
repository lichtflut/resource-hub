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

    public SchemaParsingException(String message) {
        super(message);
    }

    public SchemaParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchemaParsingException(Throwable cause) {
        super(cause);
    }

    public SchemaParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
