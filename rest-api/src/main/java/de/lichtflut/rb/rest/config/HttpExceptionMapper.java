package de.lichtflut.rb.rest.config;

import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.eh.RBException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * <p>
 *  Mapper for Java Exceptions to HTTP responses.
 * </p>
 *
 * <p>
 *  Created 06.07.12
 * </p>
 *
 * @author Oliver Tigges
 */
@Provider
public class HttpExceptionMapper implements ExceptionMapper<RBException> {

    @Override
    public Response toResponse(RBException exception) {
        switch (exception.getErrorCode()) {
            case ErrorCodes.SECURITY_UNAUTHENTICATED_USER:
                return Response.status(Response.Status.FORBIDDEN).build();
            default:
                return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
}
