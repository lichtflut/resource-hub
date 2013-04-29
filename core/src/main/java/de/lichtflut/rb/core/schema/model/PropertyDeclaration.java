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
package de.lichtflut.rb.core.schema.model;

import java.io.Serializable;

import org.arastreju.sge.model.ResourceID;


/**
 * <p>
 *  Declaration of a Property - either resource reference or value - of a resource.
 *  A set of PropertyDeclarations build a {@link ResourceSchema}.
 * </p>
 *
 * <p>
 * 	Created Jan 27, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface PropertyDeclaration extends Serializable {

	/**
	 * The property type. Usually this descriptor will be of type rdf:Property. 
	 * In a concrete RDF statement corresponding to this PropertyDeclaration this 
	 * will be the predicate.
	 * @return The resource identifier representing the descriptor.
	 */
	ResourceID getPropertyDescriptor();

	/**
	 * Set the property.
	 * @param property The property.
	 */
	void setPropertyDescriptor(ResourceID property);

    // ----------------------------------------------------

    /**
     * Get the {@link Datatype} for this PropertyDeclaration.
     */
    void setDatatype(Datatype datatype);

    /**
     * Get the {@link Datatype} for this PropertyDeclaration.
     */
    Datatype getDatatype();
	
	// -----------------------------------------------------

	/**
	 * The cardinality of this property.
	 * @return The cardinality.
	 */
	Cardinality getCardinality();

	/**
	 * Set cardinality of this property.
	 * @param c The cardinality.
	 */
	void setCardinality(Cardinality c);

	// -----------------------------------------------------
	
	/**
	 * Get the definition for the label.
	 * @return The definition.
	 */
	FieldLabelDefinition getFieldLabelDefinition();

	/**
	 * Set the label definition for this declaration.
	 * @param def The definition.
	 */
	void setFieldLabelDefinition(FieldLabelDefinition def);
	
	// -----------------------------------------------------

	/**
	 * The constraints for this property assertion.
	 * @return Set the constraints.
	 */
	Constraint getConstraint();
	
	/**
	 * Set the Constraint for this PropertyDeclaration.
	 */
	void setConstraint(Constraint constraint);
	
	/**
	 * @return true if this PropertyDeclaration has a {@link Constraint}, flase if not.
	 */
	boolean hasConstraint();
	
	// ------------------------------------------------------
	
	/**
	 * Set the {@link VisualizationInfo} for this PropertyDeclaration.
	 */
	void setVisualizationInfo(VisualizationInfo info);
	
	/**
	 * Get the {@link VisualizationInfo} for this PropertyDeclaration.
	 */
	VisualizationInfo getVisualizationInfo();
}
