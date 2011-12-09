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

import de.lichtflut.rb.core.entity.RBEntityReference;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
import de.lichtflut.rb.webck.components.fields.ResourcePickerField;

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
		
		final EntityPickerField entityPicker = new EntityPickerField("entityPicker", entityModel);
		entityPicker.add(enableIf(isNull(entityModel)));
		form.add(entityPicker);
		
		//final ResourcePickerField predicatePicker = new ResourcePickerField("predicatePicker", predicateModel, RDF.PROPERTY);
		final ResourcePickerField predicatePicker = new ResourcePickerField("predicatePicker", predicateModel);
		predicatePicker.add(visibleIf(not(isNull(entityModel))));
		form.add(predicatePicker);
		
		final AjaxButton selectButton = new AjaxButton("select") {
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		};
		selectButton.add(defaultButtonIf(isNull(entityModel)));
		selectButton.add(visibleIf(isNull(entityModel)));
		form.add(selectButton);
		
		final AjaxButton createButton = new AjaxButton("create") {
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				createRelationshipTo(entityModel.getObject(), predicateModel.getObject());
				resetModels();
				target.add(form);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		};
		createButton.add(defaultButtonIf(not(isNull(entityModel))));
		form.add(createButton);
		
		final AjaxButton cancelButton = new AjaxButton("cancel") {
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				resetModels();
				target.add(form);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		};
		cancelButton.setDefaultFormProcessing(false);
		form.add(cancelButton);
		
		add(form);
		
	}
	
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
