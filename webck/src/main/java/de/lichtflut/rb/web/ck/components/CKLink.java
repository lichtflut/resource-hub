/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;

import de.lichtflut.rb.core.spi.RBServiceProvider;


/**
 * [TODO Insert description here.
 * TODO: ADD OTHER LINKTYPES {@link CKLinkType}
 *
 * Created: Jun 22, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class CKLink extends CKComponent{

	@SuppressWarnings("unused")
	private final Object link;
	/**
	 * Constructor.
	 * @param destination -
	 * @param linkLabel -
	 * @param type -
	 */
	public CKLink(final String destination, final String linkLabel, final CKLinkType type){
		super("menuLink");
		if(type != CKLinkType.EXTERNAL_LINK) {
			throw new UnsupportedOperationException();
		}
		link = new ExternalLink("menuLink", destination);
	}

	/**
	 * TODO: DESCRIPTION.
	 * @param label -
	 * @param cls -
	 * @param type -
	 */
	public CKLink(final String label, final WebPage cls, final CKLinkType type){
		super("menuLink");
		if(type != CKLinkType.WEB_PAGE_CLASS) {
			throw new UnsupportedOperationException();
		}
		link = new Link("label"){

			@Override
			public void onClick() {
				setResponsePage(cls);
			}
		};
	}

	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		// TODO Auto-generated method stub

	}

	@Override
	public RBServiceProvider getServiceProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CKComponent setViewMode(final ViewMode mode) {
		// TODO Auto-generated method stub
		return null;
	}
}
