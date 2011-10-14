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
import org.arastreju.sge.model.nodes.views.SNClass;

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
public abstract class TypeBrowserPanel extends Panel {

	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public TypeBrowserPanel(final String id, final IModel<List<SNClass>> typesModel) {
		super(id);
		
		setOutputMarkupId(true);
		
		add(new ListView<SNClass>("listview", typesModel) {
			@Override
			protected void populateItem(final ListItem<SNClass> item) {
				final SNClass type = item.getModelObject();
				final Link link = new AjaxFallbackLink("link") {
						@Override
						public void onClick(AjaxRequestTarget target) {
							onTypeSelected(type, target);
						}	
				};
				item.add(link);
				link.add(new Label("type", type.getName()));
				link.add(new AttributeAppender("title", type.getQualifiedName().toURI()));
			}
		});
		
		add(new AjaxFallbackLink("createType") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				target.add(TypeBrowserPanel.this);
				onCreateType(target);
			}
		});
	}
	
	// -- CALLBACKS ---------------------------------------
	
	public abstract void onCreateType(AjaxRequestTarget target);
	
	public abstract void onTypeSelected(SNClass type, AjaxRequestTarget target);
	
}
