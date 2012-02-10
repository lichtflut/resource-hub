/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

/**
 * <p>
 *  Model for the currently logged in user. 
 * </p>
 *
 * <p>
 * 	Created Dec 8, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class CurrentPersonModel extends AbstractLoadableDetachableModel<RBEntity> {
	
	@SpringBean
	private ServiceProvider provider;
	
	// ----------------------------------------------------
	
	public CurrentPersonModel() {
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBEntity load() {
		ResourceID userID = CurrentUserModel.currentUserID();
		if (userID == null) {
			return null;
		}
		
		final ResourceNode userNode = provider.getResourceResolver().resolve(userID);
		final SemanticNode person = SNOPS.fetchObject(userNode, RBSystem.IS_RESPRESENTED_BY);
		if (person != null && person.isResourceNode()) {
			return provider.getEntityManager().find(person.asResource());
		} else {
			return null;
		}
	}
	
}
