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
package de.lichtflut.rb.rest.api.provider;

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
