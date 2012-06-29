/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.TypeManager;
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
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
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
 * <li>Label</li>
 * <li>Image</li>
 * <li>Short description</li>
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
	private TypeManager typeManager;
	
	@SpringBean
	private ResourceLinkProvider resourceLinkProvider;
	
	// ----------------------------------------------------

	/**
	 * @param id - wicket:id
	 * @param model - {@link RBEntity} which is to be displayed
	 */
	public EntityInfoPanel(final String id, final IModel<RBEntity> model) {
		super(id);
		
		final IModel<String> resourceLabelModel = new RBEntityLabelModel(model) {
			@Override
			public String getDefault() {
				return getString("label.untitled");
			}
		};
		
		add(new ExtendedActionsPanel("extendedActionsPanel", model));
		
		add(createLinkList(createLinkModel(model)));
		
		final ResourceLabelModel typeLabelModel = new ResourceLabelModel(new EntityTypeModel(model));
		final ResourceUriModel typeURIModel = new ResourceUriModel(new EntityTypeModel(model));
		
		add(new Label("label", resourceLabelModel));
		add(new Label("type", typeLabelModel).add(title(typeURIModel)));
		add(new ImageReference("image", new RBEntityImageUrlModel(model)));
		
		add(visibleIf(isNotNull(model)));
	}
	
	// ----------------------------------------------------

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
	
	protected IModel<List<VisualizationLink>> createLinkModel(IModel<RBEntity> model) {
		return new DerivedDetachableModel<List<VisualizationLink>, RBEntity>(model) {
			@Override
			protected List<VisualizationLink> derive(final RBEntity entity) {
				final SNClass type = typeManager.findType(entity.getType());
				final List<VisualizationLink> result = new ArrayList<VisualizationLink>();
				result.add(createLink(entity.getID(), VisualizationMode.DETAILS));
				result.add(createLink(entity.getID(), VisualizationMode.PERIPHERY));
				if (type.isSpecializationOf(RB.ORGANIZATIONAL_UNIT)) {
					result.add(createLink(entity.getID(), VisualizationMode.HIERARCHY));
				}
				if (type.isSpecializationOf(RB.PROCESS_ELEMENT)) {
					result.add(createLink(entity.getID(), VisualizationMode.FLOW_CHART));
				}
				return result;
			}
		};
	}
	
	// ---------------------------------------------------- 
	
	private VisualizationLink createLink(ResourceID rid, VisualizationMode mode) {
		final String url = resourceLinkProvider.getUrlToResource(rid, mode, DisplayMode.VIEW).toString();
		return new VisualizationLink(mode, url);
	}
	
	// -- INNER CLASSES -----------------------------------
	
	private final class EntityTypeModel extends DerivedModel<ResourceID, RBEntity> {
		private EntityTypeModel(IModel<RBEntity> original) {
			super(original);
		}

		@Override
		protected ResourceID derive(RBEntity original) {
			return original.getType();
		}
	}

}
