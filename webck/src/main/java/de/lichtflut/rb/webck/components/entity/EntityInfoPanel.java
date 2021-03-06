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
package de.lichtflut.rb.webck.components.entity;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.common.EntityType;
import de.lichtflut.rb.core.common.SchemaIdentifyingType;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.common.ImageReference;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.components.links.LabeledLink;
import de.lichtflut.rb.webck.components.navigation.ExtendedActionsPanel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import de.lichtflut.rb.webck.models.entity.RBEntityImageUrlModel;
import de.lichtflut.rb.webck.models.entity.RBEntityLabelModel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;
import de.lichtflut.rb.webck.models.resources.ResourceUriModel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNClass;

import java.util.ArrayList;
import java.util.List;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.behaviors.TitleModifier.title;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNotNull;

/**
 * This Panel represents an {@link RBEntity}.
 * The following Attributes will be displayed
 * <ul>
 *  <li>Label</li>
 *  <li>Image</li>
 *  <li>Short description</li>
 * </ul>
 *
 * If none of these attributes can be attained through {@link RBEntity}s standard methods,
 * a {@link String} representation of the {@link RBEntity} will be displayed.
 *
 * Created: Aug 31, 2011
 *
 * @author Ravi Knox
 */
public class EntityInfoPanel extends Panel {
	
	@SpringBean
	private ResourceLinkProvider resourceLinkProvider;
	
	// ----------------------------------------------------

    /**
     * @param id - wicket:id
     * @param model - {@link RBEntity} which is to be displayed
     */
    public EntityInfoPanel(final String id, final IModel<RBEntity> model) {
		super(id);

        IModel<ResourceNode> nodeModel = new DerivedDetachableModel<ResourceNode, RBEntity>(model) {
            @Override
            protected ResourceNode derive(RBEntity entity) {
                return entity.getNode();
            }
        };

        add(createLabel("label", model));

		add(new ExtendedActionsPanel("extendedActionsPanel", nodeModel));
		
		add(createLinkList(createLinkModel(nodeModel)));
		
		final ResourceLabelModel typeLabelModel = new ResourceLabelModel(new EntityTypeModel(nodeModel));
		final ResourceUriModel typeURIModel = new ResourceUriModel(new EntityTypeModel(nodeModel));

		add(new Label("type", typeLabelModel).add(title(typeURIModel)));

		add(new ImageReference("image", new RBEntityImageUrlModel(nodeModel)));
		
		add(visibleIf(isNotNull(model)));
	}
	
	// ----------------------------------------------------

    protected Component createLabel(String id, IModel<RBEntity> model) {
        final IModel<String> resourceLabelModel = new RBEntityLabelModel(model) {

            @Override
            public String getDefault() {
                return getString("label.untitled");
            }
        };
        return new Label(id, resourceLabelModel);
    }

	protected ListView<VisualizationLink> createLinkList(IModel<List<VisualizationLink>> model) {
		return new ListView<VisualizationLink>("visLinks", model) {
			@Override
			protected void populateItem(ListItem<VisualizationLink> item) {
				final VisualizationLink def = item.getModelObject();
				final CrossLink crossLink = new CrossLink(LabeledLink.LINK_ID, def.getURL());
				crossLink.add(title(new ResourceModel(def.getResourceKey())));
				item.add(new LabeledLink("link", crossLink, new ResourceModel(def.getResourceKey())));
			}
		};
	}
	
	protected IModel<List<VisualizationLink>> createLinkModel(IModel<ResourceNode> model) {
		return new DerivedDetachableModel<List<VisualizationLink>, ResourceNode>(model) {
			@Override
			protected List<VisualizationLink> derive(final ResourceNode node) {
				final List<VisualizationLink> result = new ArrayList<VisualizationLink>();
				result.add(createLink(node, VisualizationMode.DETAILS));
				result.add(createLink(node, VisualizationMode.PERIPHERY));
                final SNClass type = SchemaIdentifyingType.of(node);
                if (isSubClassOf(type, RB.TREE_NODE)) {
					result.add(createLink(node, VisualizationMode.HIERARCHY));
                    result.add(createLink(node, VisualizationMode.MAP));
				}
				if (isSubClassOf(type, RB.PROCESS_ELEMENT)) {
					result.add(createLink(node, VisualizationMode.FLOW_CHART));
				}
				return result;
			}
		};
	}
	
	// ---------------------------------------------------- 
	
	private VisualizationLink createLink(ResourceID rid, VisualizationMode mode) {
		final String url = resourceLinkProvider.getUrlToResource(rid, mode, DisplayMode.VIEW);
		return new VisualizationLink(mode, url);
	}

    private boolean isSubClassOf(SNClass clazz, ResourceID superType) {
        return clazz != null && clazz.isSpecializationOf(superType);
    }
	
	// -- INNER CLASSES -----------------------------------
	
	private final class EntityTypeModel extends DerivedModel<ResourceID, ResourceNode> {
		private EntityTypeModel(IModel<ResourceNode> original) {
			super(original);
		}

		@Override
		protected ResourceID derive(ResourceNode original) {
			return EntityType.of(original);
		}
	}

}
