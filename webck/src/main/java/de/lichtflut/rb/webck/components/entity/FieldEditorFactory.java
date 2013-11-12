/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.components.entity;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.VisualizationInfo;
import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.behaviors.TitleModifier;
import de.lichtflut.rb.webck.components.fields.AjaxEditableUploadField;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
import de.lichtflut.rb.webck.components.fields.date.DatePicker;
import de.lichtflut.rb.webck.components.rteditor.RichTextBehavior;
import de.lichtflut.rb.webck.models.DateModel;
import de.lichtflut.rb.webck.models.fields.FileUploadModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValueModel;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.UrlValidator;
import org.arastreju.sge.eh.meta.NotYetImplementedException;
import org.arastreju.sge.model.ResourceID;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

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
		Component field = createField(valueModel, true);
		if (valueModel.getFieldValue().isInherited()) {
			field.setEnabled(false);
			field.add(CssModifier.appendClass("inherited"));
			field.add(TitleModifier.title(new ResourceModel("label.property-is-inherited")));
		}
		return field;
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
				return createFileUploadField(field, valueModel);
			default:
				throw new NotYetImplementedException("Datatype: " + field.getDataType());
		}
	}

	// ----------------------------------------------------

	public Component createResourceField(final RBFieldValueModel model, final boolean allowEmbedding) {
		RBField fieldDefinition = model.getField();
		Object sn = model.getObject();
		if (sn instanceof RBEntity) {
			throw new IllegalStateException("Unexpected class RBEntity for " + sn);
		}
		final ResourceID typeConstraint = getTypeConstraint(fieldDefinition);
		return new EntityPickerField("valuefield", (IModel) model, typeConstraint);
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
		final DatePicker field = new DatePicker("valuefield", new DateModel((RBFieldValueModel) model));
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
		TextArea<String> field = new TextArea("valuefield", model);
		field.add(new RichTextBehavior(RichTextBehavior.Type.STANDARD));
		addValidator(field, fieldDefinition);
		addStyle(field, fieldDefinition.getVisualizationInfo());
		return new Fragment("valuefield", "textArea", container).add(field);
	}

	public Component createURIField(final RBField fieldDefinition, final IModel model){
		final TextField field = new TextField("valuefield", model);
		field.add(new UrlValidator());
		return new Fragment("valuefield", "textInput", container).add(field);
	}

	public Component createFileUploadField(final RBField fieldDefinition, final IModel model){
		IModel<String> prefix = Model.of(fieldDefinition.getPredicate().getQualifiedName().toURI());
		final FileUploadModel uploadModel = new FileUploadModel(model, prefix);
		// TODO get maximum from application
		// Set maximum filesize 10MB
		long maximum =  10485760;
		final AjaxEditableUploadField uploadField = new AjaxEditableUploadField("valuefield", uploadModel, maximum);
		uploadField.setOutputMarkupId(true);
		addStyle(uploadField, fieldDefinition.getVisualizationInfo());
		return new Fragment("valuefield", "fileUpload", container).add(uploadField);
	}

	// ----------------------------------------------------

	private void addValidator(final FormComponent component, final RBField fieldDefinition) {
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
