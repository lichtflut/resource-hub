/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infomanagement;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.InformationExportDialog;
import de.lichtflut.rb.webck.components.dialogs.InformationImportDialog;

/**
 * <p>
 *  Panel for import/export of information of semantic graph.
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class InformationIOPanel extends Panel {

	/**
	 * Constructor.
	 */
	public InformationIOPanel(final String id) {
		super(id);

		final Link<?> exportLink = new AjaxFallbackLink<Void>("exportLink") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new InformationExportDialog(hoster.getDialogID()));
			}
		};
		add(exportLink);

		final Link<?> importLink = new AjaxFallbackLink<Void>("importLink") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new InformationImportDialog(hoster.getDialogID()));
			}
		};
		add(importLink);
	}

}
