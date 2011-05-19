/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.genericresource;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import de.lichtflut.rb.core.schema.model.RBInvalidValueException;
import de.lichtflut.rb.core.schema.model.RBValidator;

/**
 * <p>
 * TODO: To comment
 * </p>
 * 
 *  <p>
 * 	Created May 18, 2011
 * </p>
 * 
 * @author Nils Bleisch
 *
 */
@SuppressWarnings("serial")
public class GenericResourceValidator implements IValidator<String> {

	private RBValidator<String> validator;
	
	public GenericResourceValidator(RBValidator<String> validator){
		this.validator=validator;
	}
	
	
	public void validate(IValidatable<String> validatable) {
		try {
			validator.isValid(validatable.getValue());
		} catch (RBInvalidValueException e) {
			ValidationError error = new ValidationError();
			error.setMessage(e.getMessage());
			validatable.error(error);
		}
	}

}
