/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.resourceview;

import java.util.ArrayList;
import java.util.List;

import de.lichtflut.rb.application.common.CommonParams;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;



/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jun 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceDumpPanel extends Panel {

	/**
	 * @param id
	 * @param model
	 */
	@SuppressWarnings("rawtypes")
	public ResourceDumpPanel(final String id, final IModel<ResourceNode> model) {
		super(id, model);
		
		add(new Label("uri", new PropertyModel(model, "qualifiedName")));
		
		final List<Statement> assocs = new ArrayList<Statement>(model.getObject().getAssociations());
		final ListView<Statement> assocList = new ListView<Statement>("associations", assocs) {
			protected void populateItem(ListItem<Statement> item) {
				final Statement assoc = item.getModelObject();
				item.add(new Label("predicate", Model.of(assoc.getPredicate())));
				item.add(createLink(assoc.getObject()));
			}
		};
		
		add(assocList);
	}
	
	// -----------------------------------------------------

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Link createLink(final SemanticNode node) {
		final PageParameters params = new PageParameters();
		final BookmarkablePageLink link = new BookmarkablePageLink("link", EntityDetailPage.class, params); 
		if (node.isResourceNode()) {
			params.add(CommonParams.PARAM_RESOURCE_ID, node.asResource().getQualifiedName().toURI());
		} else {
			link.setEnabled(false);
		}
		link.add(new Label("value", Model.of(node)));
		return link;
	}

}
