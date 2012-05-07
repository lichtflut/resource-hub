/**
 * 
 */
package de.lichtflut.rb.rest.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * @author nbleisch
 *
 */
@Path("/domain/{" + RBServiceEndpoint.DOMAIN_ID_PARAM + "}/")
public class DomainOps extends RBServiceEndpoint {

	@DELETE
	public Response deleteDomain(@PathParam(DOMAIN_ID_PARAM) String domainID){
		ServiceProvider provider = getProvider(domainID);
		return null;
		
	}
	
	@POST
	public Response createDomain(@PathParam(DOMAIN_ID_PARAM) String domainID){
		ServiceProvider provider = getProvider(domainID);
		return null;
		
	}
	
}
