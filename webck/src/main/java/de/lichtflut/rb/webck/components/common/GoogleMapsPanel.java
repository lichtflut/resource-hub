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

import de.lichtflut.rb.webck.models.basic.DerivedModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNotNull;

/**
 * <p>
 *  Panel showing an address in Google Maps. 
 * </p>
 *
 * <p>
 * 	Created Dec 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class GoogleMapsPanel extends Panel {

	/**
	 * @param id The component ID.
	 * @param location Model representing the location as a string.
	 */
	public GoogleMapsPanel(String id, IModel<String> location) {
		super(id, location);
		
		final IModel<String> uriModel = new DerivedModel<String, String>(location) {
			@Override
			protected String derive(String original) {
				try {
					return "http://maps.google.de/maps?f=q&source=s_q&hl=de&geocode=&q="
							+ URLEncoder.encode(original, "UTF-8")
							+ "&ie=UTF8&output=embed";
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
			}
		};
		
		final WebMarkupContainer map = new WebMarkupContainer("map");
		map.add(new AttributeModifier("src", uriModel));
		map.add(visibleIf(isNotNull(location)));
		add(map);
	}
	
}
