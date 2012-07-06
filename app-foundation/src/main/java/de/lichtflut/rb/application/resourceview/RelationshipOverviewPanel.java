/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.resourceview;

import java.util.List;

import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.common.RelationshipFilter;
import de.lichtflut.rb.webck.components.relationships.RelationshipListPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.entity.RBEntityStatementsModel;

/**
 * <p>
 *  Panel showing all relationships of an Entity.
 * </p>
 *
 * <p>
 * 	Created Dec 9, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RelationshipOverviewPanel extends Panel {
	
	@SpringBean
	private ModelingConversation conversation;
	
	private final IModel<RBEntity> model;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model
	 */
	public RelationshipOverviewPanel(final String id, final IModel<RBEntity> model) {
		super(id);
		this.model = model;
		setOutputMarkupId(true);
		
		final IModel<List<Statement>> peopleStatements = new RBEntityStatementsModel(model, new RelationshipFilter.Undeclared() {
			@Override
			public boolean accept(Statement stmt) {
				return isOfType(stmt.getObject().asResource(), RB.PERSON);
			}
		});
		add(new RelPanel("people", peopleStatements));
		
		final IModel<List<Statement>> orgStatements = new RBEntityStatementsModel(model, new RelationshipFilter.Undeclared() {
			@Override
			public boolean accept(Statement stmt) {
				return isOfType(stmt.getObject().asResource(), RB.ORGANIZATION);
			}
		});
		add(new RelPanel("organizations", orgStatements));
		
		
		final IModel<List<Statement>> otherStatements = new RBEntityStatementsModel(model, new RelationshipFilter.Undeclared() {
			@Override
			public boolean accept(Statement stmt) {
				return !isOfType(stmt.getObject().asResource(), RB.PERSON) 
						&& !isOfType(stmt.getObject().asResource(), RB.ORGANIZATION);
			}
		});
		add(new RelPanel("others", otherStatements));
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	protected void detachModel() {
		super.detachModel();
		model.detach();
	}
	
	// ----------------------------------------------------
	
	private final class RelPanel extends RelationshipListPanel {
		private RelPanel(String id, IModel<List<Statement>> model) {
			super(id, model);
		}

		@Override
		public void onRelationshipSelected(Statement stmt) {
			final ResourceID id = stmt.getObject().asResource();
			final PageParameters params = new PageParameters();
			params.add(EntityDetailPage.PARAM_RESOURCE_ID, id.getQualifiedName().toURI());
			setResponsePage(EntityDetailPage.class, params);
		}
		
		/** 
		* {@inheritDoc}
		*/
		@Override
		public void onRelationshipRemoved(Statement stmt) {
			conversation.removeStatement(stmt);
			send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.RELATIONSHIP));
		}
	}
}
