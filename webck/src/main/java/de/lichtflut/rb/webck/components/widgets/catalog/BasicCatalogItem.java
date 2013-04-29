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

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SNSelection;
import de.lichtflut.rb.core.viewspec.impl.SNWidgetSpec;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Basic catalog item.
 * </p>
 *
 * <p>
 * 	Created Feb 9, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class BasicCatalogItem extends CatalogItem {

	private final ResourceID type;

	// ----------------------------------------------------
	
	/**
	 * @param name
	 * @param description
	 */
	public BasicCatalogItem(ResourceID type, IModel<String> name, IModel<String> description) {
		super(name, description);
		this.type = type;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public WidgetSpec createWidget() {
		final SNWidgetSpec widget = new SNWidgetSpec();
		widget.setSelection(new SNSelection());
		widget.addAssociation(RDF.TYPE, type);
		return widget;
	}

}
