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
package de.lichtflut.rb.webck.components.widgets;

import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.models.ConditionalModel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.SemanticNode;

import java.lang.reflect.Constructor;

/**
 * <p>
 *  Widget that is predefined and thus not configurable.
 * </p>
 *
 * <p>
 * 	Created Jan 31, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class PredefinedWidget extends AbstractWidget {

    /**
     * Constructor.
     * @param id The component ID.
     * @param spec The specification.
     */
    public PredefinedWidget(String id, WidgetSpec spec, ConditionalModel<Boolean> perspectiveInConfigMode) {
        super(id, Model.of(spec), perspectiveInConfigMode);
    }

	/**
     * Constructor.
	 * @param id The component ID.
	 * @param spec The specification.
	 */
	public PredefinedWidget(String id, IModel<WidgetSpec> spec, ConditionalModel<Boolean> perspectiveInConfigMode) {
		super(id, spec, perspectiveInConfigMode);
	}
	
	// ----------------------------------------------------
	
	public static Component create(WidgetSpec spec, String componentID, ConditionalModel<Boolean> configMode) {
		SemanticNode implClass = SNOPS.fetchObject(spec, WDGT.IS_IMPLEMENTED_BY_CLASS);
		if (implClass != null && implClass.isValueNode()) {
			String fqcn = implClass.asValue().getStringValue();
			try {
				Class<?> clazz = Class.forName(fqcn, true, Thread.currentThread().getContextClassLoader());
				Constructor<?> constructor = clazz.getConstructor(new Class[] { String.class, WidgetSpec.class, ConditionalModel.class });
				return (Component) constructor.newInstance(componentID, spec, configMode);
			} catch (Exception e) {
				e.printStackTrace();
				return new Label(componentID, e.getClass() + ": " + e.getMessage());
			}
		} else {
			return new Label(componentID, "Widget class not specified.");
		}
	}

}
