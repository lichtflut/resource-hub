/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.webck.behaviors.TitleModifier;
import de.lichtflut.rb.webck.events.ModelChangeEvent;

/**
 * <p>
 *  Dialog for creation of a new Public {@link Constraint}.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class CreatePublicConstraintDialog extends AbstractCreateResourceDialog {

	@SpringBean
	private SchemaManager schemaManager;
	
	/**
	 * @param id
	 */
	public CreatePublicConstraintDialog(String id) {
		super(id);
		
		add(TitleModifier.title(new ResourceModel("global.dialogs.create-public-type-def.title")));
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(QualifiedName qn, AjaxRequestTarget target) {
		final Constraint constraint = schemaManager.
				prepareConstraint(qn, qn.getSimpleName());
		send(getPage(), Broadcast.BREADTH, 
				new ModelChangeEvent<Constraint>(constraint, ModelChangeEvent.PUBLIC_CONSTRAINT));
	}

}
