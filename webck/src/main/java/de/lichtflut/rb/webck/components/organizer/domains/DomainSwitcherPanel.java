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
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.components.navigation.ContextMenu;
import de.lichtflut.rb.webck.models.domains.AlternateDomainsModel;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * <p>
 *  Panel for switching of the current domain.
 * </p>
 *
 * <p>
 * 	Created May 4, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class DomainSwitcherPanel extends Panel {

	@SpringBean
	private ServiceContext context;
	
	// ----------------------------------------------------
	
	/** Constructor.
	 * @param id The component ID.
	 * @param domainName Model containing the current domain name.
	 */
	@SuppressWarnings("rawtypes")
	public DomainSwitcherPanel(String id, IModel<String> domainName) {
		super(id, domainName);
		
		final ContextMenu menu = new ContextMenu("menu");
		menu.add(new Label("currentDomain", domainName));
		menu.add(new ListView<RBDomain>("domains", new AlternateDomainsModel()) {
			@Override
			protected void populateItem(final ListItem<RBDomain> item) {
				final String domain = item.getModelObject().getName();
				final Link link = new Link("link") {
					@Override
					public void onClick() {
						context.setDomain(domain);
						throw new RestartResponseException(getApplication().getHomePage());
					}
				};
				link.add(new Label("domainName", domain));
				item.add(link);
			}
		});
		add(menu);
		
		final Label domainLabel = new Label("domainLabel", domainName);
		domainLabel.add(menu.createToggleBehavior("onclick"));
		add(domainLabel);
	}

}
