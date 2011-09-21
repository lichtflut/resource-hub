/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import java.util.List;

import org.arastreju.sge.model.nodes.views.SNClass;

/**
 * <p>
 *  Manager for Types.
 * </p>
 *
 * <p>
 * 	Created Sep 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface TypeManager {
	
	/**
	 * Create a new rb:Type with given name.
	 * @param namespace The namespace.
	 * @param name The simple name.
	 * @return The persistent class node.
	 */
	SNClass create(String namespace, String name);
	
	/**
	 * Create a new rb:Type with given name.
	 * @param namespace The namespace.
	 * @param name The simple name.
	 * @return The persistent class node.
	 */
	void remove(SNClass type);
	
	/**
	 * Find all types.
	 * @return A list with all sub classes of rb:Type.
	 */
	List<SNClass> findAll();

}
