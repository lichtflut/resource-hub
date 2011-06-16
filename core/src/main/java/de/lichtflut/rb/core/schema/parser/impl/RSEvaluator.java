/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl;

import de.lichtflut.rb.core.schema.model.ResourceSchemaType;


/**
 * Still provides the GOF-Interpreter pattern for some parsing stuff.
 *
 *
 * Created: Apr 28, 2011
 *
 * @author Nils Bleisch
 * @param <E>
 */
public interface RSEvaluator<E extends ResourceSchemaType> {

	/**
	 * Tries to evaluate the given param.
	 * @param param -
	 * @return is successful return true, if not return false
	 */
	boolean evaluate(E param);

	// -----------------------------------------------------

	/**
	 * Returns evaluated result.
	 * @return E -
	 */
	E getResult();
}
