/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
