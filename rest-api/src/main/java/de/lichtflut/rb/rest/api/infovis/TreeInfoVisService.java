package de.lichtflut.rb.rest.api.infovis;

import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * <p>
 *  Resource providing JSON tree for a resource.
 * </p>
 *
 * <p>
 *  Created 11.01.13
 * </p>
 *
 * @author Oliver Tigges
 */
@Component
@Path("infovis/domains/{domain}/tree")
public class TreeInfoVisService extends AbstractInfoVisService{

    private static final Logger LOGGER = LoggerFactory.getLogger(TreeInfoVisService.class);

    // ----------------------------------------------------

    public TreeInfoVisService() {
    }

    // ----------------------------------------------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(
            @QueryParam(value="root") String rootURI,
            @PathParam(value = "domain") String domain,
            @CookieParam(value= AuthModule.COOKIE_SESSION_AUTH) String token,
            @Context HttpServletRequest request) throws UnauthenticatedUserException
    {

        RBUser user = authenticateUser(token);

        if (rootURI == null) {
            LOGGER.info("Invalid request: no root node given.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        QualifiedName qn = toQualifiedName(rootURI);
        ResourceNode resource = conversation(domain, user).findResource(qn);
        if (resource == null) {
            LOGGER.info("Invalid request: node '{}' not found." , qn);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        LOGGER.info("Building tree for '{}'", qn);
        TreeNodeRVO rootNode = new TreeBuilder(request.getLocale()).build(resource);
        return Response.ok(rootNode).build();
    }

    // ----------------------------------------------------

    private QualifiedName toQualifiedName(String uri) {
        if (uri.contains("/")) {
            return QualifiedName.create(uri);
        } else {
            return QualifiedName.create(decodeBase64(uri));
        }
    }

}
