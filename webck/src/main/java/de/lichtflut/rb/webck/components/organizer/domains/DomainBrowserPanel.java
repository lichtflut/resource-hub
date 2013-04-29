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
package de.lichtflut.rb.webck.components.organizer.domains;

import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * <p>
 *  List view of domains.
 * </p>
 *
 * <p>
 * 	Created Feb 27, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class DomainBrowserPanel extends TypedPanel<List<RBDomain>> {

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param model The model of domains.
	 */
	@SuppressWarnings("rawtypes")
	public DomainBrowserPanel(String id, IModel<List<RBDomain>> model) {
		super(id, model);
		
		setOutputMarkupId(true);
		
		add(new ListView<RBDomain>("listview", model) {
			@Override
			protected void populateItem(final ListItem<RBDomain> item) {
				final RBDomain domain = item.getModelObject();
				
				final Link link = new AjaxFallbackLink("link") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						onDomainSelected(item.getModel());
					}
				};
				item.add(link);
				link.add(new Label("domain", domain.getName()));
				link.add(new AttributeAppender("title", domain.getTitle()));
			}
		});
		
	}
	
	// ----------------------------------------------------
	
	public abstract void onDomainSelected(IModel<RBDomain> domain);

}
