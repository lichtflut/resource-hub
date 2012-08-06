/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import org.arastreju.sge.model.ElementaryDataType;

import de.lichtflut.infra.exceptions.NotYetSupportedException;

/**
 * <p>
 *  The datatypes of a {@link TypeDefinition}.
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
			return ElementaryDataType.URI;
		case FILE:
			return ElementaryDataType.File;
		default:
			throw new NotYetSupportedException(type);
		}
	}

}
