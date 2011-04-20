package de.lichtflut.rb.core.schema.parser.impl;

import de.lichtflut.rb.core.schema.model.ResourceSchemaType;

public interface RBEvaluator<E extends ResourceSchemaType> {
	boolean evaluate(E param);
	E getResult();
}
