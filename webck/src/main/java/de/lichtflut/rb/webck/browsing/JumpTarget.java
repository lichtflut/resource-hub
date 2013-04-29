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
package de.lichtflut.rb.webck.browsing;

import org.apache.wicket.Page;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serializable;

/**
 * <p>
 *  Target for a jump to a page.
 * </p>
 *
 * <p>
 * 	Created Dec 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class JumpTarget implements Serializable {
	
	private final Class<? extends Page> pageClass;
	
	private final PageParameters params;
	
	// ----------------------------------------------------

	/**
	 * @param pageClass
	 */
	public JumpTarget(Class<? extends Page> pageClass) {
		this(pageClass, new PageParameters());
	}
	
	/**
	 * @param pageClass
	 * @param params
	 */
	public JumpTarget(Class<? extends Page> pageClass, PageParameters params) {
		this.pageClass = pageClass;
		this.params = params;
	}
	
	// ----------------------------------------------------
	
	public void activate(RequestCycle cycle) {
		cycle.setResponsePage(pageClass, params);
	}
	

}
