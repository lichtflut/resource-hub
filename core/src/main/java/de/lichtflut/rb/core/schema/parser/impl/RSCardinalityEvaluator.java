/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl;

import org.antlr.runtime.Token;

import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.impl.CardinalityFactory;
import de.lichtflut.rb.core.schema.parser.impl.simplersf.ResourceSchemaParser;

public class RSCardinalityEvaluator implements RSEvaluator<Cardinality> {

	private Cardinality cardinality;

	public RSCardinalityEvaluator(Token t, int amount){
		if(!(t.getType()==ResourceSchemaParser.CARDINALITY))
			throw new IllegalArgumentException(
					"Type of the given token " + t.getType() + " must be " + ResourceSchemaParser.CARDINALITY + " (CARDINALITY)");
		String tokenLabel = t.getText().toLowerCase();
		if (tokenLabel.equals("has")){
			 this.cardinality = CardinalityFactory.hasExactly(amount);
		}else if (tokenLabel.contains("min")){
			this.cardinality = CardinalityFactory.hasAtLeast(amount);
		}else if (tokenLabel.contains("max")){
			this.cardinality = CardinalityFactory.hasOptionalOneUpTo(amount);
		}else{
			throw new IllegalArgumentException(
				 t.getText() + " is unknown");
		}
	}

	public Cardinality getResult(){
		return this.cardinality;
	}

	/**
	 * This is not the correct implementation.
	 * TODO: Fix it
	 */
	public boolean evaluate(Cardinality c) {
		int max_c1 = c.getMaxOccurs(), max_c2 = cardinality.getMaxOccurs();
		int min_c1 = c.getMinOccurs(), min_c2 = cardinality.getMinOccurs();
		this.cardinality =  (CardinalityFactory.getAbsoluteCardinality(Math.max(max_c1,max_c2), Math.min(min_c2, min_c1) ));
		return true;
	}
}