/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

import java.util.Set;

import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.rb.core.api.TypeManager;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  Implementation of {@link TypeManager}.
 * </p>
 *
 * <p>
 * 	Created Sep 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class TypeManagerImpl implements TypeManager {
	
	private final ServiceProvider provider;
	
	// -----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param provider The service provider.
	 */
	public TypeManagerImpl(final ServiceProvider provider) {
		this.provider = provider;
	}
	
	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<SNClass> findAll() {
		return provider.getArastejuGate().getTypeSystem().getAllClasses();
	}

}
