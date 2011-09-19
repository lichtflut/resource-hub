/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.util.Collection;
import java.util.List;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.api.SchemaImporter;
import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.RSFormat;

/**
 * Mock-Implementation of {@link ISchemaManagement}.
 *
 * Created: Aug 30, 2011
 *
 * @author Ravi Knox
 */
public class MockResourceSchemaManagement implements SchemaManager {

	private static final long serialVersionUID = 1L;

	private List<ResourceSchema> schemas;
	/**
	 * Default Constructor.
	 */
	public MockResourceSchemaManagement() {
		schemas = MockResourceSchemaFactory.getAllShemas();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceSchema getResourceSchemaForResourceType(final ResourceID id) {
		ResourceSchema rs = null;
		for (ResourceSchema schema : schemas) {
			if(schema.getDescribedType() == id){
				return schema;
			}
		}
		return rs;
	}


	@Override
	public List<ResourceSchema> getAllResourceSchemas() {
		return schemas;
	}


	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.api.SchemaManager#getAllPropertyDeclarations()
	 */
	@Override
	public Collection<PropertyDeclaration> getAllPropertyDeclarations() {
		throw new NotYetImplementedException();
	}


	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.api.SchemaManager#storeOrOverrideResourceSchema(de.lichtflut.rb.core.schema.model.ResourceSchema)
	 */
	@Override
	public void storeOrOverrideResourceSchema(ResourceSchema schema) {
		throw new NotYetImplementedException();
	}


	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.api.SchemaManager#storeOrOverridePropertyDeclaration(de.lichtflut.rb.core.schema.model.PropertyDeclaration)
	 */
	@Override
	public void storeOrOverridePropertyDeclaration(
			PropertyDeclaration declaration) {
		throw new NotYetImplementedException();
	}


	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.api.SchemaManager#storeOrOverrideResourceSchema(java.util.Collection)
	 */
	@Override
	public void storeOrOverrideResourceSchema(Collection<ResourceSchema> schemas) {
		throw new NotYetImplementedException();
	}


	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.api.SchemaManager#storeOrOverridePropertyDeclaration(java.util.Collection)
	 */
	@Override
	public void storeOrOverridePropertyDeclaration(Collection<PropertyDeclaration> declarations) {
		throw new NotYetImplementedException();
	}


	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.api.SchemaManager#getImporter(de.lichtflut.rb.core.schema.parser.RSFormat)
	 */
	@Override
	public SchemaImporter getImporter(RSFormat format) {
		throw new NotYetImplementedException();
	}

}
