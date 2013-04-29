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
package de.lichtflut.rb.webck.components.relationships;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.common.RelationshipFilter;
import de.lichtflut.rb.webck.models.entity.RBEntityStatementsModel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.Statement;

import java.util.List;

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
		add(new RelationshipListPanel("people", peopleStatements));
		
		final IModel<List<Statement>> orgStatements = new RBEntityStatementsModel(model, new RelationshipFilter.Undeclared() {
			@Override
			public boolean accept(Statement stmt) {
				return isOfType(stmt.getObject().asResource(), RB.ORGANIZATION);
			}
		});
		add(new RelationshipListPanel("organizations", orgStatements));
		
		
		final IModel<List<Statement>> otherStatements = new RBEntityStatementsModel(model, new RelationshipFilter.Undeclared() {
			@Override
			public boolean accept(Statement stmt) {
				return !isOfType(stmt.getObject().asResource(), RB.PERSON) 
						&& !isOfType(stmt.getObject().asResource(), RB.ORGANIZATION);
			}
		});
		add(new RelationshipListPanel("others", otherStatements));
	}
	
	// ----------------------------------------------------
	
	@Override
	protected void detachModel() {
		super.detachModel();
		model.detach();
	}
	
}
