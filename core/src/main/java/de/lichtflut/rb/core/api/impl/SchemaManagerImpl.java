/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

import java.util.Collection;
import java.util.List;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.api.SchemaImporter;
import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.RSFormat;
import de.lichtflut.rb.core.schema.persistence.RBSchemaStore;
import de.lichtflut.rb.core.schema.persistence.SNResourceSchema;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * Reference impl of {@link ResourceSchemaManager}. This impl will take the
 * default context (rbschema#context)
 *
 * Created: Apr 19, 2011
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public class SchemaManagerImpl implements SchemaManager {

	// -------------MEMBER-FIELDS--------------------------

	private ServiceProvider provider = null;
	private RBSchemaStore store = null;

	// -----------------------------------------------------

	/**
	 * Constructor.
	 *
	 * @param provider
	 *            -
	 */
	public SchemaManagerImpl(final ServiceProvider provider) {
		this.provider = provider;
		store = new RBSchemaStore(provider.getArastejuGate());
	}

	@Override
	public ResourceSchema getResourceSchemaForResourceType(final ResourceID id) {
		final SNResourceSchema schema = store.loadSchemaForResourceType(id,null);
		if (schema == null) {
			return null;
		} else {
			return store.convertResourceSchema(schema);
		}
	}

	@Override
	public Collection<ResourceSchema> getAllResourceSchemas() {
		return (List<ResourceSchema>) this.store.loadAllResourceSchemas(null);
	}

	@Override
	public Collection<PropertyDeclaration> getAllPropertyDeclarations() {
		return this.store.loadAllPropertyDeclarations(null);
	}

	@Override
	public void storeOrOverrideResourceSchema(final ResourceSchema schema) {
		if(schema!=null){ store.store(schema,null);}

	}

	@Override
	public void storeOrOverridePropertyDeclaration(
			final PropertyDeclaration declaration) {
		if(declaration!=null){store.store(declaration,null);}

	}

	@Override
	public void storeOrOverrideResourceSchema(final Collection<ResourceSchema> schemas) {
		for (ResourceSchema schema : schemas) {
			storeOrOverrideResourceSchema(schema);
		}

	}

	@Override
	public void storeOrOverridePropertyDeclaration(
			final Collection<PropertyDeclaration> declarations) {
		for (PropertyDeclaration propertyDeclaration : declarations) {
			storeOrOverridePropertyDeclaration(propertyDeclaration);
		}
	}

	@Override
	public SchemaImporter getImporter(final RSFormat format) {
		return new AbstractSchemaImporter(provider) {

			@Override
			RSFormat getFormat() {
				return format;
			}
		};
	}

}
