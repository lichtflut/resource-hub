/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.ResourceModel;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.webck.behaviors.TitleModifier;
import de.lichtflut.rb.webck.events.ModelChangeEvent;

/**
 * <p>
 *  Dialog for creation of a new rb:Type.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class CreateTypeDialog extends AbstractCreateResourceDialog {

	/**
	 * @param id
	 */
	public CreateTypeDialog(String id) {
		super(id);
		
		add(TitleModifier.title(new ResourceModel("global.dialogs.create-type.title")));
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(QualifiedName qn, AjaxRequestTarget target) {
		final SNClass type = provider.getTypeManager().createType(qn);
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<SNClass>(type, ModelChangeEvent.TYPE));
	}

}
