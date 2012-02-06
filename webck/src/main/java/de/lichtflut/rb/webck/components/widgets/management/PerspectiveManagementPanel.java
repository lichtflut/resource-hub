/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.management;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.webck.models.viewspecs.PerspectiveListModel;

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
public class PerspectiveManagementPanel extends Panel {
	
	@SpringBean
	private ServiceProvider provider;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public PerspectiveManagementPanel(String id) {
		super(id);
		
		setOutputMarkupId(true);
		
		final ListView<Perspective> view = new ListView<Perspective>("list", new PerspectiveListModel()) {
			@Override
			protected void populateItem(final ListItem<Perspective> item) {
				final Perspective perspective = item.getModelObject();
				item.add(new Label("name", perspective.getName()));
				item.add(new Label("title", perspective.getTitle()));
				item.add(new AjaxLink("edit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
					}
				});
				item.add(new AjaxLink("delete") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						delete(item.getModelObject());
					}
				});
			}
		};
		add(view);
		
	}
	
	// ----------------------------------------------------

	private void delete(Perspective perspective) {
		provider.getViewSpecificationService().remove(perspective);
	}

}
