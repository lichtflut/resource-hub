/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.web.components.SampleResourcePanel;
import de.lichtflut.rb.web.models.ResourceNodeModel;

/**
 * <p>
 *  Simple page showing how to display a sample resource.
 * </p>
 *
 * <p>
 * 	Created Jan 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SampleResourcePage extends WebPage {

	/**
	 * @param parameters
	 */
	public SampleResourcePage(final PageParameters parameters) {
		super(parameters);
		
		add(new SampleResourcePanel("resourcePane", new ResourceNodeModel<ResourceNode>(createSample())));
	}
	
	// -----------------------------------------------------
	
	private static ResourceNode createSample() {
		final SNClass personClass = 
			new SimpleResourceID("urn:lf:classes:", "Person").asResource().asClass();
		final ResourceNode nils = personClass.createInstance( new QualifiedName("urn:lf:people:Nils"));
		Association.create(nils, Aras.HAS_FORENAME, new SNText("Nils"));
		Association.create(nils, Aras.HAS_SURNAME, new SNText("Flyshe"));
		return nils;
	}
	

}
