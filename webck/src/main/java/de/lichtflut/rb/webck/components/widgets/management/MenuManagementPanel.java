/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.management;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.viewspec.MenuItem;
import de.lichtflut.rb.webck.common.OrderedNodesContainer;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.common.impl.SerialNumberOrderedNodesContainer;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.CastingModel;
import de.lichtflut.rb.webck.models.viewspecs.MenuItemListModel;

/**
 * <p>
 *  Panel for management of perspectives.
 * </p>
 *
 * <p>
 * 	Created Feb 6, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class MenuManagementPanel extends Panel {
	
	@SpringBean
	private ServiceProvider provider;
	private OrderedNodesContainer container;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public MenuManagementPanel(String id) {
		super(id);
		
		setOutputMarkupId(true);
		
		final MenuItemListModel listModel = new MenuItemListModel();
		final IModel<List<? extends ResourceNode>> orderModel = 
				new CastingModel<List<? extends ResourceNode>>(listModel);
		
		container = new SerialNumberOrderedNodesContainer(orderModel);
		
		final ListView<MenuItem> view = new ListView<MenuItem>("list", listModel) {
			@Override
			protected void populateItem(final ListItem<MenuItem> item) {
				final MenuItem menuItem = item.getModelObject();
				item.add(new Label("name", menuItem.getName()));
				item.add(new Label("perspective", menuItem.getPerspective().getName()));
				item.add(createEditLink(item.getModel()));
				item.add(createDeleteLink(menuItem));
				item.add(createUpLink(menuItem));
				item.add(createDownLink(menuItem));
			}
		};
		add(view);
		
		final AjaxLink link = new AjaxLink("create") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				final DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new CreateMenutItemDialog(hoster.getDialogID()));
			}
		};
		add(link);
		
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onEvent(IEvent<?> event) {
		if (ModelChangeEvent.from(event).isAbout(ModelChangeEvent.VIEW_SPEC)) {
			updatePanel();
		}
	}
	
	// ----------------------------------------------------
	
	@SuppressWarnings("rawtypes")
	private AjaxLink createDeleteLink(final MenuItem item) {
		final AjaxLink link = new AjaxLink("delete") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				provider.getViewSpecificationService().removeUsersItem(item);
				updatePanel();
			}
		};
		return link;
	}
	
	@SuppressWarnings("rawtypes")
	private AjaxLink createEditLink(final IModel<MenuItem> item) {
		final AjaxLink link = new AjaxLink("edit") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				final DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new EditMenutItemDialog(hoster.getDialogID(), item));
			}
		};
		return link;
	}
	
	@SuppressWarnings("rawtypes")
	private AjaxLink createUpLink(final MenuItem item) {
		final AjaxLink link = new AjaxLink("up") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				container.moveUp(item, 1);
				updatePanel();
			}
		};
		return link;
	}
	
	@SuppressWarnings("rawtypes")
	private AjaxLink createDownLink(final MenuItem item) {
		final AjaxLink link = new AjaxLink("down") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				container.moveDown(item, 1);
				updatePanel();
			}
		};
		return link;
	}
	
	// ----------------------------------------------------
	
	protected void updatePanel() {
		RBAjaxTarget.add(this);
	}
	
}
