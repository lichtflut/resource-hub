/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.arastreju.sge.security.LoginException;
import org.springframework.stereotype.Component;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.LoginData;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.rest.api.models.transfer.UserCredentials;

/**
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 *
 */
@Component
@Path("auth/")
public class AuthOps extends RBServiceEndpoint {
		
	public AuthOps(){}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.TEXT_PLAIN})
	public Response createToken(UserCredentials loginUser) throws UnsupportedEncodingException{		
		LoginData loginData = new LoginData();
		loginData.setLoginID(loginUser.getId());
		loginData.setPassword(loginUser.getPassword());
		Response rsp;
		try {
			AuthenticationService authService = authModule.getAuthenticationService();
			RBUser user = authService.login(loginData);
			String token = authService.createSessionToken(user);
			//Is necessary to prevent special character beeing wrong interpreted by a client
			token = URLEncoder.encode(token, "UTF-8");
			NewCookie cookie = new NewCookie(AuthModule.COOKIE_SESSION_AUTH, token);
			rsp = Response.status(Response.Status.CREATED).cookie(cookie).entity(token).build();
		} catch (LoginException e) {
			rsp = Response.status(Response.Status.UNAUTHORIZED).build();
			e.printStackTrace();
		}
		
		return rsp;
	}
}
