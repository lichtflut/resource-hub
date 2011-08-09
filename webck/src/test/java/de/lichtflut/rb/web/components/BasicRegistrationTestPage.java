/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import org.apache.wicket.markup.html.WebPage;

import de.lichtflut.rb.web.components.registration.BasicRegistration;

/**
 * This page is used to test the {@link BasicRegistration}.
 *
 * Created: Aug 9, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class BasicRegistrationTestPage extends WebPage{

	/**
	 * Default Constructor.
	 */
	public BasicRegistrationTestPage(){
		this.add(new BasicRegistration("registration"));
	}

}
