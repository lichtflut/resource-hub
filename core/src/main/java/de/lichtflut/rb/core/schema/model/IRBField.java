/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.util.List;
import java.util.regex.Pattern;

import org.arastreju.sge.model.ElementaryDataType;


/**
 * An IRBField represents an edge of a RBEntity.
 *
 * Created: Aug 8, 2011
 *
 * @author Ravi Knox
 */
public interface IRBField {

	/**
	 * Returns the (attribute / )name of this RBField.
	 * @return - name of this field
	 */
	String getFieldName();

	/**
	 * Returns the field's simple name.
	 * @return - simple name of this field
	 */
	String getSimpleName();

	/**
	 * Returns the value of this RBField.
	 * This can be any type from String to Date, depending on the RBSchema.
	 * @return - Value of this field as a list
	 */
	List<Object> getFieldValue();

	/**
	 * Sets the value for this RBField.
	 * @param fieldValue - this fields value as a list
	 */
	void setFieldValue(List<Object> fieldValue);

	/**
	 * Returns the type of this filed according to the RBSchema.
	 * @return - {@link ElementaryDataType}
	 */
	ElementaryDataType getDataType();

	/**
	 * Returns wheather this field is known to the RBSchema.
	 * If it is not known, it is a 'custom-' attribute which is always of type String.
	 * @return - true if yes, false if not.
	 */
	boolean isKnownToSchema();

	/**
	 * Returns this field's constraint according to the RBSchema.
	 * TODO: specify return type (Constraint / Pattern)?!
	 * TODO: If reference type Resource = full quallified ResourceURI as constraint?!
	 * @return - list of {@link Pattern}
	 */
	List<Pattern> getConstraints();

	/**
	 * Returns the {@link Cardinality} for this field.
	 * @return - {@link Cardinality}
	 */
	Cardinality getCardinality();

	/**
	 * Returns wheather this field contains a resource reference.
	 * @return boolean - true if this field is a resource reference, false if not
	 */
	boolean isResourceReference();
}
