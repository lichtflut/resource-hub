/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.catalog;

import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.widgets.builtin.ContentDisplayWidget;
import de.lichtflut.rb.webck.components.widgets.builtin.MyCompanyWidget;
import de.lichtflut.rb.webck.components.widgets.builtin.ThatsMeWidget;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Catalog panel containing available widgets.
 * </p>
 *
 * <p>
 * 	Created Feb 9, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class WidgetCatalogPanel extends Panel {

	/**
	 * Constructor.
	 * @param id The component ID.
	 */
	public WidgetCatalogPanel(String id) {
		super(id);
		
		add(createListView("basic", basicListModel()));
		
		add(createListView("predefined", predefinedListModel()));
		
		add(createPublishedCatalog("published"));
	}
	
	// ----------------------------------------------------
	
	public abstract void onSelection(WidgetSpec selected);
	
	// ----------------------------------------------------
	
	protected Component createPublishedCatalog(String componentID) {
		return new WebMarkupContainer(componentID);
	}
	
	// ----------------------------------------------------
	
	private ListView<CatalogItem> createListView(final String componentID, IModel<List<CatalogItem>> items) {
		return new ListView<CatalogItem>(componentID, items) {
			@Override
			protected void populateItem(final ListItem<CatalogItem> item) {
				item.add(createSelectLink(item.getModelObject()));
				item.add(new Label("description", item.getModelObject().getDescription()));
			}
		};
	}

	@SuppressWarnings("rawtypes")
	private AjaxLink createSelectLink(final CatalogItem item) {
		final AjaxLink link = new AjaxLink("select") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				onSelection(item.createWidget());
			}
		};
		link.add(new Label("label", item.getName()));
		return link;
	}
	
	// ----------------------------------------------------
	
	private IModel<List<CatalogItem>> basicListModel() {
		final List<CatalogItem> list = new ArrayList<CatalogItem>();
		list.add(new BasicCatalogItem(WDGT.ENTITY_LIST, 
				new ResourceModel("basic.enity-list.name"), 
				new ResourceModel("basic.enity-list.description")));
		list.add(new BasicCatalogItem(WDGT.ENTITY_DETAILS, 
				new ResourceModel("basic.enity-details.name"), 
				new ResourceModel("basic.enity-details.description")));
		return new ListModel<CatalogItem>(list);
	}
	
	private IModel<List<CatalogItem>> predefinedListModel() {
		final List<CatalogItem> list = new ArrayList<CatalogItem>();
		list.add(new PredefinedCatalogItem(ThatsMeWidget.class, 
				new ResourceModel("predefined.thats-me.name"), 
				new ResourceModel("predefined.thats-me.description")));
		list.add(new PredefinedCatalogItem(MyCompanyWidget.class, 
				new ResourceModel("predefined.my-company.name"), 
				new ResourceModel("predefined.my-company.description")));
        list.add(new PredefinedCatalogItem(ContentDisplayWidget.class,
                new ResourceModel("predefined.content-item.name"),
                new ResourceModel("predefined.content-item.description")));
		return new ListModel<CatalogItem>(list);
	}

}
