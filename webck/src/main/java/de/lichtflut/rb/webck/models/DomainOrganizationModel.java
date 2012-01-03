/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.api.DomainOrganizer;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBEntityReference;
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
public abstract class DomainOrganizationModel extends AbstractLoadableModel<RBEntityReference> {
	
	public DomainOrganizationModel() {
	}
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public RBEntityReference load() {
		final ResourceID org = organizer().getDomainOrganization();
		if (org != null) {
			final ResourceNode entity = getServiceProvider().getResourceResolver().resolve(org);
			return new RBEntityReference(entity);
		} else {
			return null;
		}
	}
	
	// ----------------------------------------------------
	
	public abstract ServiceProvider getServiceProvider();
	
	// ----------------------------------------------------
	
	private DomainOrganizer organizer() {
		return getServiceProvider().getDomainOrganizer();
	}

}
