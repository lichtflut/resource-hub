package de.lichtflut.rb.core.config.domainstatus;

/**
 * <p>
 *  Exception while reading or writing domain infos.
 * </p>
 * <p>
 *  Created 31.12.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class DomainInfoException extends Exception {

    public DomainInfoException() {
    }

    public DomainInfoException(String message) {
        super(message);
    }

    public DomainInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainInfoException(Throwable cause) {
        super(cause);
    }
}
