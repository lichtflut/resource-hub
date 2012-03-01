/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.infra.exceptions.NotYetSupportedException;
import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.impl.SNSelectionParameter;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

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
	
	private static final Logger logger = LoggerFactory.getLogger(SelectionConfigPanel.class);
	
	// ----------------------------------------------------
	
	/**
	 * @param id
	 * @param model
	 */
	public SelectionConfigPanel(String id, IModel<Selection> model) {
		super(id, model);
		
		add(new ListView<QueryParamUIModel>("paramList", new ParamUiListModel(model)) {
			@Override
			protected void populateItem(ListItem<QueryParamUIModel> item) {
				item.add(new QueryParamRow("param", item.getModel()));
			}
		});
	}

	// ----------------------------------------------------
	
	class ParamUiListModel extends DerivedDetachableModel<List<QueryParamUIModel>, Selection> {
		
		/**
		 * Constructor.
		 */
		public ParamUiListModel(IModel<Selection> model) {
			super(model);
		}
		
		/** 
		 * {@inheritDoc}
		 */
		@Override
		protected List<QueryParamUIModel> derive(Selection selection) {
			final Set<SemanticNode> params = SNOPS.objects(selection, WDGT.HAS_PARAMETER);
			if (params.size() > 1) {
				throw new NotYetSupportedException("Selections with more tha one parameter not yet supported.");
			} 
			final List<QueryParamUIModel> result = new ArrayList<QueryParamUIModel>(1);
			result.add(new QueryParamUIModel(new ParamModel(getOriginalModel())));
			return result;
		}
		
	}
	
	class ParamModel extends DerivedModel<SNSelectionParameter, Selection> {

		/**
		 * @param original
		 */
		public ParamModel(IModel<Selection> original) {
			super(original);
		}

		/** 
		 * {@inheritDoc}
		 */
		@Override
		protected SNSelectionParameter derive(Selection selection) {
			final Set<SemanticNode> params = SNOPS.objects(selection, WDGT.HAS_PARAMETER);
			if (params.size() > 1) {
				throw new NotYetSupportedException("Selections with more tha one parameter not yet supported.");
			} else if (params.isEmpty()) {
				logger.info("Selection had no parameters yet. Will create one.");
				SNSelectionParameter param = new SNSelectionParameter();
				selection.addAssociation(WDGT.HAS_PARAMETER, param);
				param.setField(RDF.TYPE);
				return param;
			} else {
				return new SNSelectionParameter(params.iterator().next().asResource());
			}
		}
		
	}
	
}
