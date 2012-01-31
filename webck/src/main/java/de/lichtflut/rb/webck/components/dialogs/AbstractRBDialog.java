/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import org.apache.wicket.markup.html.panel.IMarkupSourcingStrategy;
import org.apache.wicket.markup.html.panel.PanelMarkupSourcingStrategy;
import org.odlabs.wiquery.ui.dialog.Dialog;

/**
 * <p>
 *  Abstract base dialog.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class AbstractRBDialog extends Dialog {
	
	public static final String CONTENT = "content";
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	public AbstractRBDialog(final String id) {
		super(id);
		
		setModal(true);
	}
	
	// ----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IMarkupSourcingStrategy newMarkupSourcingStrategy() {
		return new PanelMarkupSourcingStrategy(false);
	}

}
