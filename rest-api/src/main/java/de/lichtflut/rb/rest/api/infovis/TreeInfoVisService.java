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
package de.lichtflut.rb.rest.api.infovis;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.rest.api.util.CachingSchemaProvider;
import de.lichtflut.rb.rest.api.util.QuickInfoBuilder;
import de.lichtflut.rb.rest.api.util.SchemaProvider;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.traverse.NotPredicateFilter;
import org.arastreju.sge.traverse.PredicateFilter;
import org.arastreju.sge.traverse.TraversalFilter;
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
import java.util.Locale;

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
@Path("domains/{domain}/infovis/tree")
public class TreeInfoVisService extends AbstractInfoVisService{

    private static final Logger LOGGER = LoggerFactory.getLogger(TreeInfoVisService.class);

    public enum Type {
        DEFAULT,
        HIERARCHY
    }

    // ----------------------------------------------------

    public TreeInfoVisService() {
    }

    // ----------------------------------------------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(
            @QueryParam(value="root") String rootURI,
            @QueryParam(value="type") String typeName,
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

        Type type = getType(typeName);

        LOGGER.info("Building tree of type '{}' for '{}' ", type, qn);

        SchemaProvider schemaProvider = new CachingSchemaProvider(schemaManager(domain, user));

        TreeBuilder treeBuilder = treeBuilder(schemaProvider, type, request.getLocale());
        TreeNodeRVO rootNode = treeBuilder.build(resource);
        return Response.ok(rootNode).build();
    }

    // ----------------------------------------------------

    private TreeBuilder treeBuilder(SchemaProvider provider, Type type, Locale locale) {
        QuickInfoBuilder quickInfoBuilder = new QuickInfoBuilder(provider);
        return new TreeBuilder(quickInfoBuilder, locale, filterForType(type));
    }

    private TraversalFilter filterForType(Type type) {
        switch (type) {
            case HIERARCHY:
                return new PredicateFilter()
                        .addFollow(
                                RB.HAS_CHILD_NODE,
                                RB.HAS_SUBORDINATE
                        );
            default:
                return new NotPredicateFilter(
                                RDF.TYPE,
                                RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE,
                                RDFS.SUB_CLASS_OF
                        );
        }
    }

    private QualifiedName toQualifiedName(String uri) {
        if (uri.contains("/")) {
            return QualifiedName.from(uri);
        } else {
            return QualifiedName.from(decodeBase64(uri));
        }
    }

    private Type getType(String type) {
        if (type == null) {
            return Type.DEFAULT;
        }

        return Type.valueOf(type.toUpperCase());
    }

}
