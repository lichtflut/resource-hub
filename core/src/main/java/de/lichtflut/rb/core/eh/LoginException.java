package de.lichtflut.rb.core.eh;

/**
 * <p>
 *  Exception thrown when login does not succeed.
 * </p>
 *
 * <p>
 * 	Created Apr 29, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class LoginException extends RBAuthException {

    /**
     * @param errCode
     * @param msg
     * @param cause
     */
    public LoginException(int errCode, String msg, Throwable cause) {
        super(errCode, msg, cause);
    }

    /**
     * @param errCode
     * @param msg
     */
    public LoginException(int errCode, String msg) {
        super(errCode, msg);
    }

}
