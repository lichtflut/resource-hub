package de.lichtflut.rb.rest.api.security;

import de.lichtflut.rb.core.eh.LoginException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.LoginData;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import de.lichtflut.rb.rest.api.models.transfer.UserCredentials;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
            e.printStackTrace();
        }

        return rsp;
    }

}
