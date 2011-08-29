/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.schema.model.IRBEntity;

/**
 * <p>
 *  Manager for RBEntities.
 * </p>
 *
 * <p>
 * 	Created Aug 11, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface RBEntityManager {

	/**
	 * Find an RBEntity by it's qualified name. The schema will be loaded implicitly.
	 * @param qn The qualified name.
	 * @return The entity or null.
	 */
	IRBEntity find(QualifiedName qn);

	/**
	 * Validates and stores the entity.
	 * @param entity The entity.
	 */
	void store(IRBEntity entity);

	/**
	 * Removes all visible associations of given entity.
	 * @param entity The entity.
	 */
	void remove(IRBEntity entity);

}
