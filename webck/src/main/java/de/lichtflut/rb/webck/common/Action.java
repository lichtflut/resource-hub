/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common;

import java.io.Serializable;

import de.lichtflut.rb.core.entity.RBEntity;

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
	
	void setValue(T value);

	void execute(RBEntity target);
	
}
