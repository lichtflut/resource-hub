/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.notes;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.models.resources.ResourcePropertyModel;

/**
 * <p>
 *  Panel for display and editing of a 'mezzle' a 'memo-frazzle'.
 * </p>
 *
 * <p>
 * 	Created Jan 3, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class MezzlePanel extends TypedPanel<ResourceNode> {
	
	/**
	 * Constructor.
	 * @param id The ID.
	 * @param model The model containing the mezzle node.
	 */
	@SuppressWarnings("rawtypes")
	public MezzlePanel(final String id, final IModel<ResourceNode> model) {
		super(id, model);
		
		add(new Label("content", new ResourcePropertyModel(model, RB.HAS_CONTENT)));
		
		add(new AjaxLink("edit") {
			@Override
			public void onClick(AjaxRequestTarget target) {
			}
		});
	}
	
}
