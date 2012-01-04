/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common;

import java.io.Serializable;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  Simple action interface.
 * </p>
 *
 * <p>
 * 	Created Dec 2, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface Action<T> extends Serializable {
	
	void execute(ServiceProvider serviceProvider, RBEntity target);
	
}
