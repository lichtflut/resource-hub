/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.schema.model.INewRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.schema.model.IRBMetaInfo;

/**
 * <p>
 *  [DESCRIPTION].
 * </p>
 *
 * <p>
 * 	Created Aug 11, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SchemaBasedRBEntity implements INewRBEntity {

	private final ResourceNode node;

	// -----------------------------------------------------

	/**
	 * @param node -
	 */
	public SchemaBasedRBEntity(final ResourceNode node) {
		this.node = node;
	}

	// -----------------------------------------------------

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.model.INewRBEntity#getID()
	 */
	@Override
	public ResourceID getID() {
		// create a copy of Resource ID
		return new SimpleResourceID(node);
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.model.INewRBEntity#getQualifiedName()
	 */
	@Override
	public QualifiedName getQualifiedName() {
		return node.getQualifiedName();
	}

	// -----------------------------------------------------

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.model.INewRBEntity#getAllFields()
	 */
	@Override
	public List<IRBField> getAllFields() {
		throw new NotYetImplementedException();
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.model.INewRBEntity#addField(de.lichtflut.rb.core.schema.model.IRBField)
	 */
	@Override
	public boolean addField(final IRBField field) {
		throw new NotYetImplementedException();
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.model.INewRBEntity#getRBMetaInfo()
	 */
	@Override
	public IRBMetaInfo getRBMetaInfo() {
		throw new NotYetImplementedException();
	}

}
