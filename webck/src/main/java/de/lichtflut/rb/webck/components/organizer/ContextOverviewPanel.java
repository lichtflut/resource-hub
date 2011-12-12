/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.organizer;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.context.Context;

/**
 * <p>
 *  Panel for overview and editing of contexts.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class ContextOverviewPanel extends Panel {
	
	/**
	 * @param id
	 * @param model
	 */
	@SuppressWarnings("rawtypes")
	public ContextOverviewPanel(final String id, final IModel<List<Context>> model) {
		super(id);
		
		setOutputMarkupId(true);
		
		final ListView<Context> ctxView = new ListView<Context>("view", model) {
			@Override
			protected void populateItem(ListItem<Context> item) {
				final Context ctx = item.getModelObject();
				item.add(new Label("uri",ctx.getQualifiedName().toURI()));
			}
		};
		add(ctxView);
		
		add(new AjaxLink("createContext") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				createContext(target);
			}
		});
	}
	
	// ----------------------------------------------------
	
	public abstract void createContext(AjaxRequestTarget target);
	
}
