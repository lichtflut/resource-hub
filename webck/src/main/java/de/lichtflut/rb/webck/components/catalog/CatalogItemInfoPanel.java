/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.catalog;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

/**
 * <p>
 * This Panel provides some Information about a CatalogItem using the {@link RB#HAS_DESCRIPTION} attribute.
 * 
 * Created: Feb 8, 2013
 *
 * @author Ravi Knox
 */
public class CatalogItemInfoPanel extends Panel {

	/**
	 * Constructor.
	 * 
	 * @param id Component id
	 * @param model The IModel
	 */
	public CatalogItemInfoPanel(final String id, final IModel<ResourceNode> model) {
		super(id, model);
		addInfoLabel("info", model);
	}

	// ------------------------------------------------------

	private void addInfoLabel(final String id, final IModel<ResourceNode> model) {
		IModel<String> labelModel = getLabelModel(model);
		add(new Label(id, labelModel).setEscapeModelStrings(false));
	}

	private IModel<String> getLabelModel(final IModel<ResourceNode> model) {
		return new DerivedModel<String, ResourceNode>(model) {

			@Override
			protected String derive(final ResourceNode original) {
				SemanticNode node = SNOPS.fetchObject(original, RB.HAS_DESCRIPTION);
				String info = CatalogItemInfoPanel.this.getLocalizer().getString("label.no-description",CatalogItemInfoPanel.this);
				if(null != node && node.isValueNode()){
					info = node.asValue().getStringValue();
				}
				return info;
			}
		};
	}

}
