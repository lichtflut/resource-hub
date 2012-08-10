/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.entity.RBEntity;
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
import org.apache.wicket.markup.html.form.upload.FileUploadField;
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

    // ----------------------------------------------------

    public FieldEditorFactory(WebMarkupContainer container) {
        this.container = container;
    }

    // ----------------------------------------------------

    public Component createField(RBFieldValueModel valueModel) {
        return createField(valueModel, true);
    }

    public Component createField(RBFieldValueModel valueModel, boolean allowEmbedding) {
        RBField field = valueModel.getField();
        switch(field.getDataType()) {
            case RESOURCE:
                return createResourceField(valueModel, allowEmbedding);
            case BOOLEAN:
                return createBooleanField(field, valueModel);
            case DATE:
                return createDateField(field, valueModel);
            case INTEGER:
                return createTextField(field, valueModel, BigInteger.class);
            case DECIMAL:
                return createTextField(field, valueModel, BigDecimal.class);
            case STRING:
                return createTextField(field, valueModel, String.class);
            case TEXT:
                return createTextArea(field, valueModel);
            case RICH_TEXT:
                return createRichTextArea(field, valueModel);
            case URI:
                return createURIField(field, valueModel);
            default:
                throw new NotYetImplementedException("Datatype: " + field.getDataType());
        }
    }

    // ----------------------------------------------------

    public Component createResourceField(RBFieldValueModel model, boolean allowEmbedding) {
        RBField fieldDefinition = model.getField();
        if (allowEmbedding && fieldDefinition.isEmbedded()) {
            return new EmbeddedReferencePanel("valuefield", model, this);
        } else {
            Object object = model.getObject();
            if (object instanceof RBEntity) {
                throw new IllegalStateException("Unexpected class RBEntity for " + object);
            }
            final ResourceID typeConstraint = getTypeConstraint(fieldDefinition);
            return new EntityPickerField("valuefield", model, typeConstraint);

        }
	}

    public Component createTextField(RBField fieldDefinition, IModel model, Class<?> type) {
		final TextField field = new TextField("valuefield", model);
		field.setType(type);
		addValidator(field, fieldDefinition);
		return new Fragment("valuefield", "textInput", container).add(field);
	}

    public Component createTextArea(RBField fieldDefinition, IModel model) {
		final TextArea<String> field = new TextArea<String>("valuefield", model);
		field.setType(String.class);
		addValidator(field, fieldDefinition);
		return new Fragment("valuefield", "textArea", container).add(field);
	}

    public Component createDateField(RBField fieldDefinition, IModel model) {
		final DatePicker<Date> field = new DatePicker<Date>("valuefield", model, Date.class);
		addValidator(field, fieldDefinition);
		return new Fragment("valuefield", "textInput", container).add(field);
	}

    public Component createBooleanField(RBField fieldDefinition, IModel model) {
		final CheckBox cb = new CheckBox("valuefield", model);
		return new Fragment("valuefield", "checkbox", container).add(cb);
	}

    public Component createRichTextArea(RBField fieldDefinition, IModel model) {
		TextArea<String> field = new TextArea("valuefield", new HTMLSafeModel(model));
		field.add(new TinyMceBehavior());
		addValidator(field, fieldDefinition);
		return new Fragment("valuefield", "textArea", container).add(field);
	}

    public Component createURIField(RBField fieldDefinition, IModel model){
		final TextField field = new TextField("valuefield", model);
		field.add(new UrlValidator());
		return new Fragment("valuefield", "textInput", container).add(field);
	}

	public Component createFileChooser(final ListItem<RBFieldValueModel> item){
		FileUploadField fileUploadField = new FileUploadField("valuefield", item.getModelObject());
		return new Fragment("valuefield", "fileUpload", container).add(fileUploadField);
	}

	// ----------------------------------------------------

    private void addValidator(Component component, RBField fieldDefinition) {
		Constraint constraint = fieldDefinition.getConstraint();
		if((null != constraint) && (null != constraint.getLiteralConstraint())){
			component.add(new PatternValidator(fieldDefinition.getConstraint().getLiteralConstraint()));
		}
	}

	/**
	 * Extracts the resourceTypeConstraint of this {@link de.lichtflut.rb.core.entity.RBField}.
	 * @return the resourceTypeConstraint as an {@link org.arastreju.sge.model.ResourceID}
	 */
	private ResourceID getTypeConstraint(RBField fieldDefinition) {
		if(fieldDefinition.getDataType().equals(Datatype.RESOURCE)){
			return fieldDefinition.getConstraint().getReference().asResource();
		}
		return null;
	}

}
