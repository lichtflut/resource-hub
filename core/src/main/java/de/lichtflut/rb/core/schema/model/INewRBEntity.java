/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.QualifiedName;

/**
 * Represents a resource type defined by a RBResourceSchema.
 *
 * Created: Aug 8, 2011
 *
 * @author Ravi Knox
 */
public interface INewRBEntity {


	/**
	 * Returns the full qualified name (URI) of this RBEntity.
	 * @return - Entities qualified name
	 */
	QualifiedName getQualifiedName();

	/**
	 * Returns a RBField with the given fieldname.
	 * @param fieldName - identifier of a edge (typically "http://yyy.de#xxx) of this RBEntity.
	 * @return - IRBField
	 */
	IRBField getField(String fieldName);

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
	 * Returns all simple field names of this Entity.
	 * @return - list of all simple field names
	 */
	List<String> getAllSimpleFieldNames();

	/**
	 * Returns all full qualified field names of this Entity.
	 * @return - list of all full qualified field names
	 */
	List<String> getAllFullFieldNames();

	/**
	 * Returns MetaInfo about this RBEntity.
	 * TODO: It is not yet exactly specified what {@link IRBMetaInfo} will contain (maybe RBSchema, number of fields, etc...).
	 * @return - {@link IRBMetaInfo}
	 */
	IRBMetaInfo getRBMetaInfo();

	/**
	 * Returns the {@link ResourceID} of this node/Entity.
	 * @return - {@link ResourceID}
	 */
	ResourceID getID();

	/**
	 * String representation of this RBEntity.
	 * TODO: Specify pattern
	 * @return - {@link String}
	 */
	String toString();
}
