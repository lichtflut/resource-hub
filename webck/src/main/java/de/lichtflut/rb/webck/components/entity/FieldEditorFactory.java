/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.UrlValidator;
import org.arastreju.sge.model.ResourceID;
import org.odlabs.wiquery.ui.datepicker.DatePicker;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.VisualizationInfo;
import de.lichtflut.rb.core.services.impl.FileServiceImpl;
import de.lichtflut.rb.webck.behaviors.TinyMceBehavior;
import de.lichtflut.rb.webck.components.fields.AjaxEditableDataField;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
import de.lichtflut.rb.webck.models.HTMLSafeModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValueModel;

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

	public FieldEditorFactory(final WebMarkupContainer container) {
		this.container = container;
	}

	// ----------------------------------------------------

	public Component createField(final RBFieldValueModel valueModel) {
		return createField(valueModel, true);
	}

	public Component createField(final RBFieldValueModel valueModel, final boolean allowEmbedding) {
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
		case FILE:
			return createFileChooser(field, valueModel);
		default:
			throw new NotYetImplementedException("Datatype: " + field.getDataType());
		}
	}

	// ----------------------------------------------------

	public Component createResourceField(final RBFieldValueModel model, final boolean allowEmbedding) {
		RBField fieldDefinition = model.getField();
		Object object = model.getObject();
		if (object instanceof RBEntity) {
			throw new IllegalStateException("Unexpected class RBEntity for " + object);
		}
		final ResourceID typeConstraint = getTypeConstraint(fieldDefinition);
		return new EntityPickerField("valuefield", model, typeConstraint);
	}

	public Component createTextField(final RBField fieldDefinition, final IModel model, final Class<?> type) {
		final TextField field = new TextField("valuefield", model);
		field.setType(type);
		addValidator(field, fieldDefinition);
		addStyle(field, fieldDefinition.getVisualizationInfo());
		return new Fragment("valuefield", "textInput", container).add(field);
	}

	public Component createTextArea(final RBField fieldDefinition, final IModel model) {
		final TextArea<String> field = new TextArea<String>("valuefield", model);
		field.setType(String.class);
		addValidator(field, fieldDefinition);
		addStyle(field, fieldDefinition.getVisualizationInfo());
		return new Fragment("valuefield", "textArea", container).add(field);
	}

	public Component createDateField(final RBField fieldDefinition, final IModel model) {
		final DatePicker<Date> field = new DatePicker<Date>("valuefield", model, Date.class);
		addValidator(field, fieldDefinition);
		addStyle(field, fieldDefinition.getVisualizationInfo());
		return new Fragment("valuefield", "textInput", container).add(field);
	}

	public Component createBooleanField(final RBField fieldDefinition, final IModel model) {
		final CheckBox cb = new CheckBox("valuefield", model);
		addStyle(cb, fieldDefinition.getVisualizationInfo());
		return new Fragment("valuefield", "checkbox", container).add(cb);
	}

	public Component createRichTextArea(final RBField fieldDefinition, final IModel model) {
		TextArea<String> field = new TextArea("valuefield", new HTMLSafeModel(model));
		field.add(new TinyMceBehavior());
		addValidator(field, fieldDefinition);
		addStyle(field, fieldDefinition.getVisualizationInfo());
		return new Fragment("valuefield", "textArea", container).add(field);
	}

	public Component createURIField(final RBField fieldDefinition, final IModel model){
		final TextField field = new TextField("valuefield", model);
		field.add(new UrlValidator());
		return new Fragment("valuefield", "textInput", container).add(field);
	}

	public Component createFileChooser(final RBField fieldDefinition, final IModel model){
		AjaxEditableDataField dataField = new AjaxEditableDataField("valuefield", model){
			/**
			 * {@inheritDoc}
			 */
			@Override
			protected FormComponent newEditor(final MarkupContainer parent, final String componentId, final IModel model) {
				FileUploadField uploadField = new FileUploadField(componentId, model);
				uploadField.add(super.newEditor(parent, componentId, model));
				uploadField.setOutputMarkupId(true);
				uploadField.setVisible(false);
				//				uploadField.add(new EditorAjaxBehavior());
				return uploadField;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			protected WebMarkupContainer newLabel(final MarkupContainer parent, final String componentId, final IModel model) {
				WebMarkupContainer container = new WebMarkupContainer("container");
				String simpleName = "";
				if(!(null == model)){
					if(!(null == model.getObject())){
						simpleName = FileServiceImpl.getSimpleName(model.getObject().toString());
					}
				}
				Label label = new Label(componentId, simpleName);
				label.setEscapeModelStrings(false);
				container.add(label);
				container.add(new Label("additionalInfo", Model.of(" (click to edit)")));

				container.setOutputMarkupId(true);
				container.add(new LabelAjaxBehavior(getLabelAjaxEvent()));
				return container;
			}
		};
		dataField.setOutputMarkupId(true);
		addStyle(dataField, fieldDefinition.getVisualizationInfo());
		return new Fragment("valuefield", "fileUpload", container).add(dataField);
	}

	// ----------------------------------------------------

	private void addValidator(final Component component, final RBField fieldDefinition) {
		Constraint constraint = fieldDefinition.getConstraint();
		if((null != constraint) && (null != constraint.getLiteralConstraint())){
			component.add(new PatternValidator(fieldDefinition.getConstraint().getLiteralConstraint()));
		}
	}

	/**
	 * Extracts the resourceTypeConstraint of this {@link de.lichtflut.rb.core.entity.RBField}.
	 * @return the resourceTypeConstraint as an {@link org.arastreju.sge.model.ResourceID}
	 */
	private ResourceID getTypeConstraint(final RBField fieldDefinition) {
		if(fieldDefinition.getDataType().equals(Datatype.RESOURCE)){
			return fieldDefinition.getConstraint().getTypeConstraint().asResource();
		}
		return null;
	}

	private void addStyle(final Component comp, final VisualizationInfo visualizationInfo) {
		if (!Strings.isEmpty(visualizationInfo.getStyle())) {
			comp.add(new AttributeAppender("style", Model.of(visualizationInfo.getStyle()), " "));
		}
	}

}
