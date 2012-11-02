package de.lichtflut.rb.core.eh;

/**
 * <p>
 *  Exception for configuration issues.
 * </p>
 *
 * <p>
 *  Created 02.11.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class ConfigurationException extends RBException {

    public ConfigurationException(final String msg) {
        super(msg);
    }

    public ConfigurationException(int errorCode, String msg) {
        super(errorCode, msg);
    }

    public ConfigurationException(int errorCode, String msg, Throwable e) {
        super(errorCode, msg, e);
    }
}
