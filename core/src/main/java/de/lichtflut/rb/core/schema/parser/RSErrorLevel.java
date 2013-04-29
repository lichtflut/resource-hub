/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

	/**
	 * Constructor.
	 * @param level -
	 */
	private RSErrorLevel(final int level) {
		//Bit-masking enum shit SYSTEM.add(GRAMMAR.add(...).....).add(.....)
		this.value = 1 << level;
	}

	// ----------------------------

	/**
	 * Constructor.
	 * @param lvl -
	 */
	private RSErrorLevel(final RSErrorLevel[] lvl) {
		for (RSErrorLevel errorLevel : lvl) {
			this.add(errorLevel);
		}
	}

	// ----------------------------

	/**
	 * TODO: DESCRIPTION.
	 * @param lvl -
	 * @return boolean
	 */
	public boolean contains(final RSErrorLevel lvl) {
		if ((lvl.value & this.value) != 0){
			return true;
		}
		return false;
	}

	// ----------------------------

	/**
	 * Self returning idiom 4tw.
	 * @param lvl -
	 * @return RSErrorLevel -
	 */
	public RSErrorLevel add(final RSErrorLevel lvl) {
		if (!contains(lvl)){
			this.value += lvl.value;
		}
		return this;
	}

}
