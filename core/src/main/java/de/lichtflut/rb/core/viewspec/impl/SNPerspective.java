/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.structure.OrderBySerialNumber;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WDGT;

/**
 * <p>
 *  A perspective.
 * </p>
 *
 * <p>
 * 	Created Jan 31, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNPerspective extends ResourceView implements Perspective {

	/**
	 * @param resource
	 */
	public SNPerspective(ResourceNode resource) {
		super(resource);
	}
	
	/**
	 * @param resource
	 */
	public SNPerspective(QualifiedName qn) {
		super(qn);
		setValue(RDF.TYPE, WDGT.PERSPECTIVE);
	}

	/**
	 * Create a new perspeective.
	 */
	public SNPerspective() {
		setValue(RDF.TYPE, WDGT.PERSPECTIVE);
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getID() {
		return this;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return stringValue(RB.HAS_NAME);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return stringValue(RB.HAS_TITLE);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return stringValue(RB.HAS_DESCRIPTION);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setName(String name) {
		setValue(RB.HAS_NAME, name);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setTitle(String title) {
		setValue(RB.HAS_TITLE, title);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setDescription(String desc) {
		setValue(RB.HAS_DESCRIPTION, desc);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<ViewPort> getViewPorts() {
		final List<ViewPort> result = new ArrayList<ViewPort>();
		for(Statement stmt : getAssociations(WDGT.HAS_VIEW_PORT)) {
			result.add(new SNViewPort(stmt.getObject().asResource()));
		}
		Collections.sort(result, new OrderBySerialNumber());
		return result;
	}

}
