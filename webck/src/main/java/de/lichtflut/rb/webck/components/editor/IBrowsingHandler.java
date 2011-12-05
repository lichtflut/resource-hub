/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.editor;

import de.lichtflut.rb.core.entity.EntityHandle;

/**
 * <p>
 *  Interface for a component that supports browsing to another entity.
 * </p>
 *
 * <p>
 * 	Created Dec 2, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface IBrowsingHandler {
	
	CharSequence getUrlToResource(EntityHandle handle);
	
	void browseTo(EntityHandle handle, boolean editable);

}
