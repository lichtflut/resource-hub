/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.editor;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.components.fields.ClassPickerField;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

/**
 * <p>
 *  Panel for classification of an {@link RBEntity}.
 * </p>
 *
 * <p>
 * 	Created Dec 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ClassifyEntityPanel extends Panel {

	private final IModel<ResourceID> superClassModel;
	private final IModel<ResourceID> targetModel;
	
	// ----------------------------------------------------
	
	/**
	 * @param id - wicket:id
	 * @param entityModel - the entity to be classified.
	 */
	public ClassifyEntityPanel(final String id, final  IModel<RBEntity> entityModel, final IModel<ResourceID> targetModel) {
		super(id);
		
		this.targetModel = targetModel;
		this.superClassModel = new DerivedModel<ResourceID, RBEntity>(entityModel) {
			@Override
			protected ResourceID derive(RBEntity original) {
				return original.getType();
			}
		};
		
		final ClassPickerField picker = new ClassPickerField("typePicker", targetModel, superClassModel);
		add(picker);
		
		setVisible(false);
	}
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	protected void onConfigure() {
		super.onConfigure();
		targetModel.setObject(null);
	}

}
