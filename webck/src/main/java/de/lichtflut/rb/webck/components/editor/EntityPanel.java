/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.editor;

import java.util.List;

import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.ResourceInfoPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.RBFieldsListModel;

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
	
	private IModel<Boolean> editable;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param model The model.
	 * @param readonly
	 */
	public EntityPanel(final String id, final IModel<RBEntity> model, final IModel<Boolean> editable) {
		super(id, model);
		this.editable = editable;
		
		add(new ResourceInfoPanel("infoPanel", model));
		add(new FeedbackPanel("feedbackPanel"));
		
		add(createRows(new RBFieldsListModel(model)));
		
	}
	
	// ----------------------------------------------------
	
	public boolean isEditable() {
		return editable.getObject();
	}
	
	protected ListView<RBField> createRows(final IModel<List<RBField>> listModel) {
		final ListView<RBField> view = new ListView<RBField>("rows", listModel) {
			@Override
			protected void populateItem(ListItem<RBField> item) {
				if (isEditable()) {
					item.add(new EntityRowEditPanel("row", item.getModel())); 
				} else {
					item.add(new EntityRowDisplayPanel("row", item.getModel()));
				}
			}
		};
		return view;
	}
	
}
