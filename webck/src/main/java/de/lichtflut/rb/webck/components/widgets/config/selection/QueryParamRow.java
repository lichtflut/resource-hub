/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config.selection;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.*;

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
import de.lichtflut.rb.webck.components.fields.ClassPickerField;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
import de.lichtflut.rb.webck.components.fields.PropertyPickerField;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

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
		
		final IModel<ParameterType> typeModel = new DerivedModel<ParameterType, QueryParamUIModel>(model) {
			@Override
			protected ParameterType derive(QueryParamUIModel original) {
				return original.getType();
			}
		};
		
		final ConditionalModel<ParameterType> isType = new IsTypeCondition(typeModel);
		final ConditionalModel<ParameterType> isRelation = new IsRelationCondition(typeModel);
		final ConditionalModel hasSpecificField = new HasSpecificFieldCondition(typeModel);
		
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
		
		final PropertyPickerField field = 
				new PropertyPickerField("field", new PropertyModel<ResourceID>(model, "field"));
		field.add(visibleIf(hasSpecificField));
		add(field);
		
		final TextField<SNText> valueInput = new TextField<SNText>("valueInput", 
				new PropertyModel<SNText>(model, "value"));
		valueInput.setType(SNText.class);
		valueInput.add(visibleIf(not(or(isRelation, isType))));
		add(valueInput);
		
		final ClassPickerField typePicker = new ClassPickerField("typePicker", 
				new PropertyModel<ResourceID>(model, "value"));
		typePicker.add(visibleIf(isType));
		add(typePicker);
		
		final EntityPickerField relationPicker = new EntityPickerField("relationPicker", 
				new PropertyModel<ResourceID>(model, "value"));
		relationPicker.add(visibleIf(isRelation));
		add(relationPicker);
	}
	
	// ----------------------------------------------------
	
	private final class IsTypeCondition extends ConditionalModel<ParameterType> {

		private IsTypeCondition(IModel<ParameterType> model) {
			super(model);
		}

		@Override
		public boolean isFulfilled() {
			switch(getObject()) {
			case SPECIAL_FIELD_VALUE:
			case ANY_FIELD_VALUE:
			case ANY_FIELD_RELATION:
			case SPECIAL_FIELD_RELATION:
				return false;
			case RESOURCE_TYPE:
				return true;
			default: 
				throw new IllegalStateException("Unknown paramter type: " + getObject());
			}
		}
	}
	
	private final class IsRelationCondition extends ConditionalModel<ParameterType> {

		private IsRelationCondition(IModel<ParameterType> model) {
			super(model);
		}

		@Override
		public boolean isFulfilled() {
			switch(getObject()) {
			case SPECIAL_FIELD_VALUE:
			case ANY_FIELD_VALUE:
			case RESOURCE_TYPE:
				return false;
			case ANY_FIELD_RELATION:
			case SPECIAL_FIELD_RELATION:
				return true;
			default: 
				throw new IllegalStateException("Unknown paramter type: " + getObject());
			}
		}
	}
	
	private final class HasSpecificFieldCondition extends ConditionalModel<ParameterType> {

		private HasSpecificFieldCondition(IModel<ParameterType> model) {
			super(model);
		}

		@Override
		public boolean isFulfilled() {
			switch(getObject()) {
			case SPECIAL_FIELD_VALUE:
			case SPECIAL_FIELD_RELATION:
				return true;
			case ANY_FIELD_VALUE:
			case ANY_FIELD_RELATION:
			case RESOURCE_TYPE:
				return false;
			default: 
				throw new IllegalStateException("Unknown paramter type: " + getObject());
			}
		}
	}
	
}
