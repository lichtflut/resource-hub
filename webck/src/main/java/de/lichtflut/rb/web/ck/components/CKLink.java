/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.lichtflut.rb.core.spi.IRBServiceProvider;


/**
 * [TODO Insert description here.
 *
 * Created: Jun 22, 2011
 *
 * @author Ravi Knox
 * TODO: add ResourceLink ' CKLink(IRBEntity e){}; '
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
	 * 	<li>{@link CKL}, the ck-components-class</li>
	 * 	<li>{@link String}, the link label</li>
	 * 	<li>{@link CKComponent}, the ck-component parent-class</li>
	 * </ol>
	 * </p>
	 */
	public static final String ON_LINK_CLICK_BEHAVIOR = "de.lichtflut.web.ck.behavior.onClick";

	private AbstractLink link;
	private String label;

	/**
	 * Constructor for External Link.
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
	 * Constructor for Internal Link.
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
					setResponsePage(cls);
			}
		};

		link.add(new Label("label", label));
		add(link);
	}

	/**
	 * Constructor for Internal Link using an instance of {@link WebPage}.
	 * @param id -
	 * @param label -
	 * @param page -
	 * @param type -
	 */
	public CKLink(final String id, final String label, final WebPage page,
			final CKLinkType type){
		super(id);
		if(type != CKLinkType.WEB_PAGE_INSTANCE) {
			throw new UnsupportedOperationException();
		}
		link = new Link("link"){
			@Override
			public void onClick() {
					setResponsePage(page);
			}
		};

		link.add(new Label("label", label));
		add(link);
	}

	/**
	 * Constructor for BookmarkablePageLink.
	 * @param id -
	 * @param label -
	 * @param cls -
	 * @param params -
	 * @param type -
	 */
	@SuppressWarnings("unchecked")
	public CKLink(final String id, final String label, final Class<? extends WebPage> cls, final PageParameters params,
			final CKLinkType type){
		super(id);
		if(type != CKLinkType.BOOKMARKABLE_WEB_PAGE_CLASS){
			throw new UnsupportedOperationException();
		}
		link = new BookmarkablePageLink("link", cls, params);
		link.add(new Label("label", label));
		add(link);
	}

	/**
	 * Constructor for Custom Link.
	 * @param id -
	 * @param label -
	 * @param type -
	 */
	public CKLink(final String id, final String label, final CKLinkType type){
		super(id);
		this.label = label;
		if(type != CKLinkType.CUSTOM_BEHAVIOR){
			throw new UnsupportedOperationException();
		}
		buildComponent();
	}

	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		link = new Link("link") {
			// TODO: add further infos of this obj for developers.
			@Override
			public void onClick() {
				if((getBehavior(ON_LINK_CLICK_BEHAVIOR) != null)){
					getBehavior(ON_LINK_CLICK_BEHAVIOR).execute();
				}
			}
		};
		link.add(new Label("label", label));
		add(link);
	}

	@Override
	public IRBServiceProvider getServiceProvider() {
		// Do nothing
		return null;
	}

	@Override
	public CKComponent setViewMode(final ViewMode mode) {
		// Do nothing
		return null;
	}
}
