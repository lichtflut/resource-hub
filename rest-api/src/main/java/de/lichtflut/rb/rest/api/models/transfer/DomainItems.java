/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.models.transfer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 *  TODO: To document
 * </p>
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created Jan 23, 2013
 */
public class DomainItems extends RestBaseModel{

	private Collection<Domain> domains = new ArrayList<Domain>();

	/**
	 * @return the domains
	 */
	public Collection<Domain> getDomains() {
		return domains;
	}

	/**
	 * @param domains the domains to set
	 */
	public void setDomains(Collection<Domain> domains) {
		this.domains = domains;
	}
	
}
