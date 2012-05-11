/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.domains;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.webck.components.common.TypedPanel;

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
						onDomainSelected(domain);
					}
				};
				item.add(link);
				link.add(new Label("domain", domain.getName()));
				link.add(new AttributeAppender("title", domain.getTitle()));
			}
		});
		
	}
	
	// ----------------------------------------------------
	
	public abstract void onDomainSelected(RBDomain domain);

}
