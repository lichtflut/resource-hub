/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import de.lichtflut.rb.webck.application.BrowsingHistory;
import de.lichtflut.rb.webck.application.RBWebSession;

/**
 * <p>
 *  Special model concerning {@link BrowsingHistory}.
 * </p>
 *
 * <p>
 * 	Created Dec 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class BrowsingContextModel {

	public static ConditionalModel<Boolean> isInEditMode() {
		return new ConditionalModel<Boolean>() {
			@Override
			public boolean isFulfilled() {
				return getObject();
			}
			
			@Override
			public Boolean getObject() {
				return RBWebSession.get().getHistory().isEditing();
			}
		};
	}
	
	public static ConditionalModel<Boolean> hasPredecessors() {
		return new ConditionalModel<Boolean>() {
			@Override
			public boolean isFulfilled() {
				return getObject();
			}
			
			@Override
			public Boolean getObject() {
				return RBWebSession.get().getHistory().hasPredecessors();
			}
		};
	}
	
}
