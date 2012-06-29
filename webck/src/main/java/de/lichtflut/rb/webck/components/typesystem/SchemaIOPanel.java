/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import de.lichtflut.rb.webck.components.dialogs.SchemaExportDialog;
import de.lichtflut.rb.webck.components.dialogs.SchemaImportDialog;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.odlabs.wiquery.ui.dialog.Dialog;

/**
 * <p>
 *  IO Panel for schemas.
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SchemaIOPanel extends Panel {
	
	/**
	 * Constructor.
	 */
	@SuppressWarnings("rawtypes")
	public SchemaIOPanel(final String id) {
		super(id);
		
		final Dialog exportDialog = new SchemaExportDialog("exportDialog");
		add(exportDialog);
		
		final Link exportLink = new AjaxFallbackLink("exportLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				exportDialog.open(target);
			}
		};
		add(exportLink);
		
		final Dialog importDialog = new SchemaImportDialog("importDialog");
		add(importDialog);
		
		final Link importLink = new AjaxFallbackLink("importLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				importDialog.open(target);
			}
		};
		add(importLink);
	}
}
