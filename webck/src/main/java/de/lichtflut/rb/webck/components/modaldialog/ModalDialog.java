/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.modaldialog;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Sep 22, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@Deprecated
public class ModalDialog extends Panel {
	
	public static final String CONTENT_ID = "content";
	
	enum DISPLAY_ACTION {OPEN, CLOSE, NOTHING}
	
	private DISPLAY_ACTION action = DISPLAY_ACTION.NOTHING;
	
	// -----------------------------------------------------

	/**
	 * Constructor. 
	 */
	public ModalDialog(final String id, IModel<String> title) {
		super(id);
		
		setOutputMarkupPlaceholderTag(true);
		setVisible(false);
		
		add(new AttributeAppender("title", title));
	}
	
	/**
	 * Constructor. 
	 */
	public ModalDialog(final String id, IModel<String> title, Component content) {
		this(id, title);
		setContent(content);
	}
	
	// -----------------------------------------------------
	
	public void setContent(final Component content) {
		if (!CONTENT_ID.equals(content.getId())) {
			throw new IllegalArgumentException("ID of modal dialog content component must be " + CONTENT_ID);
		}
		addOrReplace(content);
	}
	
	// -----------------------------------------------------
	
	public void show() {
		setVisible(true);
		action = DISPLAY_ACTION.OPEN;
		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().add(this);
		}
	}
	
	public void close() {
		setVisible(false);
		action = DISPLAY_ACTION.CLOSE;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		switch(action) {
		case OPEN:
			response.renderOnDomReadyJavaScript("$('#" + getMarkupId() + "')" +
					".dialog({modal: true, width: 600, height:250})");
			break;
		case CLOSE:
			response.renderOnDomReadyJavaScript("$('#" + getMarkupId() + "').dialog('close')");
			break;
		default:
			break;
		}
	}
	
}
