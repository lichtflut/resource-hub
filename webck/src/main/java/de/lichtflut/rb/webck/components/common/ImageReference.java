/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.common;

import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.models.ConditionalModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  Component for an external image.d
 * </p>
 *
 * <p>
 * 	Created Dec 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ImageReference extends WebComponent {

	/**
	 * @param id The wicket ID.
	 * @param srcModel Model for the images "src" attribute.
	 */
	public ImageReference(String id, IModel<?> srcModel) {
		super(id, srcModel);
		
		 add(new AttributeModifier("src", srcModel));
		 add(ConditionalBehavior.visibleIf(ConditionalModel.isNotNull(srcModel)));
	}
	
		
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		checkComponentTag(tag, "img");
	}

}
