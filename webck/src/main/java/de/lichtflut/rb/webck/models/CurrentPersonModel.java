/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.security.User;

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
public abstract class CurrentPersonModel extends AbstractLoadableDetachableModel<RBEntity> {

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBEntity load() {
		User user = CurrentUserModel.currentUser();
		if (user == null) {
			return null;
		}
		final ResourceNode userNode = getServiceProvider().getResourceResolver().resolve(user.getAssociatedResource());
		final SemanticNode person = SNOPS.fetchObject(userNode, RBSystem.IS_RESPRESENTED_BY);
		if (person != null && person.isResourceNode()) {
			return getServiceProvider().getEntityManager().find(person.asResource());
		} else {
			return null;
		}
	}
	
	// ----------------------------------------------------
	
	public abstract ServiceProvider getServiceProvider();
	
}
