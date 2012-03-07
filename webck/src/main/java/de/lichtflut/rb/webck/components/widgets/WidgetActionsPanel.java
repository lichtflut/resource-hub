/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetAction;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.editor.VisualizationMode;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.components.links.LabeledLink;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

/**
 * <p>
 *  Panel containing the action links of a widget.
 * </p>
 *
 * <p>
 * 	Created Feb 1, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class WidgetActionsPanel extends TypedPanel<WidgetSpec> {
	
	@SpringBean
	private ResourceLinkProvider resourceLinkProvider;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 * @param model
	 */
	public WidgetActionsPanel(String id, IModel<WidgetSpec> model) {
		super(id, model);
		
		IModel<List<WidgetAction>> actionsModel = new DerivedDetachableModel<List<WidgetAction>, WidgetSpec>(model) {
			@Override
			protected List<WidgetAction> derive(WidgetSpec spec) {
				return spec.getActions();
			}
		};
		
		add(new ListView<WidgetAction>("list", actionsModel) {
			@Override
			protected void populateItem(ListItem<WidgetAction> item) {
				final WidgetAction action = item.getModelObject();
				item.add(createNewInstanceLink("action", action));
			}
		});
	}
	
	// ----------------------------------------------------
	
	public Component createNewInstanceLink(String componentID, WidgetAction action) {
		SemanticNode type = SNOPS.fetchObject(action, WDGT.CREATE_INSTANCE_OF);
		if (type == null) {
			return new Label(componentID, "undefined");
		}
		String url = resourceLinkProvider.getUrlToResource(type.asResource(), VisualizationMode.DETAILS, DisplayMode.CREATE);
		final CrossLink link = new CrossLink(LabeledLink.LINK_ID, url);
		return new LabeledLink(componentID, link, new ResourceModel("widget.actions.create-instance"));
	}

}
