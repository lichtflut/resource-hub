/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.ServiceContext;
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
public class CurrentOrganizationModel extends AbstractLoadableDetachableModel<RBEntity> {
	
	@SpringBean
	private EntityManager entityManager;
	
	@SpringBean 
	private ServiceContext context;
	
	@SpringBean
	private ModelingConversation conversation;
	
	// ----------------------------------------------------
	
	public CurrentOrganizationModel() {
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBEntity load() {
		RBUser user = context.getUser();
		if (user == null) {
			return null;
		}
		
		final ResourceNode userNode = conversation.resolve(SNOPS.id(user.getQualifiedName()));
		final SemanticNode person = SNOPS.fetchObject(userNode, RBSystem.IS_RESPRESENTED_BY);
		if (person == null || !person.isResourceNode()) {
			return null;
		}
		
		final SemanticNode org = SNOPS.fetchObject(person.asResource(), RB.IS_EMPLOYED_BY);
		if (org == null || !org.isResourceNode()) {
			return null;
		} else {
			return entityManager.find(org.asResource());
		}
	}
	
}
