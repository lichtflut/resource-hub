/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * <p>
 *  Helper for {@link AjaxRequestTarget}.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBAjaxTarget {
	
	/**
	 * Add one or more components to the AjaxRequestTarget - if there exists one.
	 * @param components The components to be added.
	 */
	public static void add(Component...components) {
		AjaxRequestTarget target = AjaxRequestTarget.get();
		if (target != null) {
			target.add(components);
		}
	}

	/**
	 * Preperation for Wicket 6.0, where AjaxRequestTarget.get() will be removed.
	 * @return The AjaxRequestTarget.
	 */
	public static AjaxRequestTarget getAjaxTarget() {
		return AjaxRequestTarget.get();
	}

}
