/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.typesystem;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.arastreju.sge.model.ElementaryDataType;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.web.behaviors.ConditionalBehavior;
import de.lichtflut.rb.web.ck.components.EnumDropDownChoice;
import de.lichtflut.rb.web.models.ConditionalModel;

/**
 * <p>
 *  Panel for editing of a Resource Schema.
 * </p>
 *
 * <p>
 * 	Created Sep 22, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class SchemaEditorPanel extends Panel {
	
	/**
	 *  Constructor.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SchemaEditorPanel(final String id, final IModel<List<? extends PropertyRow>> model) {
		super(id, model);
		
		setOutputMarkupPlaceholderTag(true);
		setOutputMarkupId(true);
		
		final Form<?> form = new Form("form");
		form.setOutputMarkupId(true);
		form.add(new ListView<PropertyRow>("listView", model) {
			@Override
			protected void populateItem(ListItem<PropertyRow> item) {
				final PropertyRow row = item.getModelObject();
				
				item.add(new TextField("property", new PropertyModel(row, "propertyDescriptor")));

				item.add(new TextField("min", new PropertyModel(row, "min")));
				
				final PropertyModel unboundModel = new PropertyModel(row, "unbounded");
				final TextField maxField = new TextField("max", new PropertyModel(row, "max"));
				maxField.add(ConditionalBehavior.visibleIf(ConditionalModel.isFalse(unboundModel)));
				maxField.setOutputMarkupId(true);
				item.add(maxField);
				
				final CheckBox checkBox = new CheckBox("unbounded", unboundModel);
				checkBox.add(new AjaxFormComponentUpdatingBehavior("onclick") {
					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						target.add(maxField);
					}
				});
				item.add(checkBox);
				
				final EnumDropDownChoice<ElementaryDataType> dataTypeChoice = 
					new EnumDropDownChoice<ElementaryDataType>("dataType", 
						new PropertyModel(row, "dataType"),
						ElementaryDataType.values());
				
				final boolean isPrivateTD = !row.isTypeDefinitionPublic();
				dataTypeChoice.setEnabled(isPrivateTD);
				item.add(dataTypeChoice);
				
			}
		});
		
		form.add(new AjaxFallbackButton("save", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}
		});
		
		add(form);
		
	}
	
	// -----------------------------------------------------
	
	public abstract void onSave(final AjaxRequestTarget target);
	
	// -----------------------------------------------------
	
	protected String max(final PropertyDeclaration pa) {
		if (pa.getCardinality().isUnbound()) {
			return "unbound";
		} else {
			return "" + pa.getCardinality().getMaxOccurs();
		}
	}
	
}
