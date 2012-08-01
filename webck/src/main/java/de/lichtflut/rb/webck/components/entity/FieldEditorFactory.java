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
import de.lichtflut.rb.webck.models.HTMLSafeModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValueModel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
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

    public Component createField(RBFieldValueModel model, Datatype dataType) {
        switch(dataType) {
            case BOOLEAN:
                return createBooleanField(model);
            case RESOURCE:
                return createResourceField(model);
            case DATE:
                return createDateField(model);
            case INTEGER:
                return createTextField(model, BigInteger.class);
            case DECIMAL:
                return createTextField(model, BigDecimal.class);
            case STRING:
                return createTextField(model, String.class);
            case TEXT:
                return createTextArea(model);
            case RICH_TEXT:
                return createRichTextArea(model);
            case URI:
                return createURIField(model);
            default:
                throw new NotYetImplementedException("Datatype: " + dataType);
        }
    }

    // ----------------------------------------------------

    public Component createResourceField(RBFieldValueModel model) {
		final ResourceID typeConstraint = getTypeConstraint();
		return new EntityPickerField("valuefield", model, typeConstraint);
	}

    public Component createTextField(RBFieldValueModel model, Class<?> type) {
		final TextField field = new TextField("valuefield", model);
		field.setType(type);
		addValidator(model, field);
		return new Fragment("valuefield", "textInput", container).add(field);
	}

    public Component createTextArea(RBFieldValueModel model) {
		final TextArea<String> field = new TextArea<String>("valuefield", model);
		field.setType(String.class);
		addValidator(model, field);
		return new Fragment("valuefield", "textArea", container).add(field);
	}

    public Component createDateField(RBFieldValueModel model) {
		final DatePicker<Date> field = new DatePicker<Date>("valuefield", model, Date.class);
		addValidator(model, field);
		return new Fragment("valuefield", "textInput", container).add(field);
	}

    public Component createBooleanField(RBFieldValueModel model) {
		final CheckBox cb = new CheckBox("valuefield", model);
		return new Fragment("valuefield", "checkbox", container).add(cb);
	}

    public Component createRichTextArea(RBFieldValueModel model) {
		TextArea<String> field = new TextArea("valuefield", new HTMLSafeModel(model));
		field.add(new TinyMceBehavior());
		addValidator(model, field);
		return new Fragment("valuefield", "textArea", container).add(field);
	}

    public Component createURIField(RBFieldValueModel model){
		final TextField field = new TextField("valuefield", model);
		field.add(new UrlValidator());
		return new Fragment("valuefield", "textInput", container).add(field);
	}

    // ----------------------------------------------------

    private void addValidator(RBFieldValueModel model, final Component field) {
		Constraint constraint = model.getField().getConstraint();
		if((null != constraint) && (null != constraint.getLiteralConstraint())){
			field.add(new PatternValidator(model.getField().getConstraint().getLiteralConstraint()));
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

}
