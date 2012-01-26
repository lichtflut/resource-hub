/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNScalar;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SNSelection;
import de.lichtflut.rb.core.viewspec.impl.SNWidgetSpec;

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
		
		final WidgetSpec widgetSpec = new SNWidgetSpec();
		widgetSpec.setSelection(createSelection());
		
		addColumn(widgetSpec, RB.HAS_FIRST_NAME);
		addColumn(widgetSpec, RB.HAS_LAST_NAME);
		addColumn(widgetSpec, RB.HAS_EMAIL);
		addColumn(widgetSpec, RB.IS_EMPLOYED_BY);
		
		final Model<WidgetSpec> widgetModel = new Model<WidgetSpec>(widgetSpec);
		
		add(new EntityListWidget("widget", widgetModel));
	}
	
	// ----------------------------------------------------
	
	private SNSelection createSelection() {
		final SNSelection selection = new SNSelection();
		
		final ResourceNode typeParam = new SNResource();
		typeParam.addAssociation(WDGT.CONCERNS_FIELD, RDF.TYPE);
		typeParam.addAssociation(WDGT.HAS_TERM, RB.PERSON);
		
		selection.addAssociation(WDGT.HAS_PARAMETER, typeParam);
		
		return selection;
	}
	
	private void addColumn(WidgetSpec widgetSpec, ResourceID predicate) {
		final int idx = widgetSpec.getAssociations(WDGT.DEFINES_COLUMN).size() +1;
		widgetSpec.addAssociation(WDGT.DEFINES_COLUMN, createColumnDef(predicate, idx));
	}
	
	private ResourceNode createColumnDef(ResourceID predicate, int serialNo) {
		final ResourceNode def = new SNResource();
		def.addAssociation(WDGT.CORRESPONDS_TO_PROPERTY, predicate);
		def.addAssociation(Aras.HAS_SERIAL_NUMBER, new SNScalar(serialNo));
		return def;
	}
	
}
