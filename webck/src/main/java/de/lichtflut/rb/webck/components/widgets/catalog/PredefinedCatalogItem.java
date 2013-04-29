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
package de.lichtflut.rb.webck.components.widgets.catalog;

import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SNWidgetSpec;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.nodes.views.SNText;

/**
 * <p>
 *  Catalog item for predefined widgets.
 * </p>
 *
 * <p>
 * 	Created Feb 9, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class PredefinedCatalogItem extends CatalogItem {

	private final Class<?> widgetClass;

    // ----------------------------------------------------
	
	/**
     * Constructor.
	 * @param widgetClass The class of the widget.
     * @param name The widget's name.
     * @param description The widget's description.
	 */
	public PredefinedCatalogItem(final Class<?> widgetClass, final IModel<String> name, final IModel<String> description) {
		super(name, description);
		this.widgetClass = widgetClass;
	}

	// ----------------------------------------------------

	@Override
	public WidgetSpec createWidget() {
		final WidgetSpec widgetSpec = new SNWidgetSpec();
		widgetSpec.addAssociation(RDF.TYPE, WDGT.PREDEFINED);
		widgetSpec.addAssociation(WDGT.IS_IMPLEMENTED_BY_CLASS, new SNText(widgetClass.getCanonicalName()));
		return widgetSpec;
	}

}
