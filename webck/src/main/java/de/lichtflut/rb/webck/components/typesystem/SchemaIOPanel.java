/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.InformationImportDialog;
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
		
		final Link exportLink = new AjaxFallbackLink("exportLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
                DialogHoster hoster = findParent(DialogHoster.class);
                hoster.openDialog(new SchemaExportDialog(hoster.getDialogID()));
			}
		};
		add(exportLink);
		
		final Link importLink = new AjaxFallbackLink("importLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
                DialogHoster hoster = findParent(DialogHoster.class);
                hoster.openDialog(new SchemaImportDialog(hoster.getDialogID()));
			}
		};
		add(importLink);
	}
}
