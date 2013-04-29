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

import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.dialogs.SelectWidgetDialog;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.nodes.SemanticNode;

import java.util.List;

/**
 * <p>
 *  Panel displaying widgets based on a perspective specification.
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ViewPortPanel extends TypedPanel<ViewPort> implements WidgetController {

	@SpringBean
	private ViewSpecificationService viewSpecificationService;
	
	// ----------------------------------------------------
	
	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public ViewPortPanel(String id, IModel<ViewPort> portModel, final ConditionalModel<Boolean> isConfigMode) {
		super(id, portModel);
		
		// Container needed for dynamic CSS class
		final WebMarkupContainer container = new WebMarkupContainer("container"); 
		container.setOutputMarkupId(true);
		
		final IModel<String> containerClass = new DerivedDetachableModel<String, Boolean>(isConfigMode) {
			@Override
			protected String derive(Boolean original) {
				if (Boolean.TRUE.equals(original)) {
					return "configuration-mode";
				} else {
					return "display-mode";
				}
			}
		};
		container.add(CssModifier.appendClass(containerClass));
		
		container.add(new AjaxLink("addWidget") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				final DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new SelectWidgetDialog(hoster.getDialogID()) {
					@Override
					protected void onSelection(WidgetSpec spec) {
						addWidget(spec);
					}
				});
			}
		}.add(ConditionalBehavior.visibleIf(isConfigMode)));
		
		final WidgetListModel listModel = new WidgetListModel(portModel);

		container.add(new ListView<WidgetSpec>("widgetList", listModel) {
			@Override
			protected void populateItem(ListItem<WidgetSpec> item) {
				item.add(createWidgetPanel(item.getModel(), isConfigMode));
			}
		});
		
		add(container);
	}
	
	// -- WidgetController --------------------------------
	
	@SuppressWarnings("rawtypes")
	@Override
	public void remove(WidgetSpec widget) {
		viewSpecificationService.removeWidget(getViewPort(), widget);
		send(this, Broadcast.BUBBLE, new ModelChangeEvent(ModelChangeEvent.VIEW_SPEC));
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void moveDown(WidgetSpec widget) {
        viewSpecificationService.movePositionDown(getViewPort(), widget);
		send(this, Broadcast.BUBBLE, new ModelChangeEvent(ModelChangeEvent.VIEW_SPEC));
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void moveUp(WidgetSpec widget) {
        viewSpecificationService.movePositionUp(getViewPort(), widget);
		send(this, Broadcast.BUBBLE, new ModelChangeEvent(ModelChangeEvent.VIEW_SPEC));
	}
	
	// ----------------------------------------------------

	@SuppressWarnings("rawtypes")
	public void addWidget(WidgetSpec widget) {
		final ViewPort port = getViewPort();
		port.addWidget(widget);
		viewSpecificationService.store(port);
		send(this, Broadcast.BUBBLE, new ModelChangeEvent(ModelChangeEvent.VIEW_SPEC));
	}

    protected ViewPort getViewPort() {
        return getModelObject();
    }

	// ----------------------------------------------------

	private Component createWidgetPanel(IModel<WidgetSpec> model, ConditionalModel<Boolean> isConfigMode) {
		final WidgetSpec spec = model.getObject();
		final SemanticNode type = SNOPS.fetchObject(spec, RDF.TYPE);
		if (WDGT.ENTITY_LIST.equals(type)) {
			return new EntityListWidget("widget", model, isConfigMode);
		} else if (WDGT.ENTITY_DETAILS.equals(type)) {
			return new EntityDetailsWidget("widget", model, isConfigMode);
		} else if (WDGT.PREDEFINED.equals(type)) {
			return PredefinedWidget.create(spec, "widget", isConfigMode);
		} else {
			throw new IllegalStateException();
		}
	}
	
	// ----------------------------------------------------

	class WidgetListModel extends DerivedDetachableModel<List<WidgetSpec>, ViewPort> {

		/**
		 * @param original The original model.
		 */
		public WidgetListModel(IModel<ViewPort> original) {
			super(original);
		}

		@Override
		protected List<WidgetSpec> derive(ViewPort original) {
			return original.getWidgets();
		}
		
	}

}
