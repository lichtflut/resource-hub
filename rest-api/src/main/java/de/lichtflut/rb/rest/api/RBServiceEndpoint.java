/**
 * 
 */
package de.lichtflut.rb.rest.api;

import org.arastreju.sge.security.User;
import org.springframework.beans.factory.annotation.Autowired;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.services.ServiceProviderFactory;

/**
 * @author nbleisch
 *
 */
public abstract class RBServiceEndpoint {

	static final String DOMAIN_ID_PARAM = "domainID";
	static final String ROOT_USER = "root";
	
	/**
	 * An instance of {@link ServiceProvider} which offers several necessary
	 * RB-Services to get this service running.
	 */
	@Autowired
	private ServiceProviderFactory providerFactory;

	
	/**
	 * 
	 * @param domainID
	 * @return
	 */
	protected ServiceProvider getProvider(String domainID) {
		ServiceProvider rootProvider = providerFactory.create();
		User userNode = rootProvider.getArastejuGate().getIdentityManagement().findUser(ROOT_USER);
		ServiceProvider provider = providerFactory.create();
		provider.getContext().setDomain(domainID);
		provider.getContext().setUser(userNode);
		return provider;
	}
	
}
