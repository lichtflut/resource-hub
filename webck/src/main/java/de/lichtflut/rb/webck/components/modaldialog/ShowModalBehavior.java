/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.modaldialog;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * <p>
 *  Behavior that will display a panel as modal dialog.
 * </p>
 *
 * <p>
 * 	Created Sep 22, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ShowModalBehavior extends Behavior {
	
	enum STATE {OPEN, CLOSED}
	
	private STATE state = STATE.CLOSED;
	
	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void bind(final Component component) {
		component.setOutputMarkupId(true);
		component.setOutputMarkupPlaceholderTag(true);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(final Component component, final IHeaderResponse response) {
		super.renderHead(component, response);
		switch (state) {
		case OPEN:
			if (!component.isVisible()) {
				response.renderOnDomReadyJavaScript("$('#" + component.getMarkupId() + "').dialog('close')");
			}
			break;
		case CLOSED:
			if (component.isVisible()) {
				response.renderOnDomReadyJavaScript("$('#" + component.getMarkupId() + "')" +
					".dialog({modal: true, width: 600, height:400})");
			}
			break;

		default:
			break;
		}
		if (component.isVisible()) {
			state = STATE.OPEN;
		} else {
			state = STATE.CLOSED;
		}
	}
	
}
