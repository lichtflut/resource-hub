/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl;

import org.antlr.runtime.Token;

import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
/**
 *
 * TODO Discription.
 */
public class RSCardinalityEvaluator implements RSEvaluator<Cardinality> {

	/**
	 *
	 */
	private Cardinality cardinality;

	/**
	 *TODO: DESCRIPTION.
	 * @param t -
	 * @param amount -
	 */
	public RSCardinalityEvaluator(final Token t, final int amount){
		String tokenLabel = t.getText().toLowerCase();
		if (tokenLabel.equals("has")){
			 this.cardinality = CardinalityBuilder.hasExactly(amount);
		}else if (tokenLabel.contains("min")){
			this.cardinality = CardinalityBuilder.hasAtLeast(amount);
		}else if (tokenLabel.contains("max")){
			this.cardinality = CardinalityBuilder.hasOptionalOneUpTo(amount);
		}else{
			throw new IllegalArgumentException(
				 t.getText() + " is unknown");
		}
	}

	/**
	 * @return cardinatily
	 */
	public Cardinality getResult(){
		return this.cardinality;
	}

	/**
	 * This is not the correct implementation.
	 * TODO: Fix it
	 * @param c -
	 * @return -
	 */
	public boolean evaluate(final Cardinality c) {
		int max_c1 = c.getMaxOccurs(), max_c2 = cardinality.getMaxOccurs();
		int min_c1 = c.getMinOccurs(), min_c2 = cardinality.getMinOccurs();
		this.cardinality =  (CardinalityBuilder.between(Math.min(min_c2, min_c1), Math.max(max_c1,max_c2)));
		return true;
	}
}
