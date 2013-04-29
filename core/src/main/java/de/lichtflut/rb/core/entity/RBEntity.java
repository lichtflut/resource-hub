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
package de.lichtflut.rb.core.entity;

import java.io.Serializable;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.common.SchemaIdentifyingType;
import de.lichtflut.rb.core.schema.model.ResourceSchema;


/**
 * <p>
 *  An RBField represents one attribute of a RBEntity. This attribute can contain multiple values.
 * </p>
 *
 * Created: Aug 15, 2011
 *
 * @author Ravi Knox
 */
public interface RBEntity extends Serializable {

	/**
	 * Returns the {@link ResourceID} of this node/Entity.
	 * @return - {@link ResourceID}
	 */
	ResourceID getID();


	/**
	 * Returns the {@link SchemaIdentifyingType} of this RBEntity.
	 * @return the {@link ResourceID} of this RBEntity's type
	 */
	ResourceID getType();

	/**
	 * Returns the {@link RBEntity} as an {@link ResourceNode}.
	 * @return the entity as a ResourceNode
	 */
	ResourceNode getNode();

	/**
	 * Returns a Label for this {@link RBEntity}.
	 * @return string representation for this {@link RBEntity}
	 */
	String getLabel();

	// ----------------------------------------------------

	/**
	 * Returns the {@link RBField} for the predicate.
	 * @param predicate The predicate of the field
	 * @return the field for the given predicate or null if prediate has no field associated
	 */
	RBField getField(ResourceID predicate);

	/**
	 * Returns all fields of the entity.
	 * @return All RBFields of this entity
	 */
	List<RBField> getAllFields();

	/**
	 * Add {@link RBField} to RBEntity.
	 * @param field - {@link RBField}
	 * @return true if added successfully, false if not
	 */
	boolean addField(RBField field);

	// ----------------------------------------------------

	/**
	 * Returns all quick-info fields as specified in the {@link ResourceSchema}.
	 * @return an unmodifiable {@link List} of {@link RBField}s
	 */
	List<RBField> getQuickInfo();

	/**
	 * Check if there is a schema defined for this entity.
	 * @return True if there is a schema.
	 */
	boolean hasSchema();

	/**
	 * Check if this entity is transient. This is usually only the case being created, before first storage.
	 * @return true if this entity is not yet persisted.
	 */
	boolean isTransient();

}
