/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.relationships;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.defaultButtonIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.enableIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNull;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntityReference;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
import de.lichtflut.rb.webck.components.fields.PropertyPickerField;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;

/**
 * <p>
 *  Panel for creation of new relationships.
 * </p>
 *
 * <p>
 * 	Created Dec 9, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class CreateRelationshipPanel extends Panel {

	private IModel<RBEntityReference> entityModel = new Model<RBEntityReference>();
	
	private IModel<ResourceID> predicateModel = new Model<ResourceID>();
	
	// ----------------------------------------------------
	
	/**
	 * @param id
	 * @param model
	 */
	@SuppressWarnings("rawtypes")
	public CreateRelationshipPanel(final String id) {
		super(id);
		
		final Form form = new Form("form");
		form.add(new FeedbackPanel("feedback"));
		
		final EntityPickerField entityPicker = new EntityPickerField("entityPicker", entityModel, RB.ENTITY);
		entityPicker.add(enableIf(isNull(entityModel)));
		entityPicker.getSuggestLink().setVisible(false);
		form.add(entityPicker);
		
		final PropertyPickerField predicatePicker = new PropertyPickerField("predicatePicker", predicateModel);
		predicatePicker.add(visibleIf(not(isNull(entityModel))));
		predicatePicker.setRequired(true);
		predicatePicker.getSuggestLink().setVisible(false);
		form.add(predicatePicker);
		
		final AjaxButton selectButton = new RBStandardButton("select") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				target.focusComponent(predicatePicker.getDisplayComponent());
				target.add(form);
			}
		};
		selectButton.add(defaultButtonIf(isNull(entityModel)));
		selectButton.add(visibleIf(isNull(entityModel)));
		form.add(selectButton);
		
		final AjaxButton createButton = new RBStandardButton("create") {
			
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				createRelationshipTo(entityModel.getObject(), predicateModel.getObject());
				resetModels();
				target.add(form);
			}
		};
		createButton.add(defaultButtonIf(not(isNull(entityModel))));
		form.add(createButton);
		
		final AjaxButton cancelButton = new RBCancelButton("cancel") {
			
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				resetModels();
				target.add(form);
			}
		};
		form.add(cancelButton);
		
		add(form);
		
	}
	
	// ----------------------------------------------------
	
	/**
	 * @param object
	 * @param predicate
	 */
	protected abstract void createRelationshipTo(final RBEntityReference object, ResourceID predicate);

	// ----------------------------------------------------
	
	protected void resetModels() {
		entityModel.setObject(null);
		predicateModel.setObject(null);
	}
	
}
