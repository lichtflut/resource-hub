/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity;

import java.util.List;
import java.util.Locale;
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
public interface RBField {

	/**
	 * Returns the (attribute / )name of this RBField.
	 * @return Name of this field
	 */
	ResourceID getPredicate();

	/**
	 * Returns the field's display label.
	 * @param locale TODO
	 * @return The label of the field.
	 */
	String getLabel(Locale locale);

	// -----------------------------------------------------

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
	
	// -----------------------------------------------------
	
	/**
	 * Get's the amount of value slot's. 
	 */
	int getSlots();
	
	/**
	 * Set a value in this field.
	 * @param index The index, starting with 0.
	 * @param value The value to set.
	 */
	void setValue(int index, Object value);
	
	/**
	 * Remove a slot with it's value.
	 * @param index The index, starting with 0.
	 */
	void removeSlot(int index);
	
	/**
	 * Add a value to the end of the value list.
	 * @param value THe value to add;
	 */
	void addValue(Object value);
	
	/**
	 * Get the value for given index.
	 * @param index The index.
	 * @return The object at this index or null.
	 */
	Object getValue(int index);
	
	/**
	 * Returns all non null values of this RBField as unmodifiable list.
	 * They can be of any type from String to Date, depending on the RBSchema.
	 * @return Value of this field as a list
	 */
	<T> List<T> getValues();
	
	/**
	 * Sets the value for this RBField.
	 * @param values - this fields value as a list
	 */
	@Deprecated
	void setValues(List<Object> values);
	
}
