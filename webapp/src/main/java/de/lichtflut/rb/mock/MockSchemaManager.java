/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.infra.exceptions.NotYetSupportedException;
import de.lichtflut.rb.core.api.SchemaExporter;
import de.lichtflut.rb.core.api.SchemaImporter;
import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.api.impl.SchemaExporterImpl;
import de.lichtflut.rb.core.api.impl.SchemaImporterImpl;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
import de.lichtflut.rb.core.schema.parser.impl.json.JsonSchemaParser;
import de.lichtflut.rb.core.schema.parser.impl.json.JsonSchemaWriter;

/**
 * Mock-Implementation of {@link ISchemaManagement}.
 *
 * Created: Aug 30, 2011
 *
 * @author Ravi Knox
 */
public class MockSchemaManager implements SchemaManager, Serializable {

	private static final long serialVersionUID = 1L;

	private Map<ResourceID, ResourceSchema> typeSchemaMap = new HashMap<ResourceID, ResourceSchema>();
	
	private Map<ResourceID, TypeDefinition> idTypeDefMap = new HashMap<ResourceID, TypeDefinition>();
	
	// -----------------------------------------------------
	
	/**
	 * Default Constructor.
	 */
	public MockSchemaManager() {
		for(ResourceSchema schema : MockResourceSchemaFactory.getInstance().getAllShemas()) {
			typeSchemaMap.put(schema.getDescribedType(), schema);
		}
		for(TypeDefinition typeDef : MockResourceSchemaFactory.getInstance().getPublicTypeDefs()) {
			idTypeDefMap.put(typeDef.getID(), typeDef);
		}
	}
	
	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceSchema findSchemaForType(final ResourceID type) {
		if (typeSchemaMap.containsKey(type)) {
			return typeSchemaMap.get(type);
		} else {
			return new ResourceSchemaImpl().setDescribedType(type);
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public TypeDefinition findTypeDefinition(final ResourceID id) {
		return idTypeDefMap.get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<ResourceSchema> findAllResourceSchemas() {
		return typeSchemaMap.values();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<TypeDefinition> findAllTypeDefinitions() {
		return idTypeDefMap.values();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(final ResourceSchema schema) {
		typeSchemaMap.put(schema.getDescribedType(), schema);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void store(final TypeDefinition definition) {
		idTypeDefMap.put(definition.getID(), definition);
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
	
}
