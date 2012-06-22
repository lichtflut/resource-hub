/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import java.util.List;

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
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.common.impl.SerialNumberOrderedNodesContainer;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.dialogs.SelectWidgetDialog;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.CastingModel;
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
public class ViewPortPanel extends TypedPanel<ViewPort> implements WidgetController {

	@SpringBean
	private ViewSpecificationService viewSpecificationService;
	
	private SerialNumberOrderedNodesContainer orderedNodesContainer;
	
	// ----------------------------------------------------
	
	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public ViewPortPanel(String id, IModel<ViewPort> spec, final ConditionalModel<Boolean> isConfigMode) {
		super(id, spec);
		
		
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
		
		final WidgetListModel listModel = new WidgetListModel(spec);
		final IModel<List<? extends ResourceNode>> orderModel = 
				new CastingModel<List<? extends ResourceNode>>(listModel);
		
		orderedNodesContainer = new SerialNumberOrderedNodesContainer(orderModel);
		
		container.add(new ListView<WidgetSpec>("widgetList", listModel) {
			@Override
			protected void populateItem(ListItem<WidgetSpec> item) {
				item.add(createWidgetPanel(item.getModel(), isConfigMode));
			}
		});
		
		add(container);
	}
	
	// -- WidgetController --------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void remove(WidgetSpec widget) {
		final ViewPort port = getModelObject();
		port.removeWidget(widget);
		viewSpecificationService.store(port);
		send(this, Broadcast.BUBBLE, new ModelChangeEvent(ModelChangeEvent.VIEW_SPEC));
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void moveDown(WidgetSpec widget) {
		orderedNodesContainer.moveDown(widget, 1);
		send(this, Broadcast.BUBBLE, new ModelChangeEvent(ModelChangeEvent.VIEW_SPEC));
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void moveUp(WidgetSpec widget) {
		orderedNodesContainer.moveUp(widget, 1);
		send(this, Broadcast.BUBBLE, new ModelChangeEvent(ModelChangeEvent.VIEW_SPEC));
	}
	
	// ----------------------------------------------------

	@SuppressWarnings("rawtypes")
	public void addWidget(WidgetSpec widget) {
		final ViewPort port = getModelObject();
		port.addWidget(widget);
		viewSpecificationService.store(port);
		send(this, Broadcast.BUBBLE, new ModelChangeEvent(ModelChangeEvent.VIEW_SPEC));
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

		/** 
		 * {@inheritDoc}
		 */
		@Override
		protected List<WidgetSpec> derive(ViewPort original) {
			return original.getWidgets();
		}
		
	}

}
