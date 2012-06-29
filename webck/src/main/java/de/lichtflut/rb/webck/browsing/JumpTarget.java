/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
