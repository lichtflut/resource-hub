/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import java.util.Date;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.TimeMask;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.model.nodes.views.SNTimeSpec;
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
@SuppressWarnings("serial")
public class SampleResourcePage extends RBSuperPage {

	public static final ResourceID HAS_BIRTHDATE = new SimpleResourceID("urn:lf:predicates:", "hasBirthdate");

	// -----------------------------------------------------

	/**
	 * @param parameters /
	 */
	public SampleResourcePage(final PageParameters parameters) {
		super("Sample Resource", parameters);

		add(new SampleResourcePanel("resourcePane", new ResourceNodeModel<ResourceNode>(createSample())));
	}

	// -----------------------------------------------------

	/**
	 * @return /
	 */
	private static ResourceNode createSample() {


		final SNClass personClass =
			new SimpleResourceID("urn:lf:classes:", "Person").asResource().asClass();
		final ResourceNode nils = personClass.createInstance(new QualifiedName("urn:lf:people:Nils"));
		Association.create(nils, Aras.HAS_FORENAME, new SNText("Nils"));
		Association.create(nils, Aras.HAS_SURNAME, new SNText("Flyshe"));
		Association.create(nils, HAS_BIRTHDATE, new SNTimeSpec(new Date(), TimeMask.DATE));
		return nils;
	}


}
