/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.typesystem;

import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.arastreju.sge.model.ElementaryDataType;

import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Sep 22, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SchemaEditorPanel extends Panel {
	
	/**
	 *  Constructor.
	 */
	public SchemaEditorPanel(final String id, final IModel<ResourceSchema> model) {
		super(id, model);
		
		setOutputMarkupPlaceholderTag(true);
		setOutputMarkupId(true);
		
		final IModel<List<PropertyAssertion>> assertionsModel = 
			new PropertyModel<List<PropertyAssertion>>(model, "propertyAssertions");
		
		add(new ListView<PropertyAssertion>("listView", assertionsModel) {
			@Override
			protected void populateItem(ListItem<PropertyAssertion> item) {
				final PropertyAssertion assertion = item.getModelObject();
				final PropertyDeclaration declaration = assertion.getPropertyDeclaration();
				final ElementaryDataType dataType = declaration.getElementaryDataType();
				
				item.add(new Label("property", assertion.getPropertyDescriptor().toString()));
				item.add(new Label("dataType", dataType.name()));
				item.add(new Label("min", "" + assertion.getCardinality().getMinOccurs()));
				item.add(new Label("max", max(assertion)));
				
				item.add(new AjaxEventBehavior("onClick") {
					@Override
					protected void onEvent(AjaxRequestTarget target) {
						target.add(SchemaEditorPanel.this);
						assertion.setCardinality(CardinalityBuilder.hasOptionalOneToMany());
					}
				});
			}
		});
		
	}
	
	// -----------------------------------------------------
	
	protected String max(final PropertyAssertion pa) {
		if (pa.getCardinality().isUnbound()) {
			return "unbound";
		} else {
			return "" + pa.getCardinality().getMaxOccurs();
		}
		
	}
	
}
