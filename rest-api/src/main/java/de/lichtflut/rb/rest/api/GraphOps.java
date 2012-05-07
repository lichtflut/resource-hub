package de.lichtflut.rb.rest.api;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.model.DefaultSemanticGraph;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 * 
 * 
 * </p>
 * 
 * 
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 *
 */
@Component
@Path("/graph/{" + RBServiceEndpoint.DOMAIN_ID_PARAM + "}/")
public class GraphOps extends RBServiceEndpoint{

	//Constants (package visibility sufficient)
	static final String NODE_ID_PARAM = "ID";

	/**
	 * Instance of {@link Logger}
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());

	//Constructor
	
	/**
	 * Default empty constructor.
	 * Nothing special about it
	 */
	public GraphOps(){}
	
	
	/**
	 * This Java method will process HTTP GET requests and returns a resource
	 * representation for a specified unique resourceID in the following
	 * formats:
	 * <ul>
	 * <li>RDF/XML</li>
	 * </ul>
	 */
	@GET
	@Path("/node")
	@Produces({MediaType.APPLICATION_XML })
	public Response getGraphNode(@PathParam(DOMAIN_ID_PARAM) String domainID, @QueryParam(NODE_ID_PARAM) String resourceID) {
		ServiceProvider provider = getProvider(domainID);
		Response rsp = null;
		if (resourceID == null || resourceID.equals("")) {
			rsp = Response.status(Response.Status.BAD_REQUEST).build();
			return rsp;
		}
		ResourceID id = new SimpleResourceID(resourceID);
		ResourceNode node = null;
		node = provider.getArastejuGate().startConversation().findResource(id.getQualifiedName());
		//If the resource does not exist, return a 404 response
		if (node == null) {
			rsp = Response.status(Response.Status.NOT_FOUND).build();
			return rsp;
		}
		// Get all direct associations
		Set<Statement> assocs = node.getAssociations();

		SemanticGraph graph = new DefaultSemanticGraph(assocs);
		final SemanticGraphIO io = new RdfXmlBinding();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			io.write(graph, out);
			String result = out.toString();
			rsp = Response.ok(result).build();
		} catch (Exception e) {
			log.error(
					"An "
							+ e.getClass().getName()
							+ " has been occured while processing the request for getResource."
							+ "An internal server error will be returned", e);
			rsp = Response.serverError().build();
		}
		return rsp;
	}


	/**
	 * This Java method will process a HTTP GET requests and returns a resource
	 * representation of the whole 'RB'-typed graph for the given domain
	 * formats:
	 * <ul>
	 * <li>RDF/XML</li>
	 * </ul>
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML })
	public Response getGraph(@PathParam(DOMAIN_ID_PARAM) String domainID) {
		ServiceProvider provider = getProvider(domainID);
		Response rsp = null;

		final List<SNClass> types = provider.getTypeManager().findAllTypes();
		final SemanticGraph graph = new DefaultSemanticGraph();
		for (SNClass type : types) {
			final List<ResourceNode> entities = provider.getArastejuGate().createQueryManager().findByType(type);
			for (ResourceNode entity : entities) {
				graph.merge(new DefaultSemanticGraph(entity));
			}
		}
		final SemanticGraphIO io = new RdfXmlBinding();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			io.write(graph, out);
			String result = out.toString();
			rsp = Response.ok(result).build();
		} catch (Exception e) {
			log.error(
					"An "
							+ e.getClass().getName()
							+ " has been occured while processing the request for getResources."
							+ "An internal server error will be returned", e);
			rsp = Response.serverError().build();
		}

		return rsp;
	}

	@PUT
	@Consumes({MediaType.APPLICATION_XML})
	public Response importGraph(@PathParam(DOMAIN_ID_PARAM) String domainID){
		return Response.ok().build();
	}
	

	
}
