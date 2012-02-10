/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.catalog;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SNSelection;
import de.lichtflut.rb.core.viewspec.impl.SNWidgetSpec;

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
