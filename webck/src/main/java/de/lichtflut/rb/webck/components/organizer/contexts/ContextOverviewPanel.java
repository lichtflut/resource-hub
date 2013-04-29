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
package de.lichtflut.rb.webck.components.organizer.contexts;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.context.Context;

import java.util.List;

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
