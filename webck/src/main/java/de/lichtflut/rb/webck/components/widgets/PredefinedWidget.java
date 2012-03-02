/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import java.lang.reflect.Constructor;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.models.ConditionalModel;

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
	 * @param id
	 * @param title
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
				return new Label(componentID, e.getMessage());
			}
		} else {
			return new Label(componentID, "Widget class not specified.");
		}
	}

}