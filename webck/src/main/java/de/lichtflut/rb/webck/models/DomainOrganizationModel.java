/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableModel;

/**
 * <p>
 *  Model for the domain's organization.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class DomainOrganizationModel extends AbstractLoadableModel<ResourceID> {
	
	public DomainOrganizationModel() {
	}
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public ResourceID load() {
		return organizer().getDomainOrganization();
	}
	
	// ----------------------------------------------------
	
	public abstract ServiceProvider getServiceProvider();
	
	// ----------------------------------------------------
	
	private DomainOrganizer organizer() {
		return getServiceProvider().getDomainOrganizer();
	}

}
