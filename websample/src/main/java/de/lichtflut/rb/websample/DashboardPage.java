/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.websample;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.lichtflut.rb.websample.base.RBBasePage;

/**
 * <p>
 * Sample welcome/dashboard page for Web-CK.
 * </p>
 *
 * <p>
 * Created Jan 5, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public class DashboardPage extends RBBasePage {

	public DashboardPage(final PageParameters parameters) {
		super("Dashboard", parameters);
	}

}
