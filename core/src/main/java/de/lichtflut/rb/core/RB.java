/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

/**
 *  <p>
 *   A priori known URIs for RB.
 * </p>
 *
 * Created: Aug 25, 2011
 *
 * @author Ravi Knox
 */
public interface RB {

	String NAMESPACE_URI = "http://lichtflut.de/rb#";

	/**
	 * Each RBEntity may have a primary image URL/ID.
	 */
	ResourceID HAS_IMAGE = new SimpleResourceID(NAMESPACE_URI, "hasImage");

	/**
	 * Each RBEntity may have a short literal description.
	 */
	ResourceID HAS_SHORT_DESC = new SimpleResourceID(NAMESPACE_URI, "hasShortDescription");

}
