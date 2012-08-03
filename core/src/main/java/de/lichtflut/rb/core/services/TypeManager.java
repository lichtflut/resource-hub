/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.naming.QualifiedName;

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
	 * Find a type by it's ID.
	 * @param type The type.
	 * @return The corresponding class or null.
	 */
	SNClass findType(ResourceID type);
	
	/**
	 * Get the primary class of a resource.
	 * @param resource The resource to check.
	 * @return The corresponding class or null.
	 */
	SNClass getTypeOfResource(ResourceID resource);
	
	/**
	 * Find all types.
	 * @return A list with all sub classes of rb:Type.
	 */
	List<SNClass> findAllTypes();
	
	/**
	 * Create and persist a new rb:Type with given name.
	 * @param qn The qualified name of the type.
	 * @return The persistent class node.
	 */
	SNClass createType(QualifiedName qn);
	
	/**
	 * Remove an rb:Type by it's ID.
	 * @param id The type's ID.
	 */
	void removeType(ResourceID id);
	
	/**
	 * Add a super class to an existing type.
	 * @param type The type.
	 * @param superClass The new super class for this type.
	 */
	void addSuperClass(ResourceID type, ResourceID superClass);
	
	/**
	 * Add a super class from an existing type.
	 * @param type The type.
	 * @param superClass The super class to be removed from this type.
	 */
	void removeSuperClass(ResourceID type, ResourceID superClass);
	
	// ----------------------------------------------------
	
	/**
	 * Find a property by it's qualified name.
	 * @param qn The properties name.
	 * @return The corresponding class or null.
	 */
	SNProperty findProperty(QualifiedName qn);
	
	/**
	 * Create and persist a new rdf:Property with given name.
	 * @param qn The qualified name of the type.
	 * @return The persistent property node.
	 */
	SNProperty createProperty(QualifiedName qn);
	
	/**
	 * Create a new rdf:Property with given name.
	 * @param property The property to be removed.
	 */
	void removeProperty(SNProperty property);
	
	/**
	 * Find all types.
	 * @return A list with all rdf:Properties.
	 */
	List<SNProperty> findAllProperties();

}
