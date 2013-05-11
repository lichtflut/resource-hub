/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.security.authserver;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.eh.LoginException;
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.AuthenticationTicket;
import de.lichtflut.rb.core.security.LoginData;
import de.lichtflut.rb.core.security.RBCrypt;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.security.TicketValidationException;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.eh.ArastrejuRuntimeException;
import org.arastreju.sge.model.TimeMask;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNTimeSpec;
import de.lichtflut.rb.core.security.Credential;
import de.lichtflut.rb.core.security.PasswordCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

import static de.lichtflut.rb.core.security.authserver.EmbeddedAuthFunctions.toRBUser;
import static org.arastreju.sge.SNOPS.singleObject;

/**
 * <p>
 *  This is the login service of the embedded auth module.
 * </p>
 *
 * <p>
 * 	Created May 4, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EmbeddedAuthLoginService implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedAuthLoginService.class);

	private static final String randomSecret = RBCrypt.random(20);

	private final Conversation conversation;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param conversation The Arastreju conversation.
	 */
	public EmbeddedAuthLoginService(Conversation conversation) {
		this.conversation = conversation;
	}
	
	// -- LOGIN -------------------------------------------
	
	@Override
	public RBUser login(LoginData loginData) throws LoginException {
		final String id = normalize(loginData.getLoginID());
		if (id == null) {
			throw new LoginException(ErrorCodes.LOGIN_INVALID_DATA, "No username given");
		}
		
		LOGGER.info("Trying to login user '" + id + "'.");

		final ResourceNode user = findUserNode(id);
		if (user == null){
			throw new LoginException(ErrorCodes.LOGIN_USER_NOT_FOUND, "User does not exist: " + id);	
		}
		
		verifyPassword(user, loginData.getPassword());
		setLastLogin(user);
		
		LOGGER.info("User {} logged in. ", user);
		return toRBUser(user);
	}

	@Override
	public RBUser loginByToken(String token) {
		if (token == null) {
			return null;
		}
        try {
            AuthenticationTicket ticket = AuthenticationTicket.fromToken(token);
            switch (ticket.getType()) {
                case SESSION:
                    return loginBySessionTicket(ticket);
                case REMEMBER_ME:
                    return loginByRememberMeTicket(ticket);
                default:
                    LOGGER.warn("Unknown authentication type: " + token);
            }
        } catch (TicketValidationException e) {
            LOGGER.warn("Token " + token + " is not valid: " + e.getMessage(), e.getCause());
        }
        return null;
	}
	
	@Override
	public String createSessionToken(RBUser user) {
		final String email = user.getEmail();
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, 12);
        AuthenticationTicket ticket = AuthenticationTicket.generate(
                AuthenticationTicket.Type.SESSION, email, cal.getTime(), randomSecret);
        return ticket.toToken();
	}

	@Override
	public String createRememberMeToken(RBUser user, LoginData loginData) {
		final String email = user.getEmail();
		final String id = normalize(loginData.getLoginID());
		final Credential credential = toCredential(loginData.getPassword(), findUserNode(id));
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 30);

        AuthenticationTicket ticket = AuthenticationTicket.generate(
                AuthenticationTicket.Type.REMEMBER_ME, email, cal.getTime(), credential.stringRepesentation());
        return ticket.toToken();
	}

	// ----------------------------------------------------
	
	protected void verifyPassword(final ResourceNode user, String password) throws LoginException {
		final Credential credential = toCredential(password, user);
		if (!credential.applies(singleObject(user, EmbeddedAuthModule.HAS_CREDENTIAL))){
			throw new LoginException(ErrorCodes.LOGIN_USER_CREDENTIAL_NOT_MATCH, "Wrong credential");
		}
	}
	
	protected ResourceNode findUserNode(final String id) {
		return EmbeddedAuthFunctions.findUserNode(conversation, id);
	}

	protected RBUser loginByRememberMeTicket(AuthenticationTicket ticket) throws TicketValidationException {
		try {
			final ResourceNode arasUser = findUserNode(ticket.getUser());
			if (arasUser != null ) {
                final SemanticNode credential = SNOPS.singleObject(arasUser, EmbeddedAuthModule.HAS_CREDENTIAL);
                final String secret = credential.asValue().getStringValue();
                ticket.validateSignature(secret);
                final RBUser user = toRBUser(arasUser);
                LOGGER.info("User {} logged in by remember me token.", user);
                setLastLogin(arasUser);
				return user;
			} else {
                LOGGER.warn("User {} from tocken {} does not exist.", ticket.getUser(), ticket.toToken());
            }
		} catch (ArastrejuRuntimeException e) {
			LOGGER.error("Failed to login by remember me ticket " + ticket, e);
			return null;
		}
		return null;
	}

    protected RBUser loginBySessionTicket(AuthenticationTicket ticket) throws TicketValidationException {
		try {
            ticket.validateSignature(randomSecret);
			final ResourceNode arasUser = findUserNode(ticket.getUser());
            if (arasUser != null ) {
                LOGGER.info("User {} logged in by session token.", ticket.getUser());
			    return toRBUser(arasUser);
            } else {
                LOGGER.warn("User {} from token {} does not exist.", ticket.getUser(), ticket.toToken());
            }
		} catch (ArastrejuRuntimeException e) {
			LOGGER.error("Failed to login by session ticket " + ticket, e);
		}
		return null;
	}
	
	// ----------------------------------------------------

    private void setLastLogin(final ResourceNode user) {
		SNOPS.assure(user, RBSystem.HAS_LAST_LOGIN, new SNTimeSpec(new Date(), TimeMask.TIMESTAMP));
	}
	
	private Credential toCredential(final String password, final ResourceNode user) {
		if (password != null) {
			String salt = EmbeddedAuthFunctions.getSalt(user);
			return new PasswordCredential(RBCrypt.encrypt(password, salt));
		} else {
            LOGGER.warn("Creating credential with null password for user {}.", user);
			return new PasswordCredential(null);
		}
	}
	
	private String normalize(String name) {
		if (name == null) {
			return null;
		} else {
			return name.trim().toLowerCase();
		}
	}
	
}
