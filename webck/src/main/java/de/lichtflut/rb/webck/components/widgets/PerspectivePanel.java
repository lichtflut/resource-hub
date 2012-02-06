/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isTrue;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.impl.ViewSpecTraverser;
import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.dialogs.InformationExportDialog;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

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
public class PerspectivePanel extends TypedPanel<Perspective> {

	private IModel<Boolean> isConfigMode = new Model<Boolean>(false);
	private ConditionalModel<Boolean> isConfigConditional = isTrue(isConfigMode);

	// ----------------------------------------------------
	
	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public PerspectivePanel(String id, IModel<Perspective> spec) {
		super(id, spec);

		setOutputMarkupId(true);
		
		add(new AjaxLink("configureLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				isConfigMode.setObject(true);
				target.add(PerspectivePanel.this);
			}
		}.add(visibleIf(not(isConfigConditional))));
		
		add(new AjaxLink("configDoneLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				isConfigMode.setObject(false);
				target.add(PerspectivePanel.this);
			}
		}.add(visibleIf(isConfigConditional)));
		
		add(createExportLink());

		final WebMarkupContainer container = new WebMarkupContainer("viewPortsContainer");
		container.add(CssModifier.appendClass(new LayoutModel(spec)));
		
		container.add(new ListView<ViewPort>("portList", new PortListModel(spec)) {
			@Override
			protected void populateItem(ListItem<ViewPort> item) {
				item.add(new ViewPortPanel("port", item.getModel(), isConfigConditional));
			}
		});
		add(container);
		
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onEvent(IEvent<?> event) {
		if (ModelChangeEvent.from(event).isAbout(ModelChangeEvent.VIEW_SPEC)) {
			getModel().detach();
			RBAjaxTarget.add(this);
		}
	}
	
	@SuppressWarnings("rawtypes")
	protected AjaxLink createExportLink() {
		final IModel<SemanticGraph> exportModel = new DerivedModel<SemanticGraph, Perspective>(getModelObject()) {
			@Override
			protected SemanticGraph derive(Perspective spec) {
				return new ViewSpecTraverser().toGraph(spec);
			}
		};
		AjaxLink link = new AjaxLink("exportLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new InformationExportDialog(hoster.getDialogID(), exportModel));
			}
		};
		link.add(visibleIf(isConfigConditional));
		return link;
	}
	
	// ---------------------------------------------------- 
	
	private final class LayoutModel extends DerivedDetachableModel<String, Perspective> {

		private LayoutModel(IModel<Perspective> original) {
			super(original);
		}

		@Override
		protected String derive(Perspective perspective) {
			SemanticNode layout = SNOPS.fetchObject(perspective, WDGT.HAS_LAYOUT);
			if (layout != null && layout.isValueNode()) {
				return layout.asValue().getStringValue();
			} else {
				return "";
			}
		}
	}

	class PortListModel extends DerivedDetachableModel<List<ViewPort>, Perspective> {

		/**
		 * @param original The original model.
		 */
		public PortListModel(IModel<Perspective> original) {
			super(original);
		}

		/** 
		 * {@inheritDoc}
		 */
		@Override
		protected List<ViewPort> derive(Perspective original) {
			return original.getViewPorts();
		}
		
	}
	
}
