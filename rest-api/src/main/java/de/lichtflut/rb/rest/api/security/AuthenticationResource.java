package de.lichtflut.rb.rest.api.security;

import de.lichtflut.rb.RBPermission;
import de.lichtflut.rb.core.eh.LoginException;
import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.LoginData;
import de.lichtflut.rb.core.security.RBCrypt;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.security.UserManager;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import de.lichtflut.rb.rest.api.common.UserRVO;
import de.lichtflut.rb.rest.api.models.transfer.UserCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * <p>
 *  Resource for authentication. Obtain a session ticket.
 * </p>
 *
 * <p>
 *  Created July 8, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
@Component
@Path("auth")
public class AuthenticationResource extends RBServiceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationResource.class);

    // ----------------------------------------------------

    @Path("tickets")
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.TEXT_PLAIN})
    public Response createToken(UserCredentials loginUser)
            throws UnsupportedEncodingException {
        LoginData loginData = new LoginData();
        loginData.setLoginID(loginUser.getId());
        loginData.setPassword(loginUser.getPassword());
        Response rsp;
        try {
            AuthenticationService authService = authModule.getAuthenticationService();
            RBUser user = authService.login(loginData);
            String token = authService.createSessionToken(user);
            rsp = Response.status(Response.Status.CREATED).entity(token).build();
        } catch (LoginException e) {
            rsp = Response.status(Response.Status.UNAUTHORIZED).build();
            LOGGER.info("Login failed for user {}.", loginData.getLoginID());
        }

        return rsp;
    }

    @Path("users")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(
            @CookieParam(value = AuthModule.COOKIE_SESSION_AUTH) String token,
            UserRVO userRVO)
            throws UnsupportedEncodingException, UnauthenticatedUserException {

        RBUser callingUser = authenticateUser(token);
        authorizeUser(callingUser, AuthModule.MASTER_DOMAIN, RBPermission.CREATE_USERS);

        RBUser newUser = new RBUser();
        newUser.setDomesticDomain(userRVO.getHomeDomain());
        newUser.setEmail(userRVO.getEmail());
        newUser.setUsername(userRVO.getName());

        try {
            UserManager um = authModule.getUserManagement();
            String encryptedPW = RBCrypt.encryptWithRandomSalt(userRVO.getPassword());
            um.registerUser(newUser, encryptedPW, userRVO.getHomeDomain());
        } catch (Exception e) {
            LOGGER.info("User creation failed for user '{}' due to: {}", newUser.getName(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

}
