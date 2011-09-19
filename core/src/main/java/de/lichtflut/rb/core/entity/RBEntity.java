/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity;

import java.io.Serializable;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;


/**
 * An IRBField represents one attribute of a RBEntity. This attribute can contain multiple values.
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
	 * Returns the full qualified name (URI) of this RBEntity.
	 * @return - Entities qualified name
	 */
	QualifiedName getQualifiedName();

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
	 * Returns the {@link RBField} for a Fieldname.
	 * @param fieldname - Fieldname
	 * @return null if {@link RBField} is not found
	 */
	RBField getField(String fieldname);

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

	/**
	 * Returns MetaInfo about this RBEntity.
	 * TODO: It is not yet exactly specified what {@link MetaInfo} will contain (maybe RBSchema, number of fields, etc...).
	 * @return - {@link MetaInfo}
	 */
	MetaInfo getRBMetaInfo();

	// ------------------------------------------------------------

	/**
	 * String representation of this RBEntity.
	 * TODO: Specify pattern
	 * @return {@link String}
	 */
	String toString();

}
