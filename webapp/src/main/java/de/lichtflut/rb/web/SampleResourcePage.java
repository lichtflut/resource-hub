/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jan 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SampleResourcePage extends WebPage {

	/**
	 * @param parameters
	 */
	public SampleResourcePage(final PageParameters parameters) {
		super(parameters);
		add(new TextField<String>("value"));
		add(new TextField<String>("resource"));
	}

}
