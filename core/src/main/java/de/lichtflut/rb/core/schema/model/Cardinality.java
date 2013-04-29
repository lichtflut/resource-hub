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
