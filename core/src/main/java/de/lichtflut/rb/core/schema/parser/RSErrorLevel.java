/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

/**
 * <p>
 * Enum to represent the ErrorLevel<br />
 * in which an occurred error message has to be classified<br/>
 * or can be filtered from.
 * You can combine several ErrorLevels in using the "add"-method
 * </p>
 * 
 * Created: Apr 29, 2011
 *
 * @author Nils Bleisch
 */
public enum RSErrorLevel {

	// ----------------------------

	SYSTEM(0), INTERPRETER(1), GRAMMAR(2), ALL(new RSErrorLevel[] { SYSTEM,
			INTERPRETER, GRAMMAR });
	private int value = 0;

	private RSErrorLevel(int level) {
		//Bit-masking enum shit SYSTEM.add(GRAMMAR.add(...).....).add(.....)
		this.value = 1 << level;
	}

	// ----------------------------

	private RSErrorLevel(RSErrorLevel[] lvl) {
		for (RSErrorLevel errorLevel : lvl) {
			this.add(errorLevel);
		}
	}

	// ----------------------------

	public boolean contains(RSErrorLevel lvl) {

		if ((lvl.value & this.value) != 0)
			return true;
		return false;
	}

	// ----------------------------

	/**
	 * Self returning idiom 4tw
	 */
	public RSErrorLevel add(RSErrorLevel lvl) {
		if (!contains(lvl))
			this.value += lvl.value;
		return this;
	}	
	
}
