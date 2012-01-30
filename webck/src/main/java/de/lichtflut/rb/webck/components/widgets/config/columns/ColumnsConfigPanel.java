/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config.columns;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNScalar;

import de.lichtflut.rb.core.viewspec.ColumnDef;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SNColumnDef;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.fields.PropertyPickerField;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

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
	
	/**
	 * @param id
	 * @param model
	 */
	@SuppressWarnings("rawtypes")
	public ColumnsConfigPanel(final String id, final IModel<WidgetSpec> model) {
		super(id, model);
		
		setOutputMarkupId(true);
		
		final ColumnDefsModel columnsModel = new ColumnDefsModel(model);
		add(new ListView<ColumnDef>("columnList", columnsModel) {
			@Override
			protected void populateItem(ListItem<ColumnDef> item) {
				final PropertyPickerField picker = new PropertyPickerField("property", 
						new PropertyModel<ResourceID>(item.getModel(), "property"));
				picker.setRequired(true);
				item.add(picker);
			}
		});
		
		add(new AjaxLink("addRow") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				addRow(target, columnsModel.getObject().size());
			}
		});
	}
	
	// ----------------------------------------------------

	private void addRow(AjaxRequestTarget target, int serialNumber) {
		SNResource columnDef = new SNResource();
		SNOPS.associate(columnDef, Aras.HAS_SERIAL_NUMBER, new SNScalar(serialNumber));
		SNOPS.associate(getModelObject(), WDGT.DEFINES_COLUMN, columnDef);
		target.add(this);
	}
	
	// ----------------------------------------------------
	
	class ColumnDefsModel extends DerivedModel<List<? extends ColumnDef>, WidgetSpec> {

		/**
		 * @param original
		 */
		public ColumnDefsModel(IModel<WidgetSpec> original) {
			super(original);
		}

		/** 
		 * {@inheritDoc}
		 */
		@Override
		protected List<? extends ColumnDef> derive(WidgetSpec original) {
			final List<ColumnDef> result = new ArrayList<ColumnDef>();
			final Set<SemanticNode> defNodes = SNOPS.objects(original, WDGT.DEFINES_COLUMN);
			for (SemanticNode current : defNodes) {
				result.add(new SNColumnDef(current.asResource()));
			}
			return result;
		}

	}

}
