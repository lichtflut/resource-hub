/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infomanagement;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.DefaultSemanticGraph;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.rb.core.services.ServiceProvider;
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
	
	@SpringBean
	private ServiceProvider provider;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	@SuppressWarnings("rawtypes")
	public InformationIOPanel(final String id) {
		super(id);
		
		final Link exportLink = new AjaxFallbackLink("exportLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new InformationExportDialog(hoster.getDialogID(), createExportModel()));
			}
		};
		add(exportLink);
		
		final Link importLink = new AjaxFallbackLink("importLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new InformationImportDialog(hoster.getDialogID()));
			}
		};
		add(importLink);
	}
	
	// -----------------------------------------------------
	
	private IModel<SemanticGraph> createExportModel() {
		return new AbstractReadOnlyModel<SemanticGraph>() {
			@Override
			public SemanticGraph getObject() {
				final List<SNClass> types = provider.getTypeManager().findAllTypes();
				final SemanticGraph graph = new DefaultSemanticGraph();
				for (SNClass type : types) {
					final List<ResourceNode> entities = provider.getArastejuGate().createQueryManager().findByType(type);
					for (ResourceNode entity : entities) {
						graph.merge(new DefaultSemanticGraph(entity));
					}
				}
				return graph;
			}
		};
	}
	
}
