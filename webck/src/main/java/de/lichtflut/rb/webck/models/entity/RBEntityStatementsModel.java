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
package de.lichtflut.rb.webck.models.entity;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.common.RelationshipAccess;
import de.lichtflut.rb.webck.common.RelationshipFilter;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.Statement;

import java.util.List;

/**
 * <p>
 *  Model providing an Entity's statements.
 * </p>
 *
 * <p>
 * 	Created Dec 9, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBEntityStatementsModel extends DerivedDetachableModel<List<Statement>, RBEntity> {
	
	private final RelationshipFilter filter;
	
	// ----------------------------------------------------

	public RBEntityStatementsModel(IModel<RBEntity> source, RelationshipFilter filter) {
		super(source);
		this.filter = filter;
	}
	
	// ----------------------------------------------------

	@Override
	protected List<Statement> derive(RBEntity entity) {
		return new RelationshipAccess(entity).getStatements(filter);
	}

}
