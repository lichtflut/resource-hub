/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.MetaInfo;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 * Implementation of {@link RBEntity}.
 * </p>
 *
 * <p>
 * Created Aug 11, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class RBEntityImpl implements RBEntity {

	private final ResourceNode node;
	private final ResourceSchema schema;
	private final List<ResourceID> type;
	private List<RBField> fields;

	// -----------------------------------------------------

	/**
	 * Creates a new entity without schema.
	 * @param type - rdf:type
	 */
	public RBEntityImpl(final ResourceID type) {
		this(new SNResource(), type);
	}

	/**
	 * Creates a new entity with a given schema.
	 *
	 * @param schema - The schema.
	 */
	public RBEntityImpl(final ResourceSchema schema) {
		this(new SNResource(), schema);
	}

	/**
	 * Creates an entity based on given node without schema.
	 *
	 * @param node - The node.
	 * @param type - rdf:type
	 */
	public RBEntityImpl(final ResourceNode node, final ResourceID type) {
		super();
		this.node=node;
		this.type = new ArrayList<ResourceID>();
		this.schema = null;
		if(node.getAssociations(RDF.TYPE).isEmpty()){
			this.type.add(type);
		}else{
			for (Association assoc : node.getAssociations(RDF.TYPE)) {
				this.type.add(assoc.getObject().asResource().asClass());
			}
		}
		initializeFields();

	}

	/**
	 * Creates an entity based on node and schema.
	 *
	 * @param node
	 *            The node.
	 * @param schema
	 *            The schema.
	 */
	public RBEntityImpl(final ResourceNode node, final ResourceSchema schema) {
		super();
		this.node = node;
		this.schema = schema;
		this.type=new ArrayList<ResourceID>();
		this.type.add(schema.getDescribedType());
		initializeFields();
	}

	// -----------------------------------------------------

	/**
	 * <p>
	 * Initialized this {@link RBEntity}s {@link RBField}s
	 * </p>
	 * <p>
	 * If a {@link ResourceSchema} extists it is taken into account, as well as
	 * additional fields which may not be specified in the schema, but present
	 * in the Entity itself.
	 * </p>
	 * <p>
	 * If no {@link ResourceSchema} exists, the {@link RBField}s will be created
	 * according to the values present in the Entity itself.
	 */
	private void initializeFields() {
		final Set<ResourceID> predicates = new HashSet<ResourceID>();
		for(Association current : node.getAssociations()) {
			predicates.add(current.getPredicate());
		}
		fields = new ArrayList<RBField>();
		if (schema != null) {
		for (PropertyDeclaration assertion : schema.getPropertyDeclarations()) {
			final ResourceID predicate = assertion
				.getTypeDefinition().getID();
			fields.add(new RBFieldImpl(assertion, SNOPS.objects(node, predicate)));
			predicates.remove(predicate);
			}
		}
		// TODO: Remove from blacklist rdf(s):*
		for (ResourceID predicate : predicates) {
			System.out.println("PREDICATE: " + predicate);
			System.out.println("VALUE: " + SNOPS.objects(node, predicate));
			fields.add(new RBFieldImpl(predicate, SNOPS.objects(node, predicate)));
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
		if(null != schema){
			return schema.getLabelBuilder().build(this);
		}else{
			return "";
		}
	}

	@Override
	public ResourceID getType() {
//		node.asEntity().getMainClass();
		// TODO Get type from node
		return type.get(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RBField getField(final ResourceID predicate) {
		for (RBField field : fields) {
			if(field.getPredicate().equals(predicate)){
				return field;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RBField> getAllFields() {
		return fields;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addField(final RBField field) {
		return fields.add(field);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MetaInfo getRBMetaInfo() {
		return new StandardRBMetaInfo(schema);
	}

	/**
	 * Returns this RBEntity as a {@link ResourceNode}.
	 * @return this RBEntity as a {@link ResourceNode}
	 */
	public ResourceNode getNode(){
		return node;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(){
		String s = getQualifiedName() + ", ";
		for(RBField field : getAllFields()){
			if(field.isResourceReference()){
				s += field.getLabel() + ": " + field.getConstraints() + ", ";
			}else{
				s += (field.getLabel() + ": " + field.getValues() + ", ");
			}
		}
		return s;
	}

}
