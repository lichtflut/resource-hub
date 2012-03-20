/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.ResourceModel;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.webck.behaviors.TitleModifier;
import de.lichtflut.rb.webck.events.ModelChangeEvent;

/**
 * <p>
 *  Dialog for creation of a new Public Type Definition.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class CreatePublicTypeDefDialog extends AbstractCreateResourceDialog {

	/**
	 * @param id
	 */
	public CreatePublicTypeDefDialog(String id) {
		super(id);
		
		add(TitleModifier.title(new ResourceModel("global.dialogs.create-public-type-def.title")));
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(QualifiedName qn, AjaxRequestTarget target) {
		final TypeDefinition def = provider.getSchemaManager().
				prepareTypeDefinition(qn, qn.getSimpleName());
		send(getPage(), Broadcast.BREADTH, 
				new ModelChangeEvent<TypeDefinition>(def, ModelChangeEvent.PUBLIC_TYPE_DEFINITION));
	}

}
