package de.lichtflut.rb.core.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  A security ticket identifying a user.
 * </p>
 *
 * <p>
 *  Created 25.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class AuthenticationTicket {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTicket.class);
	
    public enum Type {
        SESSION("s"),
        REMEMBER_ME("r");

        String prefix;

        Type(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }

    // ----------------------------------------------------

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss Z");

    // ----------------------------------------------------

    private final String user;

    private final Date expirationDate;

    private final String signature;
    private Type type;

    // -- Static Methods ----------------------------------

    public static AuthenticationTicket fromToken(String token) throws TicketValidationException {
        final String[] fields = token.split(":");
        if (fields.length != 4) {
            throw new TicketValidationException("Token has wrong structure: " + token);
        }

        try {
            Type type = detectTicketType(fields[0]);
            String user = fields[1];
            Date expirationDate = DATE_FORMAT.parse(fields[2]);
            String signature = fields[3];
            return new AuthenticationTicket(user, expirationDate, signature, type);
        } catch (ParseException e) {
        	LOGGER.info("Token seems to be in a wrong format: " + token, e);
            throw new TicketValidationException("Token expiration date hat wrong format: " + token, e);
        }
    }

    public static AuthenticationTicket generate(Type type, String user, Date expirationDate, String secret) {
        String dateString = DATE_FORMAT.format(expirationDate);
        String raw = type.getPrefix() + ":" +user + ":" + dateString;
        String signature = RBCrypt.md5Hex(raw + secret);
        return new AuthenticationTicket(user, expirationDate, signature, type);
    }

    private static Type detectTicketType(String field) throws TicketValidationException {
        if (Type.SESSION.getPrefix().equals(field)) {
            return Type.SESSION;
        } else if (Type.REMEMBER_ME.getPrefix().equals(field)) {
            return Type.REMEMBER_ME;
        } else {
            throw new TicketValidationException("Unknown type prefix '" + field + "' in token.");
        }
    }

    // ----------------------------------------------------

    /**
     * Constructor.
     * @param user The unique user ID.
     * @param expirationDate The ticket's expiration date.
     * @param signature The signature.
     * @param type The type of the ticket.
     */
    private AuthenticationTicket(String user, Date expirationDate, String signature, Type type) {
        this.user = user;
        this.expirationDate = expirationDate;
        this.signature = signature;
        this.type = type;
    }

    // ----------------------------------------------------

    public Type getType() {
        return type;
    }

    public String getSignature() {
        return signature;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public String getUser() {
        return user;
    }

    // ----------------------------------------------------

    public String toToken() {
        StringBuilder sb = new StringBuilder();
        sb.append(payload());
        sb.append(":");
        sb.append(signature);
        return sb.toString();
    }

    // ----------------------------------------------------

    /**
     * Validate structure and expiration date.
     */
    public void validate() throws TicketValidationException {
        if (StringUtils.isBlank(user)) {
            throw new TicketValidationException("Ticket is invalid, no user set: " + toToken());
        }
        if (expirationDate.before(new Date())) {
            throw new TicketValidationException("Ticket has expired: " + toToken());
        }
    }

    /**
     * Validate the ticket completely, including structure, expiration date and the signature.
     */
    public void validateSignature(String secret) throws TicketValidationException {
        validate();
        String calcSig = sign(payload(), secret);
        if (!calcSig.equals(signature)) {
            throw new TicketValidationException("Ticket signature is invalid: " + toToken());
        }
    }

    // ----------------------------------------------------

    protected String payload() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.getPrefix());
        sb.append(":");
        sb.append(user);
        sb.append(":");
        sb.append(DATE_FORMAT.format(expirationDate));
        return sb.toString();
    }

    protected String sign(String payload, String secret) {
        return RBCrypt.md5Hex(payload + secret);
    }

    // ----------------------------------------------------

    @Override
    public String toString() {
        return "AuthenticationTicket{" + toToken() + "}";
    }
}
