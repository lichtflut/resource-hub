/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity;

import java.util.List;
import java.util.Locale;

import de.lichtflut.rb.core.schema.model.VisualizationInfo;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;


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
	 * @param locale The locale to use.
	 * @return The label of the field.
	 */
	String getLabel(Locale locale);

    /**
     * Get additional information about the visualization of the field.
     * @return The visualization info.
     */
    VisualizationInfo getVisualizationInfo();


	// -----------------------------------------------------

	/**
	 * Returns the type of this filed according to the RBSchema.
	 * @return This field's {@link Datatype}
	 */
	Datatype getDataType();

	/**
	 * Returns the {@link Cardinality} for this field.
	 * @return This field's {@link Cardinality}
	 */
	Cardinality getCardinality();

	/**
	 * Returns whether this field contains a resource reference.
	 * @return True if this field is a resource reference, false if not
	 */
	boolean isResourceReference();

	/**
	 * Returns this field's constraint according to the RBSchema.
	 * TODO: specify return type (Constraint / Pattern)?!
	 * TODO: If reference type Resource = full qualified ResourceURI as constraint?!
	 * @return {@link Constraint}
	 */
	Constraint getConstraint();
	
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
	
}
