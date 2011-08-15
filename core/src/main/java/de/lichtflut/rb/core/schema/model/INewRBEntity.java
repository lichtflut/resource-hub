/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.QualifiedName;

/**
 * An IRBField represents an edge of a RBEntity.
 *
 * Created: Aug 15, 2011
 *
 * @author Ravi Knox
 */
public interface INewRBEntity {

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
	 * Returns all fields of the NewRBEntity.
	 * @return - All RBFields of this entity
	 */
	List<IRBField> getAllFields();

	/**
	 * Add RBField to RBEntity.
	 * @param field - {@link IRBField}
	 * @return true if added successfully, false if not
	 */
	boolean addField(IRBField field);

	/**
	 * Returns MetaInfo about this RBEntity.
	 * TODO: It is not yet exactly specified what {@link IRBMetaInfo} will contain (maybe RBSchema, number of fields, etc...).
	 * @return - {@link IRBMetaInfo}
	 */
	IRBMetaInfo getRBMetaInfo();

	/**
	 * String representation of this RBEntity.
	 * TODO: Specify pattern
	 * @return - {@link String}
	 */
	String toString();

}
