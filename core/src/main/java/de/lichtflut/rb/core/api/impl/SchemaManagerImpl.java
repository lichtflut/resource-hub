/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.QueryManager;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.api.SchemaImporter;
import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.parser.RSFormat;
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

	@Override
	public ResourceSchema findByType(final ResourceID type) {
		final ModelingConversation mc = startConversation();
		final List<ResourceNode> subjects = mc.createQueryManager().findSubjects(RBSchema.DESCRIBES, type);
		mc.close();
		if (subjects.isEmpty()) {
			return null;
		} else if (subjects.size() == 1) {
			final SNResourceSchema schemaNode = SNResourceSchema.view(subjects.get(0));
			return binding.toModelObject(schemaNode);
		} else {
			throw new IllegalStateException("Found more than one Schema for type " + type + ": " + subjects);
		}
	}

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

	@Override
	public Collection<TypeDefinition> findAllTypeDefinitions() {
		final List<TypeDefinition> result = new ArrayList<TypeDefinition>();
		final List<ResourceNode> nodes = query().findByType(RBSchema.PROPERTY_TYPE_DEF);
		for (ResourceNode node : nodes) {
			final TypeDefinition typeDef = binding.toModelObject(new SNPropertyTypeDefinition(node));
			result.add(typeDef);
		}
		return result;
	}
	
	// -----------------------------------------------------

	@Override
	public void store(final ResourceSchema schema) {
		final ModelingConversation mc = startConversation();
		final ResourceNode existing = mc.findResource(schema.getID().getQualifiedName());
		if (existing != null) {
			mc.remove(existing, true);
		}
		final SNResourceSchema node = binding.toSemanticNode(schema);
		mc.attach(node);
		mc.close();
	}

	@Override
	public SchemaImporter getImporter(final RSFormat format) {
		throw new NotYetImplementedException();
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
