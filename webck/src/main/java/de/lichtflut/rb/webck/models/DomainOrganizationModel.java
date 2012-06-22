/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

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
public class DomainOrganizationModel extends AbstractLoadableDetachableModel<ResourceID> {
	
	@SpringBean
	private DomainOrganizer domainOrganizer;
	
	// ----------------------------------------------------
	
	public DomainOrganizationModel() {
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public ResourceID load() {
		return domainOrganizer.getDomainOrganization();
	}
	
}
