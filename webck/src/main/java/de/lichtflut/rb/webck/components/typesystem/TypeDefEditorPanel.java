/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.arastreju.sge.model.ElementaryDataType;

import de.lichtflut.rb.webck.components.EnumDropDownChoice;

/**
 * 
 * <p>
 *  Panel for editing a Type Definition.
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class TypeDefEditorPanel extends Panel {
	
	/**
	 *  Constructor.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TypeDefEditorPanel(final String id, final IModel<PropertyRow> model) {
		super(id, model);
		
		setOutputMarkupPlaceholderTag(true);
		setOutputMarkupId(true);
		
		final Form<?> form = new Form("form");
		form.setOutputMarkupId(true);
				
		final TextField<String> nameField = 
			new TextField<String>("name", new PropertyModel<String>(model, "displayName"));
		form.add(nameField);
		
		final EnumDropDownChoice<ElementaryDataType> dataTypeChoice = 
			new EnumDropDownChoice<ElementaryDataType>("dataType", 
				new PropertyModel(model, "dataType"),
				ElementaryDataType.values());
		form.add(dataTypeChoice);
		
		form.add(new ConstraintsEditorPanel("constraints", model));
		
		form.add(new AjaxFallbackButton("save", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onSave(target);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}
		});
		
		add(form);
		
	}
	
	// -----------------------------------------------------
	
	public abstract void onSave(final AjaxRequestTarget target);
	
}
