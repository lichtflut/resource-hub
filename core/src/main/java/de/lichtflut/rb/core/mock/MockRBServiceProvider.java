/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.mock;

import org.arastreju.sge.ArastrejuGate;

import de.lichtflut.rb.core.api.INewRBEntityManagement;
import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.spi.INewRBServiceProvider;

/**
 * Mock-Implementation of {@link INewRBServiceProvider}.
 *
 * Created: Aug 30, 2011
 *
 * @author Ravi Knox
 */
public class MockRBServiceProvider implements INewRBServiceProvider {

	private ResourceSchemaManagement schemaManagement = null;
	private INewRBEntityManagement typeManagement = null;

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
	public ResourceSchemaManagement getResourceSchemaManagement() {
		return schemaManagement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public INewRBEntityManagement getRBEntityManagement() {
		return typeManagement;
	}

}
