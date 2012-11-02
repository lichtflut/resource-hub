/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.hasSchema;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.webck.behaviors.FocusFirstFormElementBehavior;
import de.lichtflut.rb.webck.browsing.BrowsingState;
import de.lichtflut.rb.webck.browsing.EntityBrowsingStep;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.common.GoogleMapsPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.fields.RBFieldsListModel;

/**
 * <p>
 *  Panel for displaying and editing of an {@link RBEntity}.
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntityPanel extends Panel {

	public static final String VIEW_COMP_ID = "rows";

	@SpringBean
	private SemanticNetworkService semanticNetwork;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param model The model.
	 */
	public EntityPanel(final String id, final IModel<RBEntity> model) {
		super(id, model);

		add(new FeedbackPanel("feedbackPanel"));

		add(createRows(new RBFieldsListModel(model)));

		add(new GoogleMapsPanel("map", new DerivedDetachableModel<String, RBEntity>(model) {
			@Override
			protected String derive(final RBEntity original) {
				final ResourceID type = semanticNetwork.resolve(original.getType());
				if (type.asResource().asClass().isSpecializationOf(RB.LOCATION)) {
					return original.getLabel();
				} else {
					return null;
				}
			}
		}));
		add(visibleIf((hasSchema(model))));
	}

	// ----------------------------------------------------

	@Override
	public void onEvent(final IEvent<?> event) {
		final ModelChangeEvent<?> mce = ModelChangeEvent.from(event);
		if (mce.isAbout(ModelChangeEvent.ENTITY)) {
			getListView().removeAll();
		}
	}

	@Override
	protected void onConfigure() {
		super.onConfigure();
		if (!isInViewMode()) {
			add(new FocusFirstFormElementBehavior());
		}
	}

	// ----------------------------------------------------

	protected Component createRows(final IModel<List<RBField>> listModel) {
		final ListView<RBField> view = new ListView<RBField>(VIEW_COMP_ID, listModel) {
			@Override
			protected void populateItem(final ListItem<RBField> item) {
				if (isInViewMode()) {
					item.add(new EntityRowDisplayPanel("row", item.getModel()));
				} else if (isEmbedded(item.getModel())) {
					item.add(new EmbeddedReferencePanel("row", item.getModel()));
				} else {
					item.add(new EntityRowEditPanel("row", item.getModel()));
				}
			}
		};
		view.setReuseItems(true);
		return view;
	}

	@SuppressWarnings("unchecked")
	protected ListView<RBField> getListView() {
		return (ListView<RBField>) get(VIEW_COMP_ID);
	}

	private boolean isInViewMode() {
		final EntityBrowsingStep step = RBWebSession.get().getHistory().getCurrentStep();
		return step == null || BrowsingState.VIEW.equals(step.getState());
	}


	private boolean isEmbedded(final IModel<RBField> model) {
		return model.getObject().getVisualizationInfo().isEmbedded();
	}

}
