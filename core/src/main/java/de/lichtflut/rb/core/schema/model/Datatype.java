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

import org.arastreju.sge.eh.meta.NotYetSupportedException;
import org.arastreju.sge.model.ElementaryDataType;

/**
 * <p>
 *  Enumeration of data types.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public enum Datatype {

	BOOLEAN,
	INTEGER,
	DECIMAL,
	STRING,
	TEXT,
	RICH_TEXT,
	TIMESTAMP,
	DATE,
	TIME_OF_DAY,
	URI,
	FILE,

	RESOURCE;

	// ----------------------------------------------------

	public static ElementaryDataType getCorrespondingArastrejuType(final Datatype type) {
		switch(type) {
		case BOOLEAN:
			return ElementaryDataType.BOOLEAN;
		case INTEGER:
			return ElementaryDataType.INTEGER;
		case DATE:
			return ElementaryDataType.DATE;
		case DECIMAL:
			return ElementaryDataType.DECIMAL;
		case TIME_OF_DAY:
			return ElementaryDataType.TIME_OF_DAY;
		case TIMESTAMP:
			return ElementaryDataType.TIMESTAMP;
		case RESOURCE:
			return ElementaryDataType.RESOURCE;
		case STRING:
		case TEXT:
		case RICH_TEXT:
			return ElementaryDataType.STRING;
		case URI:
		case FILE:
			return ElementaryDataType.URI;
		default:
			throw new NotYetSupportedException(type);
		}
	}

}
