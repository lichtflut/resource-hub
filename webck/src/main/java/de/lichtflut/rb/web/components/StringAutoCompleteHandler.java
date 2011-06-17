/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import java.io.Serializable;
import java.util.Iterator;

/**
 * StringAutoCompleteHandler.
 *
 * @author Aderhold.Erik
 */
public interface StringAutoCompleteHandler extends Serializable {

	/**
	 * Ermittelt die Auswahlm�glichkeiten des StringAutoCompleteFeldes.
	 *
	 * @param input the input
	 * @return Iterator<String> Auswahlm�glichkeiten
	 */
	Iterator<String> getChoices(final String input);
}
