/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.odlabs.wiquery.ui.dialog.Dialog;

import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.components.dialogs.SchemaExportDialog;
import de.lichtflut.rb.webck.components.dialogs.SchemaImportDialog;

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
	
	@SpringBean
	private ServiceProvider provider;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	@SuppressWarnings("rawtypes")
	public SchemaIOPanel(final String id) {
		super(id);
		
		final Dialog exportDialog = createExportDialog();
		add(exportDialog);
		
		final Link exportLink = new AjaxFallbackLink("exportLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				exportDialog.open(target);
			}
		};
		add(exportLink);
		
		final Dialog importDialog = createImportDialog();
		add(importDialog);
		
		final Link importLink = new AjaxFallbackLink("importLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				importDialog.open(target);
			}
		};
		add(importLink);
	}
	
	// ----------------------------------------------------
	
	protected SchemaManager getSchemaManager() {
		return provider.getSchemaManager();
	}
	
	// -----------------------------------------------------
	
	private Dialog createExportDialog() {
		final Dialog dialog = new SchemaExportDialog("exportDialog") {
			@Override
			public SchemaManager getSchemaManager() {
				return SchemaIOPanel.this.getSchemaManager();
			}
		};
		return dialog;
	}
	
	private Dialog createImportDialog() {
		final Dialog dialog = new SchemaImportDialog("importDialog") {
			@Override
			public SchemaManager getSchemaManager() {
				return SchemaIOPanel.this.getSchemaManager();
			}
		};
		return dialog;
	}
	
}
