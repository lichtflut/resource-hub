package de.lichtflut.rb.rest.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.model.DefaultSemanticGraph;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.lichtflut.rb.core.services.ServiceProvider;

@Component
@Path("/resources")
public class ResourceService {

	/**
	 * An instance of {@link ServiceProvider} which offers several necessary RB-Services
	 * to get this service running.
	 */
	@Autowired
	private ServiceProvider provider;
	
	
	/**
	 * TODO: This is just for debug purposes and can be removed if the spring config is
	 * set up properly
	 * @param provider
	 */
	@Autowired
	public void setServiceProvider(ServiceProvider provider){
		this.provider = provider;
	}

	
	
	/** This Java method will process HTTP GET requests
	 * and returns a resource representation for a specified unique resourceID
	 * in the following formats:
	 * <ul>
	 * 	<li>RDF/XML</li>
	 * <li>JSON</li>
	 * </ul>
	 */
    @GET
    @Path("/{resourceID}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getResource(@PathParam("resourceID") String resourceID){
    	Response rsp = null;
    	//Mock this
    	resourceID = "http://lichtflut.de#hasEmail";
    	ResourceID id = new SimpleResourceID(resourceID);

    	ResourceNode node = provider.getResourceResolver().resolve(id);
    	Set<Statement> assocs = node.getAssociations();
    	
    	SemanticGraph graph = new DefaultSemanticGraph(assocs);
    	final SemanticGraphIO io = new RdfXmlBinding();
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	try {
			io.write(graph, out);
		} catch (SemanticIOException e) {
			e.printStackTrace();
			rsp = Response.serverError().build();
		} catch (IOException e) {
			e.printStackTrace();
			rsp = Response.serverError().build();
		}
    	String result = out.toString();
    	
    	rsp = Response.ok(result).build();
		return rsp;
	}
	
	
	
}
