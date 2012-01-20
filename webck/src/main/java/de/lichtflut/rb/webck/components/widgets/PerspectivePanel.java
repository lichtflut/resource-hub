/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SimpleSelection;
import de.lichtflut.rb.core.viewspec.impl.SimpleWidgetSpec;

/**
 * <p>
 *  Panel displaying widgets based on a perspective specification.
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerspectivePanel extends Panel {

	@SpringBean
	protected ServiceProvider provider;

	// ----------------------------------------------------
	
	/**
	 * @param id
	 */
	public PerspectivePanel(String id, IModel<Perspective> spec) {
		super(id);
		
		final WidgetSpec widgetSpec = new SimpleWidgetSpec();
		widgetSpec.setSelection(new SimpleSelection(normalizeKey(RDF.TYPE) + ":" + normalizeValue(RB.PERSON)));
		final Model<WidgetSpec> widgetModel = new Model<WidgetSpec>(widgetSpec);
		
		add(new EntityListWidget("widget", widgetModel));
	}
	
	private String normalizeKey(final ResourceID id) {
		return normalizeKey(id.toURI());
	}
	
	private String normalizeValue(final ResourceID id) {
		return normalizeValue(id.toURI());
	}
	
	private String normalizeKey(final String key) {
		return key.replaceAll(":", "\\\\:");
	}
	
	private String normalizeValue(final String key) {
		return key.trim().toLowerCase().replaceAll(":", "\\\\:");
	}
}
