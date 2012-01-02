/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity;

import java.io.Serializable;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;


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
	 * Returns a Label for this {@link RBEntity}.
	 * It may consist of various {@link RBField}s.
	 * It does not have to be unique.
	 * @return {@link String} representation for this {@link RBEntity}
	 */
	String getLabel();

	/**
	 * Returns the RDF:TYPE of this RBEntity.
	 * @return the {@link ResourceID} of this RBEntity's type
	 */
	ResourceID getType();

	/**
	 * Returns the {@link RBEntity} as an {@link ResourceNode}.
	 * @return the IRBEntity as an ResourceNode
	 */
	ResourceNode getNode();

	/**
	 * Returns the {@link RBField} for the predicate.
	 * @param predicate The predicate of the field.
	 * @return null if {@link RBField} is not found
	 */
	RBField getField(ResourceID predicate);

	/**
	 * Returns all fields of the NewRBEntity.
	 * @return - All RBFields of this entity
	 */
	List<RBField> getAllFields();

	/**
	 * Add {@link RBField} to RBEntity.
	 * @param field - {@link RBField}
	 * @return true if added successfully, false if not
	 */
	boolean addField(RBField field);

}
