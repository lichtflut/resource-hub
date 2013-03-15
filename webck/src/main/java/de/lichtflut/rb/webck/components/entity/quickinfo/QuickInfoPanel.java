/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity.quickinfo;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.entity.EntityRowDisplayPanel;
import de.lichtflut.rb.webck.models.ConditionalModel;

/**
 * <p>
 * This panel provides a {@link RBEntity}s quick-info.
 * </p>
 * Created: Feb 27, 2013
 *
 * @author Ravi Knox
 */
public class QuickInfoPanel extends TypedPanel<RBEntity> {

	/**
	 * Constructor.
	 * 
	 * @param id The component id
	 * @param model Contains the entity's resourceId
	 */
	public QuickInfoPanel(final String id, final IModel<RBEntity> model) {
		super(id, model);

		WebMarkupContainer container = new WebMarkupContainer("container");

		Label label = new Label("noInfo", new ResourceModel("label.no-info"));
		label.add(visibleIf(not(hasQuickInfo(model))));
		container.add(label);

		ListView<RBField> view = new ListView<RBField>("info", new ListModel<RBField>(model.getObject().getQuickInfo())) {
			@Override
			protected void populateItem(final ListItem<RBField> item) {
				item.add(new EntityRowDisplayPanel("row", item.getModel()));
			}
		};
		view.add(visibleIf(hasQuickInfo(model)));
		container.add(view);

		add(container);
	}

	// ------------------------------------------------------

	private ConditionalModel<Boolean> hasQuickInfo(final IModel<RBEntity> model){
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				if (model.getObject() == null || model.getObject().getQuickInfo().isEmpty()) {
					return false;
				}
				return true;
			}
		};
	}

}
