/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec.impl;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;

import de.lichtflut.infra.exceptions.NotYetSupportedException;
import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;

/**
 * <p>
 *  Node representing a widget specification.
 * </p>
 *
 * <p>
 * 	Created Jan 23, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNWidgetSpec extends ResourceView implements WidgetSpec {

	/**
	 * Default constructor for new widget specifications.
	 */
	public SNWidgetSpec() {
	}
	
	/**
	 * @param resource
	 */
	public SNWidgetSpec(ResourceNode resource) {
		super(resource);
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
	public Selection getSelection() {
		SemanticNode node = SNOPS.singleObject(this, WDGT.HAS_SELECTION);
		if (node != null && node.isResourceNode()) {
			return new SNSelection(node.asResource());
		} else {
			return null;
		}
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
	public void setSelection(Selection selection) {
		if (selection instanceof ResourceNode) {
			setValue(WDGT.HAS_SELECTION, selection);
		} else {
			throw new NotYetSupportedException();
		}
	}
	
}
