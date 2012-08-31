/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import de.lichtflut.rb.core.entity.RBEntity;

/**
 * <p>
 * This class provides some methods to create URIs of {@link RBEntity}s.
 * </p>
 * Created: Aug 15, 2012
 *
 * @author Ravi Knox
 */
public class LinkProvider {

	// ------------------------------------------------------

	public String buildRepositoryStructureFor(final RBEntity entity, final String fileName) {
		String path = entity.getType().toURI();
		path = path + "/" + entity.getID();
		path = path.replace("http://", "").replace(".", "/").replace("#", "/");
		path = path.concat("/" + fileName);
		return path;
	}

}
