/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.RB;



/**
 * <p>
 * Some constants for testing purposes.
 * </p>
 * Created: Jan 7, 2013
 *
 * @author Ravi Knox
 */
public interface RBTestConstants {

	ResourceID ADDRESS = new SimpleResourceID(RB.COMMON_NAMESPACE_URI, "Address");

	ResourceID HAS_AVATAR = new SimpleResourceID(RB.COMMON_NAMESPACE_URI, "hasAvatar");

}
