/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.webck.models.basic.AbstractDerivedModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import de.lichtflut.rb.webck.models.types.PropertyRowListModel;

/**
 * <p>
 *  This {@link Panel} lists all Properties for a given Type.
 * </p>
 *
 * <p>
 * 	Created Feb 27, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class SchemaDetailPanel extends Panel{

	/**
	 * @param id - wicket:id
	 * @param model to display
	 */
	public SchemaDetailPanel(String id, IModel<ResourceSchema> schema) {
		super(id);
		this.add(createListView(schema));
	}

	// ------------------------------------------------------

	/**
	 * @param model 
	 * @return
	 */
	private Component createListView(IModel<ResourceSchema> schema) {
		
		IModel<List<PropertyRow>> list = new PropertyRowListModel(schema);
		ListView<PropertyRow> view = new ListView<PropertyRow>("row", list) {

			@Override
			protected void populateItem(ListItem<PropertyRow> item) {
				PropertyRow decl = (PropertyRow) item.getModelObject();
				fillRow(decl, item);
			}
		};
		return view;
	}


	private void fillRow(PropertyRow decl, ListItem<PropertyRow> item) {
		addPropertyDecl(decl, item);
		addLabelDecl(decl, item);
		addMinimumDecl(decl, item);
		addMaximumDecl(decl, item);
		addDatatypeDecl(decl, item);
		item.add(new Label("constraints", decl.getLiteralConstraints().toString()));
	}

	/**
	 * @param decl
	 * @param item
	 */
	protected void addDatatypeDecl(PropertyRow decl, ListItem<PropertyRow> item) {
		Label label = new Label("datatype", decl.getDataType().name());
//		addTitle(decl.getDataType().name().toLowerCase()., label)
		item.add(label);
	}

	/**
	 * @param decl
	 * @param item
	 */
	protected void addMaximumDecl(PropertyRow decl, ListItem<PropertyRow> item) {
		String max = String.valueOf(decl.getMax());
		Label label = new Label("max", max);
//		addTitle(max, label);
		item.add(label);
	}

	/**
	 * @param decl
	 * @param item
	 */
	protected void addMinimumDecl(PropertyRow decl, ListItem<PropertyRow> item) {
		String min = String.valueOf(decl.getMin());
		Label label = new Label("min", min);
//		addTitle(min, label);
		item.add(label);
	}

	/**
	 * @param decl
	 * @param item
	 */
	@SuppressWarnings("rawtypes")
	protected void addLabelDecl(PropertyRow decl, ListItem<PropertyRow> item) {
		IModel model =  new PropertyModel<ResourceID>(decl, "defaultLabel");
		Label label = new Label("label", model);
//		addTitle(model.getObject().toString(), label);
		item.add(label);
	}

	/**
	 * @param decl
	 * @param item
	 */
	protected void addPropertyDecl(PropertyRow decl, ListItem<PropertyRow> item) {
		
//		ResourceLabelBuilder.getInstance().getFieldLabel(decl.getPropertyDescriptor(), getLocale()));		
		
		Label label = new Label("property", Model.of(ResourceLabelBuilder.getInstance().getFieldLabel(decl.getPropertyDescriptor(), getLocale())));
//		addTitle(decl.getPropertyDescriptor().toURI(), label);
		item.add(label);
	}

	/**
	 * @param valueOf
	 * @param label
	 * @return 
	 */
	private void addTitle(IModel model, Label label) {
		label.add(new AttributeAppender("title", model));
	}
}
