/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.behaviors.TitleModifier;
import de.lichtflut.rb.webck.components.common.ImageReference;
import de.lichtflut.rb.webck.components.editor.IBrowsingHandler;
import de.lichtflut.rb.webck.components.editor.VisualizationMode;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import de.lichtflut.rb.webck.models.entity.RBEntityImageUrlModel;
import de.lichtflut.rb.webck.models.entity.RBEntityLabelModel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;
import de.lichtflut.rb.webck.models.resources.ResourceUriModel;

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
public class ResourceInfoPanel extends Panel {

	/**
	 * @param id - wicket:id
	 * @param model - {@link RBEntity} which is to be displayed
	 */
	public ResourceInfoPanel(final String id, final IModel<RBEntity> model) {
		super(id);
		
		final IModel<String> resourceLabelModel = new RBEntityLabelModel(model) {
			@Override
			public String getDefault() {
				return getString("label.untitled");
			}
		};

		add(new CrossLink("showPeriphery", new DerivedModel<String, RBEntity>(model) {
			@Override
			protected String derive(RBEntity original) {
				return getUrlTo(model.getObject().getID(), VisualizationMode.PERIPHERY);
			}
		}).add(TitleModifier.title(new ResourceModel("global.link.periphery-visualization"))));
		
		add(new CrossLink("showHierarchy", new DerivedModel<String, RBEntity>(model) {
			@Override
			protected String derive(RBEntity original) {
				return getUrlTo(model.getObject().getID(), VisualizationMode.HIERARCHY);
			}
		}).add(TitleModifier.title(new ResourceModel("global.link.hierarchical-visualization"))));
		
		final ResourceLabelModel typeLabelModel = new ResourceLabelModel(new EntityTypeModel(model));
		final ResourceUriModel typeURIModel = new ResourceUriModel(new EntityTypeModel(model));
		
		add(new Label("label", resourceLabelModel));
		add(new Label("type", typeLabelModel).add(TitleModifier.title(typeURIModel)));
		add(new ImageReference("image", new RBEntityImageUrlModel(model)));
	}
	
	// ----------------------------------------------------
	
	protected String getUrlTo(final ResourceID rid, VisualizationMode mode) {
		final IBrowsingHandler handler = findParent(IBrowsingHandler.class);
		if (handler == null) {
			throw new IllegalStateException(getClass().getSimpleName() 
					+ " must be placed placed in a component/page that implements " 
					+ IBrowsingHandler.class);
		}
		return handler.getUrlToResource(rid, mode).toString();
	}
	
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
