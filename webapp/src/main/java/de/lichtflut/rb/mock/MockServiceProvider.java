/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.ArastrejuGate;

import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.api.TypeManager;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * Mock-Implementation of {@link ServiceProvider}.
 *
 * Created: Aug 30, 2011
 *
 * @author Ravi Knox
 */
public class MockServiceProvider implements ServiceProvider {

	private SchemaManager schemaManager;
	private EntityManager entityManager;
	private TypeManager typeManager;
	
	private List<RBEntity> dataPool = new ArrayList<RBEntity>();
	
	private final static MockServiceProvider INSTANCE = new MockServiceProvider();

	// -----------------------------------------------------
	
	/**
	 * @return the default instance
	 */
	public static MockServiceProvider getDefaultInstance() {
		return INSTANCE;
	}
	
	// -----------------------------------------------------

	/**
	 * Constructor.
	 */
	public MockServiceProvider() {
		dataPool.addAll(MockNewRBEntityFactory.getAllEntities());
        schemaManager = new MockSchemaManager();
        entityManager = new MockEntityManager(dataPool);
        typeManager = new MockTypeManager(dataPool);
	}

	// -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArastrejuGate getArastejuGate() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaManager getSchemaManager() {
		return schemaManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeManager getTypeManager() {
		return typeManager;
	}

}