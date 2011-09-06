/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.RSFormat;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;

/**
 * Mock-Implementation of {@link ResourceSchemaManagement}.
 *
 * Created: Aug 30, 2011
 *
 * @author Ravi Knox
 */
public class MockResourceSchemaManagement implements ResourceSchemaManagement {

	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor.
	 */
	public MockResourceSchemaManagement() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFormat(final RSFormat format) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RSFormat getFormat() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void storeSchemaRepresentation(final String representation, final RSFormat format) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String loadSchemaRepresenation(final RSFormat format) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RSParsingResult generateAndResolveSchemaModelThrough(final InputStream is) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RSParsingResult generateAndResolveSchemaModelThrough(final File file) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RSParsingResult generateAndResolveSchemaModelThrough(final String s) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RSParsingResult generateSchemaModelThrough(final InputStream is) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RSParsingResult generateSchemaModelThrough(final File file) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RSParsingResult generateSchemaModelThrough(final String s) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceSchema getResourceSchemaForResourceType(final ResourceID id) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<ResourceSchema> getAllResourceSchemas() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PropertyDeclaration> getAllPropertyDeclarations() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void storeOrOverrideResourceSchema(final ResourceSchema schema) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void storeOrOverridePropertyDeclaration(
			final PropertyDeclaration declaration) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void storeOrOverrideResourceSchema(final Collection<ResourceSchema> schema) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void storeOrOverridePropertyDeclaration(
			final Collection<PropertyDeclaration> declarations) {
	}

}
