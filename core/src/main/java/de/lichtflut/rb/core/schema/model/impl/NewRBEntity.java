/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.schema.model.IRBMetaInfo;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 *  Implementation of {@link IRBEntity}.
 * </p>
 *
 * <p>
 * 	Created Aug 11, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class NewRBEntity implements IRBEntity {

	private final ResourceNode node;
	private final ResourceSchema schema;
	private List<IRBField> fields;

	// -----------------------------------------------------

	/**
	 * Creates a new entity without schema.
	 */
	public NewRBEntity() {
		this(new SNResource());
	}

	/**
	 * Creates an entity based on given node without schema.
	 * @param node The node.
	 */
	public NewRBEntity(final ResourceNode node) {
		this(node, null);
	}

	/**
	 * Creates a new entity with a given schema.
	 * @param schema The schema.
	 */
	public NewRBEntity(final ResourceSchema schema) {
		this(new SNResource(), schema);
	}

	/**
	 * Creates an entity based on node and schema.
	 * @param node The node.
	 * @param schema The schema.
	 */
	public NewRBEntity(final ResourceNode node, final ResourceSchema schema) {
		super();
		this.node = node;
		this.schema = schema;
		initializeFields();
	}

	// -----------------------------------------------------

	/**
	 * <p>
	 * Initialized this {@link IRBEntity}s {@link IRBField}s
	 * </p>
	 * <p>
	 * If a {@link RBSchema} extists it is taken into account, as well as
	 * additional fields which may not be specified in the schema, but present in
	 * the Entity itself.
	 * </p>
	 * <p>
	 * If no {@link RBSchema} exists, the {@link IRBField}s will be created
	 * according to the values present in the Entity itself.
	 */
	private void initializeFields() {
		fields = new ArrayList<IRBField>();
		if(schema == null){
			// TODO: implement
			throw new NotYetImplementedException("Only RBEntites with RBSchemas are supported!");
		}else{
			for (PropertyAssertion assertion : schema.getPropertyAssertions()) {
				fields.add(new RBField(assertion,
						node.getAssociationClients(assertion.getPropertyDeclaration().getIdentifier())));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getID() {
		// create a copy of Resource ID
		return new SimpleResourceID(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QualifiedName getQualifiedName() {
		return node.getQualifiedName();
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IRBField> getAllFields() {
		return fields;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addField(final IRBField field) {
		return fields.add(field);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IRBMetaInfo getRBMetaInfo() {
		throw new NotYetImplementedException();
	}
}
