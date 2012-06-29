/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.behaviors.TitleModifier;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  Dialog for creation of a new rdf:Property.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class CreatePropertyDialog extends AbstractCreateResourceDialog {

	@SpringBean
	private TypeManager typeManager;
	
	/**
	 * @param id
	 */
	public CreatePropertyDialog(String id) {
		super(id);
		
		add(TitleModifier.title(new ResourceModel("global.dialogs.create-property.title")));
		
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(QualifiedName qn, AjaxRequestTarget target) {
		final SNProperty property = typeManager.createProperty(qn);
		send(getPage(), Broadcast.BREADTH, 
				new ModelChangeEvent<SNProperty>(property, ModelChangeEvent.PROPERTY));
	}

}
