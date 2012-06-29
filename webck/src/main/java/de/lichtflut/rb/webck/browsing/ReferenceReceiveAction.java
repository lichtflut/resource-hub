/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.browsing;

import de.lichtflut.rb.core.entity.RBEntity;
import org.arastreju.sge.ModelingConversation;

import java.io.Serializable;

/**
 * <p>
 *  Action to be executed when a new entity has been created in a page flow. 
 * </p>
 *
 * <p>
 * 	Created Dec 2, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ReferenceReceiveAction<T> extends Serializable {
	
	void execute(ModelingConversation conversation, RBEntity target);
	
}
