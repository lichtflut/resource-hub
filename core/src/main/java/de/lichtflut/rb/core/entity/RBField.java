/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity;

import java.util.List;
import java.util.Set;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;


/**
 * <p>
 * 	An RBField represents one value field (or association) of an RBEntity.
 *  Each field consists basically of a predicate and one or more values. The predicate defines
 *  the semantic of the field's value to the entity.
 * </p>
 *
 * Created: Aug 8, 2011
 *
 * @author Ravi Knox
 */
public interface RBField{

	/**
	 * Returns the (attribute / )name of this RBField.
	 * @return Name of this field
	 */
	ResourceID getPredicate();

	/**
	 * Returns the field's simple name.
	 * @return Simple name of this field
	 */
	String getLabel();

	/**
	 * Returns the value of this RBField.
	 * This can be any type from String to Date, depending on the RBSchema.
	 * @return Value of this field as a list
	 */
	List<Object> getValues();

	/**
	 * Sets the value for this RBField.
	 * @param values - this fields value as a list
	 */
	void setValues(List<Object> values);

	/**
	 * Returns the type of this filed according to the RBSchema.
	 * @return This field's {@link ElementaryDataType}
	 */
	ElementaryDataType getDataType();

	/**
	 * Returns the {@link Cardinality} for this field.
	 * @return This field's {@link Cardinality}
	 */
	Cardinality getCardinality();

	/**
	 * Returns whether this field is known to the RBSchema.
	 * If it is not known, it is a 'custom-' attribute which is always of type String.
	 * @return True if yes, false if not.
	 */
	boolean isKnownToSchema();

	/**
	 * Returns whether this field contains a resource reference.
	 * @return True if this field is a resource reference, false if not
	 */
	boolean isResourceReference();

	/**
	 * Returns this field's constraint according to the RBSchema.
	 * TODO: specify return type (Constraint / Pattern)?!
	 * TODO: If reference type Resource = full qualified ResourceURI as constraint?!
	 * @return List of {@link Constraint}
	 */
	Set<Constraint> getConstraints();

}
