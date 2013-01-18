/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.fields;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.RBFieldValue;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.nodes.SemanticNode;

/**
 * <p>
 * 	Model representing one value of an RBField.
 * </p>
 * 
 * <p>
 * 	Created: Aug 24, 2011
 * </p>
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class RBFieldValueModel implements IModel<Object> {

	private final RBField field;
	
	private final int index;
	
	// -----------------------------------------------------

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * <p>
	 * </p>
	 * @param field - instance of {@link RBField}
	 * @param index - index of the value in {@link RBField}
	 */
	public RBFieldValueModel(final RBField field, final int index){
		this.field = field;
		this.index = index;
	}
	
	// -----------------------------------------------------
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getObject() {
        RBFieldValue fieldValue = getFieldValue();
        if (fieldValue != null) {
            return fieldValue.getValue();
        } else {
            return null;
        }
    }

	@Override
	public void setObject(final Object object) {
        RBFieldValue fieldValue = getFieldValue();
        if (fieldValue != null) {
            getFieldValue().setValue(object);
        }
	}
	
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	public RBField getField() {
		return field;
	}

    public RBFieldValue getFieldValue() {
        return field.getValue(index);
    }

	// -----------------------------------------------------
	
	@Override
	public void detach() {
		// Do nothing
	}
	
}
