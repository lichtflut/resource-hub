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
package de.lichtflut.rb.webck.components.widgets.config.columns;

import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.viewspec.ColumnDef;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SNColumnDef;
import de.lichtflut.rb.webck.common.OrderedNodesContainer;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.common.impl.OrderedModelContainer;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.links.ConfirmedLink;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.CastingModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.ResourceNode;

import java.util.List;

/**
 * <p>
 *  Panel for configuration of a (list) widget's columns.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ColumnsConfigPanel extends TypedPanel<WidgetSpec> {
	
	@SpringBean
	protected SemanticNetworkService service;
	
	private ColumnDefsModel columnsModel;
	
	// ----------------------------------------------------

	/**
     * Constructor.
	 * @param id The wicket ID.
	 * @param model The model providing the widget spec.
	 */
	@SuppressWarnings("rawtypes")
	public ColumnsConfigPanel(final String id, final IModel<WidgetSpec> model) {
		super(id, model);
		
		setOutputMarkupId(true);
		
		columnsModel = new ColumnDefsModel(model);
		
		final IModel<List<? extends ResourceNode>> orderModel = 
				new CastingModel<List<? extends ResourceNode>>(columnsModel);
		final OrderedNodesContainer container = new OrderedModelContainer(orderModel);
		
		add(new ListView<ColumnDef>("columnList", columnsModel) {
			@Override
			protected void populateItem(ListItem<ColumnDef> item) {
				item.add(new ColumnsConfigRow("row", item.getModel(), container));
				item.add(createRemoveLink(item.getModel()));
			}
		});
		
		add(new AjaxLink("addRow") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				columnsModel.addColumn();
				target.add(ColumnsConfigPanel.this);
			}
		});
	}
	
	// ----------------------------------------------------
	
	@Override
	public void onEvent(IEvent<?> event) {
		if (ModelChangeEvent.from(event).isAbout(ModelChangeEvent.ANY)) {
			update();
		}
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		columnsModel.detach();
	}
	
	// ----------------------------------------------------
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private AjaxLink createRemoveLink(final IModel<ColumnDef> model) {
		return new ConfirmedLink("removeLink", new ResourceModel("global.message.remove-widget-column-confirmation")) {
			@Override
			protected void applyActions(AjaxRequestTarget target) {
				removeColumn(model.getObject());
			}
		};
	}
	
	private void removeColumn(final ColumnDef columnDef) {
		final WidgetSpec widgetSpec = getModelObject();
		SNOPS.remove(widgetSpec, WDGT.DEFINES_COLUMN, columnDef);
		service.remove(columnDef);
		update();
	}
	
	private void update() {
		RBAjaxTarget.add(this);	
		columnsModel.reset();
	}
	
	// ----------------------------------------------------
	
	class ColumnDefsModel extends DerivedDetachableModel<List<? extends ColumnDef>, WidgetSpec> {

		public ColumnDefsModel(IModel<WidgetSpec> widget) {
			super(widget);
		}
		
		public void addColumn() {
			getOriginal().addColumn(new SNColumnDef());
		}

		@Override
		protected List<ColumnDef> derive(WidgetSpec original) {
            return original.getColumns();
		}

	}

}
