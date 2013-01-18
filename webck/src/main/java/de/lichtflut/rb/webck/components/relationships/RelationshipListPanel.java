/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.relationships;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.components.links.LabeledLink;
import de.lichtflut.rb.webck.components.listview.ActionLink;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.SemanticNode;

import java.util.List;

import static de.lichtflut.rb.webck.models.ConditionalModel.isEmpty;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

/**
 * <p>
 *  Panel listing relationships.
 * </p>
 *
 * <p>
 * 	Created Dec 9, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RelationshipListPanel extends Panel {

    @SpringBean
    private SemanticNetworkService service;

    @SpringBean
    private ResourceLinkProvider resourceLinkProvider;

    // ----------------------------------------------------

    /**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model containing the statements.
	 */
	public RelationshipListPanel(final String id, final IModel<List<Statement>> model) {
		super(id, model);
		
		final ListView<Statement> listView = new ListView<Statement>("rows", model) {
			@Override
			protected void populateItem(final ListItem<Statement> item) {
				final Statement stmt = item.getModelObject();
				item.add(new Label("role", getLabelForPredicate(stmt.getPredicate())));
				item.add(new Label("entity", getLabelForEntity(stmt.getObject())));

                CrossLink entityLink = new CrossLink(LabeledLink.LINK_ID, getLinkToEntity(stmt.getObject()));
                LabeledLink viewLink = new LabeledLink("viewLink", entityLink, new ResourceModel("action.view"));
                viewLink.setLinkCssClass("action-view");
                viewLink.setLinkTitle(new ResourceModel("action.view"));
                item.add(viewLink);

				item.add(new ActionLink("removeLink", new ResourceModel("action.remove")){
					@Override
					public void onClick(AjaxRequestTarget target) {
						onRelationshipRemoved(item.getModelObject());
						onStatementModelChanged();
					}
				}.setLinkCssClass("action-delete").setLinkTitle(new ResourceModel("action.remove")));
			}
			
			private void onStatementModelChanged() {
				removeAll();
			}
			
		};
		
		add(listView);
		
		add(ConditionalBehavior.visibleIf(not(isEmpty(model))));
		
	}
	
	// ----------------------------------------------------

    protected void onRelationshipRemoved(Statement stmt) {
        service.remove(stmt);
        send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.RELATIONSHIP));
    }
	
	// ----------------------------------------------------
	
	protected String getLabelForEntity(final SemanticNode node) {
		if (node == null) {
			return "<null>";
		} else if (node.isResourceNode()) {
			return ResourceLabelBuilder.getInstance().getLabel(node.asResource(), getLocale());
		} else {
			return node.toString();	
		}
	}

    protected String getLinkToEntity(final SemanticNode node) {
        if (node != null && node.isResourceNode()) {
            return resourceLinkProvider.getUrlToResource(node.asResource(), VisualizationMode.DETAILS, DisplayMode.VIEW);
        } else {
            return "";
        }
    }
	
	protected String getLabelForPredicate(final ResourceID predicate) {
		return ResourceLabelBuilder.getInstance().getFieldLabel(predicate, getLocale());
	}
	

}
