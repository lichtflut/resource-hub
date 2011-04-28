/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl;

import de.lichtflut.rb.core.schema.model.ResourceSchemaType;


/**
 * Still provides the GOF-Interpreter pattern for some parsing stuff
 * 
 * 
 * Created: Apr 28, 2011
 *
 * @author Nils Bleisch
 */
public interface RSEvaluator<E extends ResourceSchemaType> {
	
	boolean evaluate(E param);
	
	// -----------------------------------------------------
	
	E getResult();
}
