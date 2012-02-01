/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.SelectWidgetDialog;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

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
public class ViewPortPanel extends Panel {

	@SpringBean
	protected ServiceProvider provider;
	
	// ----------------------------------------------------
	
	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public ViewPortPanel(String id, IModel<ViewPort> spec, final ConditionalModel<Boolean> isConfigMode) {
		super(id);
		
		final WebMarkupContainer container = new WebMarkupContainer("container"); 
		
		container.add(new AjaxLink("addWidget") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				final DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new SelectWidgetDialog(hoster.getDialogID()) {
					@Override
					protected void onSelection(WidgetSpec spec) {
					}
				});
			}
		}.add(ConditionalBehavior.visibleIf(isConfigMode)));
		
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
		
		container.add(new ListView<WidgetSpec>("widgetList", new WidgetListModel(spec)) {
			@Override
			protected void populateItem(ListItem<WidgetSpec> item) {
				item.add(createWidgetPanel(item.getModel()));
			}
		});
		
		add(container);
	}

	// ----------------------------------------------------

	private Component createWidgetPanel(IModel<WidgetSpec> model) {
		final WidgetSpec spec = model.getObject();
		final SemanticNode type = SNOPS.fetchObject(spec, RDF.TYPE);
		if (WDGT.ENTITY_LIST.equals(type)) {
			return new EntityListWidget("widget", model);
		} else if (WDGT.ENTITY_DETAILS.equals(type)) {
			return new EntityDetailsWidget("widget", model);
		} else if (WDGT.PREDEFINED.equals(type)) {
			return PredefinedWidget.create(spec, "widget");
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

		/** 
		 * {@inheritDoc}
		 */
		@Override
		protected List<WidgetSpec> derive(ViewPort original) {
			return original.getWidgets();
		}
		
	}
	
}
