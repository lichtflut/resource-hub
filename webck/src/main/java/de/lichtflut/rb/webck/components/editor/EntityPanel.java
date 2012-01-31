/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.editor;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.hasSchema;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.behaviors.FocusFirstFormElementBehavior;
import de.lichtflut.rb.webck.browsing.BrowsingState;
import de.lichtflut.rb.webck.browsing.EntityBrowsingStep;
import de.lichtflut.rb.webck.components.ResourceInfoPanel;
import de.lichtflut.rb.webck.components.common.GoogleMapsPanel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
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
	
	/**
	 * Constructor.
	 * @param id The ID.
	 * @param model The model.
	 */
	public EntityPanel(final String id, final IModel<RBEntity> model) {
		super(id, model);
		
		setOutputMarkupId(true);
		
		add(new ResourceInfoPanel("infoPanel", model));
		add(new FeedbackPanel("feedbackPanel"));
		
		add(createRows(new RBFieldsListModel(model)));
		
		add(new GoogleMapsPanel("map", new DerivedModel<String, RBEntity>(model) {
			@Override
			protected String derive(RBEntity original) {
				if (original != null && RB.ADDRESS.equals(original.getType())) {
					return original.getLabel();
				} else {
					return null;
				}
			}
		}));
		add(visibleIf((hasSchema(model))));
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	protected void onConfigure() {
		super.onConfigure();
		if (!isInViewMode()) {
			add(new FocusFirstFormElementBehavior());
		}
	}
	
	protected Component createRows(final IModel<List<RBField>> listModel) {
		final ListView<RBField> view = new ListView<RBField>("rows", listModel) {
			@Override
			protected void populateItem(ListItem<RBField> item) {
				if (isInViewMode()) {
					item.add(new EntityRowDisplayPanel("row", item.getModel()));
				} else {
					item.add(new EntityRowEditPanel("row", item.getModel())); 
				} 
			}
		};
		return view;
	}
	
	private boolean isInViewMode() {
		final EntityBrowsingStep step = RBWebSession.get().getHistory().getCurrentStep();
		if (step != null) {
			return BrowsingState.VIEW.equals(step.getState());
		} else {
			return true;
		}
	}
	
}
