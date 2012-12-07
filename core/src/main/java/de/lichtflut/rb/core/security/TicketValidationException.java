package de.lichtflut.rb.core.security;

/**
 * <p>
 *  Exception to be thrown when an AuthenticationTicket or token is not valid.
 * </p>
 *
 * <p>
 *  Created 25.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class TicketValidationException extends Exception {

	public TicketValidationException() {
	}

	public TicketValidationException(final String message) {
		super(message);
	}

	public TicketValidationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public TicketValidationException(final Throwable cause) {
		super(cause);
	}

}
