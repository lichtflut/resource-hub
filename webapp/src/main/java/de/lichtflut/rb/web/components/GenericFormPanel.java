/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created May 6, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
@SuppressWarnings({ "serial", "unchecked" })
public class GenericFormPanel extends Panel {
	

	public GenericFormPanel(String id, IModel model, ResourceSchema schema) {
		super(id, model);
		init(schema);
	}
	
	public GenericFormPanel(String id, ResourceSchema schema) {
		super(id);
		init(schema);
	}
	
	
	private void init(ResourceSchema schema){
		Form form = new Form("form");
		if(schema!=null){
			final List<PropertyAssertion> assertions = new ArrayList<PropertyAssertion>(schema.getPropertyAssertions());
			form.add(new ListView("propertylist", assertions) {
			    protected void populateItem(ListItem item) {
			        PropertyAssertion assertion = (PropertyAssertion) item.getModelObject();
			        item.add(new Label("propertyLabel", assertion.getPropertyDeclaration().getName()));
			        Fragment fragment = new Fragment("propertyInput","textInput", this);
			        fragment.add(new TextField<String>("text", Model.of("")));
			        item.add(fragment);
			    }
			});
		}
		this.add(form);
	}//End of method init

}
