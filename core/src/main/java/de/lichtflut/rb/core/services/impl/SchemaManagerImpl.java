/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.query.FieldParam;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryManager;
import org.arastreju.sge.query.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.infra.exceptions.NotYetSupportedException;
import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
import de.lichtflut.rb.core.schema.parser.impl.json.JsonSchemaParser;
import de.lichtflut.rb.core.schema.parser.impl.json.JsonSchemaWriter;
import de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration;
import de.lichtflut.rb.core.schema.persistence.SNPropertyTypeDefinition;
import de.lichtflut.rb.core.schema.persistence.SNResourceSchema;
import de.lichtflut.rb.core.schema.persistence.Schema2GraphBinding;
import de.lichtflut.rb.core.schema.persistence.TypeDefinitionResolver;
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
@SuppressWarnings("serial")
public class SchemaManagerImpl implements SchemaManager {

	// -------------MEMBER-FIELDS--------------------------

	private ServiceProvider provider;
	
	private Schema2GraphBinding binding;
	
	private Logger logger = LoggerFactory.getLogger(SchemaImporterImpl.class);

	// -----------------------------------------------------

	/**
	 * Constructor.
	 * @param provider The service provider.
	 */
	public SchemaManagerImpl(final ServiceProvider provider) {
		this.provider = provider;
		this.binding = new Schema2GraphBinding(new TypeDefResolverImpl());
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
	public TypeDefinition findTypeDefinition(final ResourceID id) {
		final ModelingConversation mc = startConversation();
		final ResourceNode node = mc.findResource(id.getQualifiedName());
		mc.close();
		if (node != null) {
			return binding.toModelObject(new SNPropertyTypeDefinition(node));
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
		final List<ResourceNode> nodes = query().findByType(RBSchema.RESOURCE_SCHEMA);
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
	public Collection<TypeDefinition> findPublicTypeDefinitions() {
		final List<TypeDefinition> result = new ArrayList<TypeDefinition>();
		final List<ResourceNode> nodes = query().findByType(RBSchema.PROPERTY_TYPE_DEF);
		for (ResourceNode node : nodes) {
			final TypeDefinition typeDef = binding.toModelObject(new SNPropertyTypeDefinition(node));
			if (typeDef.isPublicTypeDef()) {
				result.add(typeDef);
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
		final ModelingConversation mc = startConversation();
		final SNResourceSchema existing = findSchemaNodeByType(schema.getDescribedType());
		if (existing != null) {
			removeSchema(mc, existing);
		}
		ensureReferencedResourcesExist(mc, schema);
		final SNResourceSchema node = binding.toSemanticNode(schema);
		mc.attach(node);
		mc.close();
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void removeSchemaForType(final ResourceID type) {
		final ModelingConversation mc = startConversation();
		final SNResourceSchema existing = findSchemaNodeByType(type);
		if (existing != null) {
			removeSchema(mc, existing);
		}
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void store(final TypeDefinition definition) {
		Validate.isTrue(definition.isPublicTypeDef(), "Only public type definition may be stopred explicitly.");
		final ModelingConversation mc = startConversation();
		final ResourceNode existing = mc.findResource(definition.getID().getQualifiedName());
		if (existing != null) {
			mc.remove(existing, true);
		}
		final SNPropertyTypeDefinition node = binding.toSemanticNode(definition);
		mc.attach(node);
		mc.close();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public TypeDefinition prepareTypeDefinition(final QualifiedName qn, final String displayName) {
		final TypeDefinitionImpl typeDef = new TypeDefinitionImpl(new SimpleResourceID(qn), true);
		typeDef.setName(displayName);
		store(typeDef);
		return typeDef;
	}
	
	// -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaImporter getImporter(final String format) {
		if ("JSON".equalsIgnoreCase(format.trim())) {
			return new SchemaImporterImpl(provider, new JsonSchemaParser());
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
	
	private ModelingConversation startConversation() {
		return provider.getArastejuGate().startConversation();
	}
	
	private QueryManager query() {
		return provider.getArastejuGate().createQueryManager();
	}
	
	// -----------------------------------------------------
	
	/**
	 * Find the persistent node representing the schema of the given type.
	 */
	private SNResourceSchema findSchemaNodeByType(final ResourceID type) {
		final ModelingConversation mc = startConversation();
		final Query query = query().buildQuery().add(new FieldParam(RBSchema.DESCRIBES, type));
		final QueryResult result = query.getResult();
		mc.close();
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
			mc.remove(decl, false);
		}
		mc.remove(schemaNode, false);
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
		if (!clazzes.contains(RB.TYPE)) {
			logger.info("Making {} an rb:Type", attached);
			SNOPS.associate(attached, RDF.TYPE, RB.TYPE);
		}
		// 2nd: check properties
		for (PropertyDeclaration decl : schema.getPropertyDeclarations()) {
			final SNProperty property = mc.resolve(decl.getPropertyDescriptor()).asResource().asProperty();
			if (!property.isSubPropertyOf(RDF.PROPERTY)) {
				logger.info("Making {} an rdf:Property", property);
				SNOPS.associate(property, RDF.TYPE, RDF.PROPERTY);
			}
		}
	}
	
	// -----------------------------------------------------
	
	/**
	 * Simple implementation of {@link TypeDefinitionResolver}.
	 */
	private class TypeDefResolverImpl implements TypeDefinitionResolver {
		@Override
		public SNPropertyTypeDefinition resolve(final TypeDefinition typeDef) {
			final ModelingConversation mc = startConversation();
			final ResourceNode node = mc.findResource(typeDef.getID().getQualifiedName());
			mc.close();
			if (node != null) {
				return new SNPropertyTypeDefinition(node);
			} else {
				return null;
			}
		}
	}
	
}
