/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.SemanticGraph;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.ViewSpecExportTraverser;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.dialogs.InformationExportDialog;
import de.lichtflut.rb.webck.components.widgets.EntityListWidget;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

/**
 * <p>
 *  Configuration panel of a {@link EntityListWidget}.
 * </p>
 *
 * <p>
 * 	Created Jan 26, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntityListWidgetConfigPanel extends TypedPanel<WidgetSpec> {
	
	/**
	 * @param id
	 * @param model
	 */
	@SuppressWarnings("rawtypes")
	public EntityListWidgetConfigPanel(String id, IModel<WidgetSpec> model) {
		super(id, model);
		
		final IModel<SemanticGraph> exportModel = new DerivedModel<SemanticGraph, WidgetSpec>(model) {
			@Override
			protected SemanticGraph derive(WidgetSpec spec) {
				return new ViewSpecExportTraverser().toGraph(spec);
			}
		};
		
		final Link exportLink = new AjaxFallbackLink("exportLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new InformationExportDialog(hoster.getDialogID(), exportModel));
			}
		};
		add(exportLink);
		
	}

}
