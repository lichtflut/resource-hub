/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.components.widgets;

import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetAction;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.components.links.LabeledLink;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.SemanticNode;

import java.util.List;

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
