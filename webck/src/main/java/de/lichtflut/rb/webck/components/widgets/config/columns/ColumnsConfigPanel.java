/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config.columns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.arastreju.sge.structure.OrderBySerialNumber;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.viewspec.ColumnDef;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SNColumnDef;
import de.lichtflut.rb.webck.common.OrderedNodesContainer;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.common.impl.SerialNumberOrderedNodesContainer;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.CastingModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

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
	protected ServiceProvider provider;
	
	private ColumnDefsModel columnsModel;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 * @param model
	 */
	@SuppressWarnings("rawtypes")
	public ColumnsConfigPanel(final String id, final IModel<WidgetSpec> model) {
		super(id, model);
		
		setOutputMarkupId(true);
		
		columnsModel = new ColumnDefsModel(model);
		
		final IModel<List<? extends ResourceNode>> orderModel = 
				new CastingModel<List<? extends ResourceNode>>(columnsModel);
		final OrderedNodesContainer container = new SerialNumberOrderedNodesContainer(orderModel);
		
		add(new ListView<ColumnDef>("columnList", columnsModel) {
			@Override
			protected void populateItem(ListItem<ColumnDef> item) {
				item.add(new ColumnsConfigRow("row", item.getModel(), container));
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
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onEvent(IEvent<?> event) {
		if (ModelChangeEvent.from(event).isAbout(ModelChangeEvent.ANY)) {
			RBAjaxTarget.add(this);	
			columnsModel.reset();
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected void onDetach() {
		super.onDetach();
		columnsModel.detach();
	}
	
	@SuppressWarnings("rawtypes")
	private AjaxLink createRemoveLink() {
		return new AjaxLink("remove") {
			@Override
			public void onClick(AjaxRequestTarget target) {
			}
		};
	}
	
	// ----------------------------------------------------
	
	class ColumnDefsModel extends DerivedDetachableModel<List<? extends SNColumnDef>, WidgetSpec> {

		private int maxSerialNumber = 0;
		
		/**
		 * @param original
		 */
		public ColumnDefsModel(IModel<WidgetSpec> original) {
			super(original);
		}
		
		public void addColumn() {
			final WidgetSpec widgetSpec = getOriginal();
			final SNResource columnDef = new SNResource();
			SNOPS.associate(columnDef, Aras.HAS_SERIAL_NUMBER, new SNScalar(++maxSerialNumber));
			SNOPS.associate(widgetSpec, WDGT.DEFINES_COLUMN, columnDef);
		}

		public void removeColumn(SNColumnDef column) {
			final WidgetSpec widgetSpec = getOriginal();
			SNOPS.remove(widgetSpec, WDGT.DEFINES_COLUMN, column);
			provider.getArastejuGate().startConversation().remove(column);
		}
		
		/** 
		 * {@inheritDoc}
		 */
		@Override
		protected List<SNColumnDef> derive(WidgetSpec original) {
			final List<SNColumnDef> result = new ArrayList<SNColumnDef>();
			final Set<SemanticNode> defNodes = SNOPS.objects(original, WDGT.DEFINES_COLUMN);
			for (SemanticNode current : defNodes) {
				final SNColumnDef def = new SNColumnDef(current.asResource());
				result.add(def);
				maxSerialNumber = Math.max(maxSerialNumber, def.getPosition());
			}
			Collections.sort(result, new OrderBySerialNumber());
			return result;
		}

	}

}
