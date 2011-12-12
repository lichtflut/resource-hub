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
	
	public static void add(Component...components) {
		AjaxRequestTarget target = AjaxRequestTarget.get();
		if (target != null) {
			target.add(components);
		}
	}

}
