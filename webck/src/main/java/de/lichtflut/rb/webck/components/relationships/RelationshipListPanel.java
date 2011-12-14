/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.relationships;

import static de.lichtflut.rb.webck.models.ConditionalModel.isEmpty;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.components.listview.ActionLink;

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
public abstract class RelationshipListPanel extends Panel {

	/**
	 * @param id
	 * @param model
	 */
	public RelationshipListPanel(final String id, final IModel<List<? extends Statement>> model) {
		super(id, model);
		
		final ListView<Statement> listView = new ListView<Statement>("rows", model) {
			@Override
			protected void populateItem(final ListItem<Statement> item) {
				final Statement stmt = item.getModelObject();
				item.add(new Label("role", getLabelForPredicate(stmt.getPredicate())));
				item.add(new Label("entity", getLabelForEntity(stmt.getObject())));
				item.add(new ActionLink("viewLink", new ResourceModel("action.view")){
					@Override
					public void onClick(AjaxRequestTarget target) {
						onRelationshipSelected(item.getModelObject());
					}
				});
				item.add(new ActionLink("removeLink", new ResourceModel("action.remove")){
					@Override
					public void onClick(AjaxRequestTarget target) {
						onRelationshipRemoved(item.getModelObject());
					}
				});
			}
		};
		
		add(listView);
		
		add(ConditionalBehavior.visibleIf(not(isEmpty(model))));
		
	}
	
	// ----------------------------------------------------
	
	public abstract void onRelationshipSelected(Statement stmt);
	
	public abstract void onRelationshipRemoved(Statement stmt); 
	
	// ----------------------------------------------------
	
	protected String getLabelForEntity(final SemanticNode node) {
		if (node.isResourceNode()) {
			return ResourceLabelBuilder.getInstance().getLabel(node.asResource(), getLocale());
		} else {
			return node.toString();	
		}
	}
	
	protected String getLabelForPredicate(final ResourceID predicate) {
		return ResourceLabelBuilder.getInstance().getFieldLabel(predicate, getLocale());
	}
	

}
