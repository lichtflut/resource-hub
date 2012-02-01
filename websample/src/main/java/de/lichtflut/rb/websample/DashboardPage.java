/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.websample;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.arastreju.sge.model.nodes.views.SNText;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SNPerspective;
import de.lichtflut.rb.core.viewspec.impl.SNSelection;
import de.lichtflut.rb.core.viewspec.impl.SNViewPort;
import de.lichtflut.rb.core.viewspec.impl.SNWidgetSpec;
import de.lichtflut.rb.webck.components.widgets.PerspectivePanel;
import de.lichtflut.rb.webck.components.widgets.builtin.ThatsMeWidget;
import de.lichtflut.rb.websample.base.RBBasePage;

/**
 * <p>
 * Sample welcome/dashboard page for Web-CK.
 * </p>
 *
 * <p>
 * Created Jan 5, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public class DashboardPage extends RBBasePage {

	public DashboardPage(final PageParameters parameters) {
		super("Dashboard", parameters);
		
		add(new PerspectivePanel("perspective", createDummyPerspective()));
	}
	
	// ----------------------------------------------------
	
	protected IModel<Perspective> createDummyPerspective() {
		final Perspective perspective = new SNPerspective();
		perspective.addAssociation(WDGT.HAS_VIEW_PORT, createDummyViewPort1());
		perspective.addAssociation(WDGT.HAS_VIEW_PORT, createDummyViewPort2());
		return new Model<Perspective>(perspective);
	}
	
	private ViewPort createDummyViewPort1() {
		final ViewPort port = new SNViewPort();
		port.addAssociation(WDGT.CONTAINS_WIDGET, createDummyDetailsWidget());
		port.addAssociation(WDGT.CONTAINS_WIDGET, createDummyListWidget());
		return port;
	}
	
	private ViewPort createDummyViewPort2() {
		final ViewPort port = new SNViewPort();
		port.addAssociation(WDGT.CONTAINS_WIDGET, createThatsMeWidget());
		port.addAssociation(WDGT.CONTAINS_WIDGET, createDummyListWidget());
		return port;
	}
	
	private WidgetSpec createDummyListWidget() {
		final WidgetSpec widgetSpec = new SNWidgetSpec();
		widgetSpec.addAssociation(RDF.TYPE, WDGT.ENTITY_LIST);
		widgetSpec.setSelection(createListSelection());
		widgetSpec.setTitle("All people");
		
		addColumn(widgetSpec, RB.HAS_FIRST_NAME);
		addColumn(widgetSpec, RB.HAS_LAST_NAME);
		addColumn(widgetSpec, RB.HAS_EMAIL);
		addColumn(widgetSpec, RB.IS_EMPLOYED_BY);
		
		return widgetSpec;
	}
	
	private WidgetSpec createDummyDetailsWidget() {
		final WidgetSpec widgetSpec = new SNWidgetSpec();
		widgetSpec.addAssociation(RDF.TYPE, WDGT.ENTITY_DETAILS);
		widgetSpec.setSelection(createListSelection());
		widgetSpec.setTitle("One people");
		return widgetSpec;
	}
	
	private WidgetSpec createThatsMeWidget() {
		final WidgetSpec widgetSpec = new SNWidgetSpec();
		widgetSpec.addAssociation(RDF.TYPE, WDGT.PREDEFINED);
		widgetSpec.addAssociation(WDGT.IS_IMPLEMENTED_BY_CLASS, new SNText(ThatsMeWidget.class.getCanonicalName()));
		return widgetSpec;
	}
	
	private SNSelection createListSelection() {
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
