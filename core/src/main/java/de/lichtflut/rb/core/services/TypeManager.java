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
package de.lichtflut.rb.core.services;

import java.util.List;
import java.util.Set;

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
	SNClass find(ResourceID type);

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
	 * Get all super classes of this base class.
	 *
	 * @param base The base.
	 * @return The superclasses.
	 */
	Set<SNClass> getSuperClasses(ResourceID base);

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

	/**
	 * Get all direct subclasses of the base class.
	 * @param base The base
	 * @return A {@link Set} containing all sub classes. Empty if base has no sub class
	 */
	Set<SNClass> getSubClasses(ResourceID base);

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
