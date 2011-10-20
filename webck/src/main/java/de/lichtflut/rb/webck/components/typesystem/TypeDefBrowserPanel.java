/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.schema.model.TypeDefinition;

/**
 * <p>
 *  Panel for Browsing of {@link TypeDefinition}s.
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class TypeDefBrowserPanel extends Panel {

	/**
	 * Constructor.
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public TypeDefBrowserPanel(final String id, final IModel<List<TypeDefinition>> typeDefsModel) {
		super(id);
		
		setOutputMarkupId(true);
		
		add(new ListView<TypeDefinition>("listview", typeDefsModel) {
			@Override
			protected void populateItem(final ListItem<TypeDefinition> item) {
				final TypeDefinition typeDef = item.getModelObject();
				final Link link = new AjaxFallbackLink("link") {
						@Override
						public void onClick(AjaxRequestTarget target) {
							onTypeDefSelected(typeDef, target);
						}	
				};
				item.add(link);
				link.add(new Label("typeDef", typeDef.getName()));
				link.add(new AttributeAppender("title", typeDef.getID().getQualifiedName()));
			}
		});
		
		add(new AjaxFallbackLink("createTypeDef") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				target.add(TypeDefBrowserPanel.this);
				onCreateTypeDef(target);
			}
		});
	}
	
	// -- CALLBACKS ---------------------------------------
	
	public abstract void onCreateTypeDef(AjaxRequestTarget target);
	
	public abstract void onTypeDefSelected(TypeDefinition typeDef, AjaxRequestTarget target);
	
}
