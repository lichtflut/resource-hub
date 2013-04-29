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
package de.lichtflut.rb.webck.components.typesystem.properties;

import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.LoadableModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.arastreju.sge.model.nodes.views.SNProperty;

import java.util.List;

/**
 * <p>
 *  Browser panel for rdf:Properties.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class SNPropertyBrowserPanel extends Panel {

	private final LoadableModel<List<SNProperty>> model;
	
	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public SNPropertyBrowserPanel(final String id, final LoadableModel<List<SNProperty>> model) {
		super(id);
		
		this.model = model;
		
		setOutputMarkupId(true);
		
		add(new ListView<SNProperty>("listview", model) {
			@Override
			protected void populateItem(final ListItem<SNProperty> item) {
				final SNProperty type = item.getModelObject();
				final Link link = new AjaxFallbackLink("link") {
						@Override
						public void onClick(AjaxRequestTarget target) {
							onPropertySelected(type, target);
						}	
				};
				item.add(link);
				link.add(new Label("type", type.getQualifiedName().getSimpleName()));
				link.add(new AttributeAppender("title", type.getQualifiedName().toURI()));
			}
		});
		
		add(new AjaxFallbackLink("createType") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				onCreateProperty(target);
			}
		});
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void onEvent(final IEvent<?> event) {
		final ModelChangeEvent<?> mce = ModelChangeEvent.from(event);
		if (mce.isAbout(ModelChangeEvent.PROPERTY)) {
			model.reset();
			RBAjaxTarget.add(this);
		} 
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	protected void onDetach() {
		super.onDetach();
		model.detach();
	}
	
	// -- CALLBACKS ---------------------------------------
	
	public abstract void onCreateProperty(AjaxRequestTarget target);
	
	public abstract void onPropertySelected(SNProperty property, AjaxRequestTarget target);
	
}
