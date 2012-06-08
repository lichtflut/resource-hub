/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.persistence.TransactionControl;
import org.arastreju.sge.query.FieldParam;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.infra.exceptions.NotYetSupportedException;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.ReferenceConstraint;
import de.lichtflut.rb.core.schema.parser.impl.json.JsonSchemaParser;
import de.lichtflut.rb.core.schema.parser.impl.json.JsonSchemaWriter;
import de.lichtflut.rb.core.schema.parser.impl.rsf.RsfSchemaParser;
import de.lichtflut.rb.core.schema.persistence.ConstraintResolver;
import de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration;
import de.lichtflut.rb.core.schema.persistence.SNResourceSchema;
import de.lichtflut.rb.core.schema.persistence.Schema2GraphBinding;
import de.lichtflut.rb.core.services.SchemaExporter;
import de.lichtflut.rb.core.services.SchemaImporter;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 * 	Implementation of {@link SchemaManager}.
 * </p>
 * 
 * Created: Apr 19, 2011
 * 
 * @author Nils Bleisch
 * @author Oliver Tigges
 */
public class SchemaManagerImpl extends AbstractService implements SchemaManager {

	private Schema2GraphBinding binding;
	
	private Logger logger = LoggerFactory.getLogger(SchemaManagerImpl.class);

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * @param provider The service provider.
	 */
	public SchemaManagerImpl(final ServiceProvider provider) {
		super(provider);
		this.binding = new Schema2GraphBinding(new ConstraintResolverImpl());
	}
	
	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceSchema findSchemaForType(final ResourceID type) {
		final SNResourceSchema schemaNode = findSchemaNodeByType(type);
		if (schemaNode != null) {
			final ResourceSchema schema = binding.toModelObject(schemaNode);
			return schema;
		} else {
			return null;
		}
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public boolean isSchemaDefinedFor(ResourceID type) {
		return findSchemaNodeByType(type) != null;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Constraint findConstraint(final ResourceID id) {
		final ModelingConversation mc = mc();
		final ResourceNode node = mc.findResource(id.getQualifiedName());
		if (node != null) {
			return new ReferenceConstraint(node);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<ResourceSchema> findAllResourceSchemas() {
		final List<ResourceSchema> result = new ArrayList<ResourceSchema>();
		final List<ResourceNode> nodes = gate().createQueryManager().findByType(RBSchema.RESOURCE_SCHEMA);
		for (ResourceNode node : nodes) {
			final ResourceSchema schema = binding.toModelObject(SNResourceSchema.view(node));
			result.add(schema);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Constraint> findPublicConstraints() {
		final List<Constraint> result = new ArrayList<Constraint>();
		final List<ResourceNode> nodes = gate().createQueryManager().findByType(RBSchema.PUBLIC_CONSTRAINT);
		for (ResourceNode node : nodes) {
			final Constraint constraint = new ReferenceConstraint(node);
			if (constraint.isPublic()) {
				result.add(constraint);
			}
		}
		return result;
	}
	
	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(final ResourceSchema schema) {
		Validate.isTrue(schema.getDescribedType() != null, "The type described by this schema is not defined.");
		final ModelingConversation mc = mc();
		final TransactionControl tx = mc.beginTransaction();
		try {
			final SNResourceSchema existing = findSchemaNodeByType(schema.getDescribedType());
			if (existing != null) {
				removeSchema(mc, existing);
			}
			ensureReferencedResourcesExist(mc, schema);
			final SNResourceSchema node = binding.toSemanticNode(schema);
			mc.attach(node);
			logger.info("Stored schema for type {}.", schema.getDescribedType());
			tx.success();
		} finally {
			tx.finish();
		}
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void removeSchemaForType(final ResourceID type) {
		final ModelingConversation mc = mc();
		final SNResourceSchema existing = findSchemaNodeByType(type);
		if (existing != null) {
			removeSchema(mc, existing);
			logger.info("Removed schema for type {}.", type);
		}
	}
	
	

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void store(final Constraint constraint) {
		Validate.isTrue(constraint.isPublic(), "Only public type definition may be stopred explicitly.");
		final ModelingConversation mc = mc();
		remove(constraint);
		mc.attach(constraint.asResourceNode());
		logger.info("Stored public constraint for {}.", constraint.getName());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(Constraint constraint){
		final ModelingConversation mc = mc();
		final ResourceNode existing = mc.findResource(constraint.asResourceNode().getQualifiedName());
		if(null != existing){
			mc.remove(existing);
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Constraint prepareConstraint(final QualifiedName qn, final String displayName) {
		final ReferenceConstraint constraint = new ReferenceConstraint(new SNResource(qn));
		constraint.setApplicableDatatypes(Collections.singletonList(Datatype.STRING));
		constraint.setName(displayName);
		constraint.isPublic(true);
		constraint.setLiteralConstraint("*");
		store(constraint);
		return constraint;
	}
	
	// -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaImporter getImporter(final String format) {
		if ("JSON".equalsIgnoreCase(format.trim())) {
			return new SchemaImporterImpl(getProvider(), new JsonSchemaParser());
		} 
		if ("RSF".equalsIgnoreCase(format.trim())) {
			return new SchemaImporterImpl(getProvider(), new RsfSchemaParser());
		} else {
			throw new NotYetSupportedException("Unsupported format: " + format);
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public SchemaExporter getExporter(final String format) {
		if ("JSON".equalsIgnoreCase(format.trim())) {
			return new SchemaExporterImpl(this, new JsonSchemaWriter());
		} else {
			throw new NotYetSupportedException("Unsupported format: " + format);
		}
	}

	// -----------------------------------------------------
	
	/**
	 * Find the persistent node representing the schema of the given type.
	 */
	private SNResourceSchema findSchemaNodeByType(final ResourceID type) {
		final Query query = query().add(new FieldParam(RBSchema.DESCRIBES, type));
		final QueryResult result = query.getResult();
		if (result.isEmpty()) {
			return null;
		} else if (result.size() == 1) {
			return SNResourceSchema.view(result.iterator().next());
		} else {
			throw new IllegalStateException("Found more than one Schema for type " + type + ": " + result);
		}
	}
	
	/**
	 * Removes the schema graph.
	 * @param mc The existing conversation.
	 * @param existing The schema node.
	 */
	protected void removeSchema(final ModelingConversation mc, final SNResourceSchema schemaNode) {
		for(SNPropertyDeclaration decl : schemaNode.getPropertyDeclarations()) {
			if (decl.hasConstraint() && !decl.getConstraint().isPublic()) {
				mc.remove(decl.getConstraint().asResourceNode());
			}
			mc.remove(decl);
		}
		mc.remove(schemaNode);
	}
	
	/**
	 * Checks if the resources referenced by this schema exist and have the correct settings:
	 * <ul>
	 * 	<li>Described Type</li>
	 * 	<li>Properties of Property Declarations</li>
	 * </ul>
	 * @param type The type.
	 */
	private void ensureReferencedResourcesExist(final ModelingConversation mc, final ResourceSchema schema) {
		// 1st: check described type
		final ResourceNode attached = mc.resolve(schema.getDescribedType());
		final Set<SemanticNode> clazzes = SNOPS.objects(attached, RDF.TYPE);
		if (!clazzes.contains(RBSystem.TYPE)) {
			SNOPS.associate(attached, RDF.TYPE, RBSystem.TYPE);
		}
		if (!clazzes.contains(RDFS.CLASS)) {
			SNOPS.associate(attached, RDF.TYPE, RDFS.CLASS);
		}
		// 2nd: check properties
		for (PropertyDeclaration decl : schema.getPropertyDeclarations()) {
			final SNProperty property = mc.resolve(decl.getPropertyDescriptor()).asResource().asProperty();
			if (!property.isSubPropertyOf(RDF.PROPERTY)) {
				SNOPS.associate(property, RDF.TYPE, RDF.PROPERTY);
			}
		}
	}
	
	// -----------------------------------------------------
	
	/**
	 * Simple implementation of {@link ConstraintResolver}.
	 */
	private class ConstraintResolverImpl implements ConstraintResolver {
		@Override
		public Constraint resolve(final Constraint constraint) {
			final ModelingConversation mc = mc();
			final ResourceNode node = mc.findResource(constraint.asResourceNode().getQualifiedName());
			if (node != null) {
				return new ReferenceConstraint(node);
			} else {
				return null;
			}
		}
	}
	
}
