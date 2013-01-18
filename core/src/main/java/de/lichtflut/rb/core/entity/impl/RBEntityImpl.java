/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity.impl;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.common.SchemaIdentifyingType;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.InheritedDecorator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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

    private static final boolean ADD_UNDECLARED_FIELDS = false;

    private final ResourceNode node;

	private final ResourceSchema schema;

	private List<RBField> fields;

    private boolean transientState;

    // -----------------------------------------------------

	/**
	 * Creates an entity based on node only.
	 *
	 * @param node The node.
	 */
	public RBEntityImpl(final ResourceNode node) {
		super();
		this.node = InheritedDecorator.from(node);
		this.schema = null;
		initializeFields();
	}

	/**
	 * Creates an entity based on node and schema.
	 *
	 * @param node The node.
	 * @param schema The schema.
	 */
	public RBEntityImpl(final ResourceNode node, final ResourceSchema schema) {
		super();
        this.node = InheritedDecorator.from(node);
		this.schema = schema;
		if (schema != null) {
			setType(schema.getDescribedType());
		}
		initializeFields();
	}

	/**
	 * Creates an entity based on node and type.
	 *
	 * @param node The node.
	 * @param type The type of this entity.
	 */
	public RBEntityImpl(final ResourceNode node, final ResourceID type) {
		super();
        this.node = InheritedDecorator.from(node);
		this.schema = null;
		setType(type);
		initializeFields();
	}

	// -----------------------------------------------------

	@Override
	public ResourceID getID() {
		// create a copy of Resource ID
		return new SimpleResourceID(node.getQualifiedName());
	}

	/**
	 * @return The corresponding {@link ResourceNode}.
	 */
	public ResourceNode getNode(){
		return node;
	}

	@Override
	public ResourceID getType() {
		return SchemaIdentifyingType.of(node);
	}

	@Override
	public String getLabel() {
		if(null != schema){
			return schema.getLabelBuilder().build(this);
		} else{
			return "";
		}
	}

	@Override
	public RBField getField(final ResourceID predicate) {
		for (RBField field : fields) {
			if(field.getPredicate().equals(predicate)){
				return field;
			}
		}
		return null;
	}

	@Override
	public List<RBField> getAllFields() {
		return fields;
	}

	@Override
	public boolean addField(final RBField field) {
		return fields.add(field);
	}

	@Override
	public List<RBField> getQuickInfo() {
		List<RBField> list = new ArrayList<RBField>();
		if(null == schema){
			return list;
		}

		for (PropertyDeclaration pdec : schema.getQuickInfo()) {
			list.add(getField(pdec.getPropertyDescriptor()));
		}
		return list;
	}

	@Override
	public boolean hasSchema() {
		return schema != null;
	}

    @Override
    public boolean isTransient() {
        return transientState;
    }

    public RBEntityImpl markTransient() {
        this.transientState = true;
        return this;
    }

    public RBEntityImpl markPersisted() {
        this.transientState = false;
        return this;
    }

    // ----------------------------------------------------

	@Override
	public String toString(){
		String s = node.getQualifiedName() + ", ";
		for(RBField field : getAllFields()) {
			if(field.isResourceReference()) {
				s += field.getLabel(Locale.getDefault()) + ": " + field.getConstraint() + ", ";
			} else{
				s += (field.getLabel(Locale.getDefault()) + ": " + field.getValues() + ", ");
			}
		}
		return s;
	}

	// ----------------------------------------------------

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
		for(Statement current : node.getAssociations()) {
			predicates.add(current.getPredicate());
		}
		fields = new ArrayList<RBField>();
		if (schema != null) {
			for (PropertyDeclaration decl : schema.getPropertyDeclarations()) {
				final ResourceID predicate = decl.getPropertyDescriptor();
				fields.add(new DeclaredRBField(decl, SNOPS.associations(node, predicate)));
				predicates.remove(predicate);
			}
		}
        if (ADD_UNDECLARED_FIELDS) {
            addUndeclaredFields(predicates);
        }
	}

    private void addUndeclaredFields(Set<ResourceID> predicates) {
        predicates.remove(RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE);
        predicates.remove(RDF.TYPE);
        predicates.remove(RDFS.LABEL);
        for (ResourceID predicate : predicates) {
            final Set<Statement> statements = SNOPS.associations(node, predicate);
            if (!statements.isEmpty()) {
                fields.add(new UndeclaredRBField(predicate, statements));
            }
        }
    }

	/**
	 * Associate type with node.
	 * @param type The type to set.
	 */
	private void setType(final ResourceID type) {
		SNOPS.assure(node, RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE, type);
        SNOPS.associate(node, RDF.TYPE, type);
	}
}
