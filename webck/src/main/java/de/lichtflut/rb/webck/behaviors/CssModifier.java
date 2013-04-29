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
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
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
public class CssModifier {

	public static Behavior setClass(final String cssClass) {
		return new AttributeModifier("class", cssClass);
	}
	
	public static Behavior setClass(final IModel<String> cssClass) {
		return new AttributeModifier("class", cssClass);
	}
	
	public static Behavior appendClass(final IModel<String> cssClass) {
		return new AttributeAppender("class", cssClass, " ");
	}

    public static Behavior appendClass(final String cssClass) {
        return new AttributeAppender("class", Model.of(cssClass), " ");
    }

    public static Behavior appendStyle(String style) {
        return new AttributeAppender("style", Model.of(style), ";");
    }
	
}
