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
