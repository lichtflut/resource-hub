/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.behaviors.TitleModifier;
import de.lichtflut.rb.webck.components.common.ImageReference;
import de.lichtflut.rb.webck.models.EntityImageUrlModel;
import de.lichtflut.rb.webck.models.ResourceLabelModel;
import de.lichtflut.rb.webck.models.ResourceUriModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

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
	 * @param entity - {@link RBEntity} which is to be displayed
	 */
	public ResourceInfoPanel(final String id, final IModel<RBEntity> model) {
		super(id);
		
		final IModel<String> resourceLabelModel = new DerivedModel<String, RBEntity>(model) {
			@Override
			protected String derive(RBEntity original) {
				final String label = original.getLabel();
				if (StringUtils.isBlank(label)) {
					return getString("label.untitled");
				} else {
					return label;
				}
			}
			/** 
			* {@inheritDoc}
			*/
			@Override
			public String getDefault() {
				return "";
			}
		};
		
		final ResourceLabelModel typeLabelModel = new ResourceLabelModel(new DerivedModel<ResourceID, RBEntity>(model) {
			@Override
			protected ResourceID derive(RBEntity original) {
				return original.getType();
			}
		});
		
		final ResourceUriModel typeURIModel = new ResourceUriModel(new DerivedModel<ResourceID, RBEntity>(model) {
			@Override
			protected ResourceID derive(RBEntity original) {
				return original.getType();
			}
		});
		
		add(new Label("label", resourceLabelModel));
		add(new Label("type", typeLabelModel).add(TitleModifier.title(typeURIModel)));
		add(new ImageReference("image", new EntityImageUrlModel(model)));
	}

}
