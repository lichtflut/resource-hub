/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.mock;

import java.util.Collection;

import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.api.IRBEntityManagement;
import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 * This class provides static {@link IRBEntityManagement} for testing purposes.
 * </p>
 * Created: Aug 18, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class MockRBEntityManagement implements IRBEntityManagement {

	@Override
	public boolean createOrUpdateEntity(final IRBEntity instance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IRBEntity loadEntity(final QualifiedName qn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRBEntity loadEntity(final String nodeIdentifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRBEntity loadEntity(final ResourceNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IRBEntity> loadAllEntitiesForSchema(
			final Collection<ResourceSchema> schemas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IRBEntity> loadAllEntitiesForSchema(
			final Collection<ResourceSchema> schemas, final String filter, final SearchContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IRBEntity> loadAllEntitiesForSchema(final ResourceSchema schema) {
		return MockNewRBEntityFactory.getListOfNewRBEntities();	}

	@Override
	public Collection<IRBEntity> loadAllEntitiesForSchema(
			final ResourceSchema schema, final String filter, final SearchContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
