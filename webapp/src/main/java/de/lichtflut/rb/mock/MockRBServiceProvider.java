/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import org.arastreju.sge.ArastrejuGate;

import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.spi.IRBServiceProvider;

/**
 * Mock-Implementation of {@link IRBServiceProvider}.
 *
 * Created: Aug 30, 2011
 *
 * @author Ravi Knox
 */
public class MockRBServiceProvider implements IRBServiceProvider {

	private SchemaManager schemaManagement = null;
	private EntityManager typeManagement = null;

	/**
	 * Constructor.
	 */
	public MockRBServiceProvider() {
        schemaManagement = new MockResourceSchemaManagement();
        typeManagement = new MockRBEntityManagement();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArastrejuGate getArastejuGateInstance() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaManager getResourceSchemaManagement() {
		return schemaManagement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityManager getRBEntityManagement() {
		return typeManagement;
	}

}
