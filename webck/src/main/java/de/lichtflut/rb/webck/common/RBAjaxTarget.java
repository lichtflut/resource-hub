/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.common;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.cycle.RequestCycle;

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
		AjaxRequestTarget target = getAjaxTarget();
		if (target != null) {
			target.add(components);
		}
	}

	/**
	 * Preparation for Wicket 6.0, where AjaxRequestTarget.get() will be removed.
	 * @return The AjaxRequestTarget.
	 */
	public static AjaxRequestTarget getAjaxTarget() {
		return RequestCycle.get().find(AjaxRequestTarget.class);
	}

}
