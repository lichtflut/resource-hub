/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config.selection;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.impl.SNSelectionParameter;
import de.lichtflut.rb.webck.components.common.TypedPanel;

/**
 * <p>
 *  Panel for configuration of a {@link Selection}.
 * </p>
 *
 * <p>
 * 	Created Jan 27, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SelectionConfigPanel extends TypedPanel<Selection> {
	
	/**
	 * @param id
	 * @param model
	 */
	public SelectionConfigPanel(String id, IModel<Selection> model) {
		super(id, model);
		
		add(new ListView<QueryParamUIModel>("paramList", new ParamModel(model)) {
			@Override
			protected void populateItem(ListItem<QueryParamUIModel> item) {
				item.add(new QueryParamRow("param", item.getModel()));
			}
		});
	}

	// ----------------------------------------------------
	
	class ParamModel extends AbstractReadOnlyModel<List<QueryParamUIModel>> {
		
		private final IModel<Selection> selectionModel;
		private List<QueryParamUIModel> list;
		
		// ----------------------------------------------------

		/**
		 * Constructor.
		 */
		public ParamModel(IModel<Selection> model) {
			this.selectionModel = model;
		}
		
		/** 
		 * {@inheritDoc}
		 */
		@Override
		public List<QueryParamUIModel> getObject() {
			if (list == null) {
				list = new ArrayList<QueryParamUIModel>();
				final Selection selection = selectionModel.getObject();
				for (SemanticNode parameter : SNOPS.objects(selection, WDGT.HAS_PARAMETER)) {
					list.add(new QueryParamUIModel(new SNSelectionParameter(parameter.asResource())));
				}
			}
			return list;
		}

	}
	
}
