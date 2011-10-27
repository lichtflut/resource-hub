/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.query.QueryManager;

import de.lichtflut.infra.exceptions.NotYetSupportedException;
import de.lichtflut.rb.core.api.SchemaExporter;
import de.lichtflut.rb.core.api.SchemaImporter;
import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
import de.lichtflut.rb.core.schema.parser.impl.json.JsonSchemaParser;
import de.lichtflut.rb.core.schema.parser.impl.json.JsonSchemaWriter;
import de.lichtflut.rb.core.schema.persistence.SNPropertyTypeDefinition;
import de.lichtflut.rb.core.schema.persistence.SNResourceSchema;
import de.lichtflut.rb.core.schema.persistence.Schema2GraphBinding;
import de.lichtflut.rb.core.schema.persistence.TypeDefinitionResolver;
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
	
	private Schema2GraphBinding binding = new Schema2GraphBinding(new TypeDefResolverImpl());

	// -----------------------------------------------------

	/**
	 * Constructor.
	 * @param provider The service provider.
	 */
	public SchemaManagerImpl(final ServiceProvider provider) {
		this.provider = provider;
	}
	
	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceSchema findSchemaByType(final ResourceID type) {
		final SNResourceSchema schemaNode = findSchemaNodeByType(type);
		if (schemaNode != null) {
			return binding.toModelObject(schemaNode);	
		} else {
			return new ResourceSchemaImpl().setDescribedType(type);
		}
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
	public Collection<TypeDefinition> findAllTypeDefinitions() {
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
		final ResourceNode existing = findSchemaNodeByType(schema.getDescribedType());
		if (existing != null) {
			// remove DESCRIBES association in order to prevent the type to be deleted.
			Association assoc = SNOPS.singleAssociation(existing, RBSchema.DESCRIBES);
			existing.remove(assoc);
			mc.remove(existing, true);
		}
		final SNResourceSchema node = binding.toSemanticNode(schema);
		mc.attach(node);
		mc.close();
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
		return new TypeDefinitionImpl(new SimpleResourceID(qn), true).setName(displayName);
	}
	
	// -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaImporter getImporter(final String format) {
		if ("JSON".equalsIgnoreCase(format.trim())) {
			return new SchemaImporterImpl(this, new JsonSchemaParser());
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
		return provider.getArastejuGate().startConversation().createQueryManager();
	}
	
	// -----------------------------------------------------
	
	/**
	 * Find the persistent node representing the schema of the given type.
	 */
	private SNResourceSchema findSchemaNodeByType(final ResourceID type) {
		final ModelingConversation mc = startConversation();
		final List<ResourceNode> subjects = mc.createQueryManager().findSubjects(RBSchema.DESCRIBES, type);
		mc.close();
		if (subjects.isEmpty()) {
			return null;
		} else if (subjects.size() == 1) {
			return SNResourceSchema.view(subjects.get(0));
		} else {
			throw new IllegalStateException("Found more than one Schema for type " + type + ": " + subjects);
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
