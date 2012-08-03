/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.webck.behaviors.TinyMceBehavior;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.HTMLSafeModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValueModel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.UrlValidator;
import org.arastreju.sge.model.ResourceID;
import org.odlabs.wiquery.ui.datepicker.DatePicker;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * <p>
 *  Panel containing one field of a entity, which consists of
 *  <ul>
 *  	<li> a label </li>
 *  	<li> one or more values </li>
 *  </ul>
 *  for editing.
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FieldEditorFactory implements Serializable {

    private final WebMarkupContainer container;
    private final IModel<RBField> fieldModel;

    // ----------------------------------------------------

    public FieldEditorFactory(WebMarkupContainer container, IModel<RBField> fieldModel) {
        this.container = container;
        this.fieldModel = fieldModel;
    }

    // ----------------------------------------------------

    public Component createField(final ListItem<RBFieldValueModel> item, final Datatype dataType) {
        switch(dataType) {
            case BOOLEAN:
                return createBooleanField(item);
            case RESOURCE:
                return createResourceField(item);
            case DATE:
                return createDateField(item);
            case INTEGER:
                return createTextField(item, BigInteger.class);
            case DECIMAL:
                return createTextField(item, BigDecimal.class);
            case STRING:
                return createTextField(item, String.class);
            case TEXT:
                return createTextArea(item);
            case RICH_TEXT:
                return createRichTextArea(item);
            case URI:
                return createURIField(item);
            default:
                throw new NotYetImplementedException("Datatype: " + dataType);
        }
    }

    // ----------------------------------------------------

    public Component createResourceField(final ListItem<RBFieldValueModel> item) {
		final ResourceID typeConstraint = getTypeConstraint();
		return new EntityPickerField("valuefield", item.getModelObject(), typeConstraint);
	}

    public Component createTextField(final ListItem<RBFieldValueModel> item, Class<?> type) {
		final TextField field = new TextField("valuefield", item.getModelObject());
		field.setType(type);
		addValidator(item, field);
		return new Fragment("valuefield", "textInput", container).add(field);
	}

    public Component createTextArea(final ListItem<RBFieldValueModel> item) {
		final TextArea<String> field = new TextArea<String>("valuefield", item.getModelObject());
		field.setType(String.class);
		addValidator(item, field);
		return new Fragment("valuefield", "textArea", container).add(field);
	}

    public Component createDateField(final ListItem<RBFieldValueModel> item) {
		final DatePicker<Date> field = new DatePicker<Date>("valuefield", item.getModelObject(), Date.class);
		addValidator(item, field);
		return new Fragment("valuefield", "textInput", container).add(field);
	}

    public Component createBooleanField(ListItem<RBFieldValueModel> item) {
		final CheckBox cb = new CheckBox("valuefield", item.getModelObject());
		return new Fragment("valuefield", "checkbox", container).add(cb);
	}

    public Component createRichTextArea(ListItem<RBFieldValueModel> item) {
		TextArea<String> field = new TextArea("valuefield", new HTMLSafeModel(item.getModelObject()));
		field.add(new TinyMceBehavior());
		addValidator(item, field);
		return new Fragment("valuefield", "textArea", container).add(field);
	}

    public Component createURIField(ListItem<RBFieldValueModel> item){
		final TextField field = new TextField("valuefield", item.getModelObject());
		field.add(new UrlValidator());
		return new Fragment("valuefield", "textInput", container).add(field);
	}

    // ----------------------------------------------------

    private void addValidator(final ListItem<RBFieldValueModel> item, final Component field) {
		Constraint constraint = item.getModelObject().getField().getConstraint();
		if((null != constraint) && (null != constraint.getLiteralConstraint())){
			field.add(new PatternValidator(item.getModelObject().getField().getConstraint().getLiteralConstraint()));
		}
	}

	/**
	 * Extracts the resourceTypeConstraint of this {@link de.lichtflut.rb.core.entity.RBField}.
	 * @return the resourceTypeConstraint as an {@link org.arastreju.sge.model.ResourceID}
	 */
	private ResourceID getTypeConstraint() {
		final RBField field = getField();
		if(field.getDataType().equals(Datatype.RESOURCE)){
			return field.getConstraint().getReference().asResource();
		}
		return null;
	}
	
	private RBField getField() {
		return fieldModel.getObject();
	}
	
	// -- INNER CLASSES -----------------------------------
	
	private class IsBeneathFormConditional extends ConditionalModel {
		@Override
		public boolean isFulfilled() {
			return container.findParent(Form.class) != null;
		}
	}

}
