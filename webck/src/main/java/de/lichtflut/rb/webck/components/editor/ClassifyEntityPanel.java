/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.editor;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.components.fields.ClassPickerField;

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

	private final IModel<RBEntity> entityModel;
	private final IModel<ResourceID> superClassModel;
	private final IModel<ResourceID> targetModel;
	
	// ----------------------------------------------------
	
	/**
	 * @param id - wicket:id
	 * @param entity - {@link RBEntity} which is to be displayed
	 */
	@SuppressWarnings("rawtypes")
	public ClassifyEntityPanel(final String id, final  IModel<RBEntity> entityModel, final IModel<ResourceID> targetModel) {
		super(id);
		
		this.entityModel = entityModel;
		this.targetModel = targetModel;
		this.superClassModel = new Model<ResourceID>();
		
		final ClassPickerField picker = new ClassPickerField("typePicker", targetModel, superClassModel);
		add(picker);
		
		add(new AjaxLink("suggest") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				picker.getDisplayComponent().search(target, "#!*");
			}
		});
		
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
		final RBEntity entity = entityModel.getObject();
		if (entity != null) {
			superClassModel.setObject(entity.getType());
		} 
	}

}
