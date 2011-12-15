/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infomanagement;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.arastreju.sge.model.DefaultSemanticGraph;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.odlabs.wiquery.ui.dialog.Dialog;

import de.lichtflut.rb.core.services.ServiceProvider;
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
public abstract class InformationIOPanel extends Panel {
	
	/**
	 * Constructor.
	 */
	@SuppressWarnings("rawtypes")
	public InformationIOPanel(final String id) {
		super(id);
		
		final Dialog exportDialog = new InformationExportDialog("exportDialog") {
			protected SemanticGraph getExportGraph() {
				final ServiceProvider sp = getServiceProvider();
				final List<SNClass> types = sp.getTypeManager().findAllTypes();
				
				final SemanticGraph graph = new DefaultSemanticGraph();
				for (SNClass type : types) {
					final List<ResourceNode> entities = sp.getArastejuGate().createQueryManager().findByType(type);
					for (ResourceNode entity : entities) {
						graph.merge(new DefaultSemanticGraph(entity));
					}
				}
				return graph;
			}
		};
		add(exportDialog);
		
		final Link exportLink = new AjaxFallbackLink("exportLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				exportDialog.open(target);
			}
		};
		add(exportLink);
		
		final Dialog importDialog = new InformationImportDialog("importDialog") {
			@Override
			protected ServiceProvider getServiceProvider() {
				return InformationIOPanel.this.getServiceProvider();
			}
		};
		add(importDialog);
		
		final Link importLink = new AjaxFallbackLink("importLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				importDialog.open(target);
			}
		};
		add(importLink);
	}
	
	// -----------------------------------------------------
	
	protected abstract ServiceProvider getServiceProvider();
	
}
