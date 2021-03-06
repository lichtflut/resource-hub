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
package de.lichtflut.rb.webck.components.infovis.common;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.entity.VisualizationLink;
import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.components.links.LabeledLink;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.naming.QualifiedName;

import java.util.ArrayList;
import java.util.List;

import static de.lichtflut.rb.webck.behaviors.TitleModifier.title;

/**
 * <p>
 *  Info panel for the selected node.
 * </p>
 *
 * <p>
 * 	Created Mar 9, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class CurrentNodeInfoPanel extends Panel {

	private final VisualizationMode visMode;
	
	private final AbstractDefaultAjaxBehavior updateBehavior;
	
	@SpringBean
	private ResourceLinkProvider resourceLinkProvider;
	
	@SpringBean
	private TypeManager typeManager;

	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param initial Model containing the initially selected node.
	 */
	public CurrentNodeInfoPanel(String id, IModel<ResourceNode> initial, VisualizationMode mode) {
		super(id, initial);
		this.visMode = mode;
		
		setOutputMarkupId(true);
		
		final ContextModel model = new ContextModel(initial);
		
		final IModel<String> resourceLabelModel = new ResourceLabelModel(model) {
			@Override
			public String getDefault() {
				return getString("label.untitled");
			}
		};
		
		add(createCrossRefLinks(model));
		
		add(createVisLinks(model));
		
		add(new Label("label", resourceLabelModel));
		
		updateBehavior = new AbstractDefaultAjaxBehavior() {
			@Override
			protected void respond(AjaxRequestTarget target) {
				final IRequestParameters params = RequestCycle.get().getRequest().getQueryParameters();
				final StringValue rid = params.getParameterValue("selected");
				if (!rid.isEmpty()) {
					model.changeID(new SimpleResourceID(rid.toString()));
				}
				target.add(CurrentNodeInfoPanel.this);
			}
		};
		add(updateBehavior);
		
	}
	
	// ----------------------------------------------------
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(OnDomReadyHeaderItem.forScript("LFRB.InfoVis.callbackURL = '" + updateBehavior.getCallbackUrl() + "';"));
	}
	
	// ----------------------------------------------------
	
	protected Component createCrossRefLinks(final ContextModel model) {
		final IModel<List<CrossReferenceLink>> visLinkModel = new DerivedDetachableModel<List<CrossReferenceLink>, ResourceNode>(model) {
			protected List<CrossReferenceLink> derive(ResourceNode node) {
				final List<CrossReferenceLink> result = new ArrayList<CrossReferenceLink>();
				for (Statement stmt : SNOPS.associations(node, Aras.HAS_PARENT_NODE)) {
					if (stmt.getObject().isResourceNode()) {
						final ResourceNode target = stmt.getObject().asResource();
						final String label = ResourceLabelBuilder.getInstance().getFieldLabel(stmt.getPredicate(), getLocale());
						result.add(new CrossReferenceLink(label, target));
					}
				}
				return result;
			}
		};
		return new ListView<CrossReferenceLink>("crossReferenceLinks", visLinkModel) {
			@Override
			protected void populateItem(ListItem<CrossReferenceLink> item) {
				final CrossReferenceLink def = item.getModelObject();
				item.add(new Label("field", def.getField()));
				
				final String url = resourceLinkProvider.getUrlToResource(def.getTarget(), visMode, DisplayMode.VIEW).toString();
				final CrossLink crossLink = new CrossLink(LabeledLink.LINK_ID, url);
				crossLink.add(title(def.getField()));
				final String label = ResourceLabelBuilder.getInstance().getLabel(def.getTarget(), getLocale());
				item.add(new LabeledLink("link", crossLink, label));
			}
		};
	}

	protected ListView<VisualizationLink> createVisLinks(final ContextModel model) {
		final IModel<List<VisualizationLink>> visLinkModel = new DerivedDetachableModel<List<VisualizationLink>, ResourceNode>(model) {
			protected List<VisualizationLink> derive(ResourceNode node) {
				final List<VisualizationLink> result = new ArrayList<VisualizationLink>();
				final SNClass type = typeManager.getTypeOfResource(node);
				result.add(createLink(node, VisualizationMode.DETAILS));
				result.add(createLink(node, VisualizationMode.PERIPHERY));
				if (type.isSpecializationOf(RB.ORGANIZATIONAL_UNIT)) {
					result.add(createLink(node, VisualizationMode.HIERARCHY));
				}
				if (type.isSpecializationOf(RB.PROCESS_ELEMENT)) {
					result.add(createLink(node, VisualizationMode.FLOW_CHART));
				}
				return result;
			}
		};
		return new ListView<VisualizationLink>("visLinks", visLinkModel) {
			@Override
			protected void populateItem(ListItem<VisualizationLink> item) {
				final VisualizationLink def = item.getModelObject();
				final CrossLink crossLink = new CrossLink(LabeledLink.LINK_ID, def.getURL());
				crossLink.add(title(new ResourceModel(def.getResourceKey())));
				item.add(new LabeledLink("link", crossLink, new ResourceModel(def.getResourceKey())));
			}
		};
	}
	
	private VisualizationLink createLink(ResourceID rid, VisualizationMode mode) {
		final String url = resourceLinkProvider.getUrlToResource(rid, mode, DisplayMode.VIEW).toString();
		return new VisualizationLink(mode, url);
	}
	
	// ----------------------------------------------------
	
	private static class ContextModel extends AbstractLoadableDetachableModel<ResourceNode> {
		
		@SpringBean
		private SemanticNetworkService service;
		
		private IModel<? extends ResourceID> id;
		
		// ----------------------------------------------------
		
		/**
		 * @param initial
		 */
		public ContextModel(IModel<? extends ResourceID> initial) {
			this.id = initial;
			Injector.get().inject(this);
		}
		
		// ----------------------------------------------------
		
		@Override
		public ResourceNode load() {
			if (id.getObject() != null) {
				final QualifiedName qn = id.getObject().getQualifiedName();
				return service.find(qn);
			} else {
				return null;
			}
		}
		
		// ----------------------------------------------------
		
		public void changeID(ResourceID newID) {
			this.id = new Model<ResourceID>(newID);
		}
		
	}
	
}
