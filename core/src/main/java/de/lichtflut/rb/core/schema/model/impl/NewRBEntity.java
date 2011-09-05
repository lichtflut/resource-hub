/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.schema.model.IRBMetaInfo;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 * Implementation of {@link IRBEntity}.
 * </p>
 *
 * <p>
 * Created Aug 11, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class NewRBEntity implements IRBEntity, Serializable {

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
	 *
	 * @param node - The node.
	 */
	public NewRBEntity(final ResourceNode node) {
		this(node, null);
	}

	/**
	 * Creates a new entity with a given schema.
	 *
	 * @param schema - The schema.
	 */
	public NewRBEntity(final ResourceSchema schema) {
		this(new SNResource(), schema);
	}

	/**
	 * Creates an entity based on node and schema.
	 *
	 * @param node
	 *            The node.
	 * @param schema
	 *            The schema.
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
	 * If a {@link ResourceSchema} extists it is taken into account, as well as
	 * additional fields which may not be specified in the schema, but present
	 * in the Entity itself.
	 * </p>
	 * <p>
	 * If no {@link ResourceSchema} exists, the {@link IRBField}s will be created
	 * according to the values present in the Entity itself.
	 */
	private void initializeFields() {
		final Set<ResourceID> predicates = new HashSet<ResourceID>();
		for(Association current : node.getAssociations()) {
			predicates.add(current.getPredicate());
		}
		fields = new ArrayList<IRBField>();
			for (PropertyAssertion assertion : schema.getPropertyAssertions()) {
				final ResourceID predicate = assertion
					.getPropertyDeclaration().getIdentifier();
				fields.add(new RBField(assertion, node
						.getAssociationClients(predicate)));
				predicates.remove(predicate);
			}
		// TODO: Remove from blacklist rdf(s):*
		for (ResourceID predicate : predicates) {
			fields.add(new RBField(predicate, node
					.getAssociationClients(predicate)));
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


	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceID getType() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IRBField getField(final String fieldname) {
		for (IRBField field : fields) {
			if(field.getFieldName().equals(fieldname)){
				return field;
			}
		}
		return null;
	}

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
		return new RBMetaInfo(schema);
	}

	/**
	 * Returns this RBEntity as a {@link ResourceNode}.
	 * @return this RBEntity as a {@link ResourceNode}
	 */
	ResourceNode getNode(){
		return node;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(){
		String s = getQualifiedName() + ", ";
		for(IRBField field : getAllFields()){
			if(field.isResourceReference()){
				s += field.getLabel() + ": " + field.getConstraints() + ", ";
			}else{
				s += (field.getLabel() + ": " + field.getFieldValues() + ", ");
			}
		}
		return s;
	}

}
