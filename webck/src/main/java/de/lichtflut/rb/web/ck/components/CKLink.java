/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import org.apache.wicket.markup.html.link.Link;


/**
 * [TODO Insert description here.
 *
 * Created: Jun 22, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings({ "rawtypes", "serial" })
public abstract class CKLink extends Link{

	/**
	 * Constructor.
	 * @param id -
	 */
	public CKLink(final String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract void onClick();

}
