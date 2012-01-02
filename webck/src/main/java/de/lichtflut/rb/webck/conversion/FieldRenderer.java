/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.conversion;

import java.io.Serializable;
import java.util.Locale;

/**
 * <p>
 *  Simple interface for to-string renderers.
 * </p>
 *
 * <p>
 * 	Created Dec 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface FieldRenderer<T> extends Serializable {
	
	String render(T object, Locale locale);

}