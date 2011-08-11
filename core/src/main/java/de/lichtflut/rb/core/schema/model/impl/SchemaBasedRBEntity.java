/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.schema.model.INewRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.schema.model.IRBMetaInfo;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 *  Implementation of {@link INewRBEntity}.
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
	
	@SuppressWarnings("unused")
	private final ResourceSchema schema;

	// -----------------------------------------------------

	/**
	 * Creates a new entity without schema. 
	 */
	public SchemaBasedRBEntity() {
		this(new SNResource());
	}
	
	/**
	 * Creates an entity based on given node without schema.
	 * @param node The node.
	 */
	public SchemaBasedRBEntity(final ResourceNode node) {
		this(node, null);
	}
	
	/**
	 * Creates a new entity with a given schema.
	 * @param schema The schema.
	 */
	public SchemaBasedRBEntity(final ResourceSchema schema) {
		this(new SNResource(), schema);
	}
	
	/**
	 * Creates an entity based on node and schema.
	 * @param node The node.
	 * @param schema The schema.
	 */
	public SchemaBasedRBEntity(final ResourceNode node, final ResourceSchema schema) {
		super();
		this.node = node;
		this.schema = schema;
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
