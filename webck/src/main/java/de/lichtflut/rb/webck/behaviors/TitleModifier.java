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
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * <p>
 *  Simpler modifier for css stuff.
 * </p>
 *
 * <p>
 * 	Created Dec 9, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class TitleModifier extends AttributeModifier {

	public static TitleModifier title(final String title) {
		return new TitleModifier(title);
	}
	
	public static TitleModifier title(final IModel<String> title) {
		return new TitleModifier(title);
	}
	
	// ----------------------------------------------------
	
	public TitleModifier(final String title) {
		super("title", new Model<String>(title));
	}
	
	public TitleModifier(final IModel<String> title) {
		super("title", title);
	}

}
