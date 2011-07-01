/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;

import de.lichtflut.rb.core.spi.RBServiceProvider;


/**
 * [TODO Insert description here.
 *
 * Created: Jun 22, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class CKLink extends CKComponent{


	//Behavior-Keys
	/**
	 * <p>
	 * This behavior is called, when the link is clicked.
	 * If not set, by default the page passed as an argument is called.
	 * Does not work for external-links!!
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined order:
	 * <ol>
	 * 	<li>{@link Class}, the ck-components-class</li>
	 * 	<li>String, the link label</li>
	 * 	<li>Component, the ck-components-class</li>
	 * </ol>
	 * </p>
	 */
	public static final String ON_LINK_CLICK_BEHAVIOR = "de.lichtflut.web.ck.behavior.onClick";

	private final AbstractLink link;

	/**
	 * Constructor.
	 * @param id -
 	 * @param label -
	 * @param destination -
	 * @param type -
	 */
	public CKLink(final String id, final String label, final String destination, final CKLinkType type){
		super(id);
		if(type != CKLinkType.EXTERNAL_LINK) {
			throw new UnsupportedOperationException();
		}
		link = new ExternalLink("link", destination);
		link.add(new Label("label", label));
		add(link);
	}

	/**
	 * TODO: DESCRIPTION.
	 * @param id -
	 * @param label -
	 * @param cls -
	 * @param type -
	 */
	public CKLink(final String id, final String label, final Class<? extends WebPage> cls,
			final CKLinkType type){
		super(id);
		if(type != CKLinkType.WEB_PAGE_CLASS) {
			throw new UnsupportedOperationException();
		}
		link = new Link("link"){
			@Override
			public void onClick() {
				if((getBehavior(ON_LINK_CLICK_BEHAVIOR) != null)){
					getBehavior(ON_LINK_CLICK_BEHAVIOR).execute(this, label, getParent());
				}else{
//					((CKComponent)this.getParent()).getBehavior(key)
					setResponsePage(cls);
				}
			}
		};

		link.add(new Label("label", label));
		add(link);
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
