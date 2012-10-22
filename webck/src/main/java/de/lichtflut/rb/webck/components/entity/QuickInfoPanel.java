/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;

/**
 * <p>
 * This Panel displays a {@link RBEntity}s' quick-info.
 * </p>
 * Created: Sep 24, 2012
 *
 * @author Ravi Knox
 */
public class QuickInfoPanel extends Panel {

	/**
	 * @param id - wicket:id
	 * @param model - {@link IModel} for {@link RBEntity}
	 */
	public QuickInfoPanel(final String id, final IModel<RBEntity> model) {
		super(id, model);
		createTitle(model);
		createListView(model);
	}

	// ------------------------------------------------------

	/**
	 * Add the entities' title to the panel.
	 * @param model
	 */
	private void createTitle(final IModel<RBEntity> model) {
		add(new Label("label", model.getObject().getLabel()));
	}

	/**
	 * Add quick-info.
	 * @param model
	 */
	private void createListView(final IModel<RBEntity> model) {
		add(new ListView<RBField>("quickInfo", new ListModel<RBField>(model.getObject().getQuickInfo())) {

			@Override
			protected void populateItem(final ListItem<RBField> item) {
				item.add(new EntityRowDisplayPanel("rbField", item.getModel()));
			}

		});
	}

}
