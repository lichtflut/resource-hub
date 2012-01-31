/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config.selection;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNText;

import scala.actors.threadpool.Arrays;
import de.lichtflut.rb.webck.components.fields.ResourcePickerField;
import de.lichtflut.rb.webck.models.ConditionalModel;

/**
 * <p>
 *  Row for editing a query parameter of a selection.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class QueryParamRow extends Panel {

	/**
	 * @param id
	 * @param model
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public QueryParamRow(final String id, final IModel<QueryParamUIModel> model) {
		super(id, model);
		
		setOutputMarkupId(true);
		
		final ConditionalModel isRelation = new ConditionalModel() {
			@Override
			public boolean isFulfilled() {
				switch(model.getObject().getType()) {
				case SPECIAL_FIELD_VALUE:
				case ANY_FIELD_VALUE:
					return false;
				case ANY_FIELD_RELATION:
				case RESOURCE_TYPE:
				case SPECIAL_FIELD_RELATION:
					return true;
				default: 
					throw new IllegalStateException("Unknown paramter type: " + model.getObject().getType());
				}
			}
		};
		
		final ConditionalModel hasSpecificField = new ConditionalModel() {
			@Override
			public boolean isFulfilled() {
				switch(model.getObject().getType()) {
				case SPECIAL_FIELD_VALUE:
				case SPECIAL_FIELD_RELATION:
					return true;
				case ANY_FIELD_VALUE:
				case ANY_FIELD_RELATION:
				case RESOURCE_TYPE:
					return false;
				default: 
					throw new IllegalStateException("Unknown paramter type: " + model.getObject().getType());
				}
			}
		};
		
		final DropDownChoice<ParameterType> typeChooser = new DropDownChoice<ParameterType>("type", 
				new PropertyModel<ParameterType>(model, "type"),
				Arrays.asList(ParameterType.values()), new EnumChoiceRenderer<ParameterType>()) ;
		typeChooser.add(new OnChangeAjaxBehavior() {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(QueryParamRow.this);				
			}
		});
		add(typeChooser);
		
		final ResourcePickerField field = new ResourcePickerField("field", 
				new PropertyModel<ResourceID>(model, "field"));
		field.add(visibleIf(hasSpecificField));
		add(field);
		
		final TextField<SNText> valueInput = new TextField<SNText>("valueInput", 
				new PropertyModel<SNText>(model, "value"));
		valueInput.setType(SNText.class);
		valueInput.add(visibleIf(not(isRelation)));
		add(valueInput);
		
		
		final ResourcePickerField valuePicker = new ResourcePickerField("valuePicker", 
				new PropertyModel<ResourceID>(model, "value"));
		valuePicker.add(visibleIf(isRelation));
		add(valuePicker);
	}
	
}
