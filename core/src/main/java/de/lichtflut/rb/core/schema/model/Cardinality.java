/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.io.Serializable;

/**
 * <p>
 *  The cardinality of a PropertyDeclaration.
 *  In contrast to the concept of 'cardinality' in software modeling or design
 *  the cardinality of a PropertyDeclaration is rather a recommendation than obligatory.
 * </p>
 *
 * <p>
 * 	<code>isUnbound()</code> must be true if max occurs will be grater than Integer.MAX_VALUE;
 * </p>
 *
 * <p>
 * 	Created Feb 3, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface Cardinality extends Serializable {

	/**
	 * Returns if Attribute can have unlimited values.
	 * @return boolean
	 */
	boolean isUnbound();

	/**
	 * Returns min. occurence of a value.
	 * @return int
	 */
	int getMinOccurs();

	/**
	 * Returns max. occurence of a value.
	 * @return int
	 */
	int getMaxOccurs();

}
