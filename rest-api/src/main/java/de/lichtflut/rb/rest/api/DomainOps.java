/**
 * 
 */
package de.lichtflut.rb.rest.api;

import javax.ws.rs.Path;

/**
 * @author nbleisch
 *
 */
@Path("/domain/{" + RBServiceEndpoint.DOMAIN_ID_PARAM + "}/")
public class DomainOps extends RBServiceEndpoint {

}
