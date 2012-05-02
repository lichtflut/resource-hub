/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.Collection;
import java.util.Set;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.TypeDefinition;

/**
 * <p>
 *  Reference to a type definition needed during parsing process.
 * </p>
 *
 * <p>
 * 	Created Oct 27, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class TypeDefinitionReference implements TypeDefinition {
	
	private TypeDefinition delegate;
	
	private final ResourceID id;

	// -----------------------------------------------------
	
	/**
	 * @param id
	 */
	public TypeDefinitionReference(final ResourceID id) {
		this.id = id;
	}
	
	// -----------------------------------------------------
	
	/**
	 * @param delegate the delegate to set
	 */
	public void setDelegate(final TypeDefinition delegate) {
		this.delegate = delegate;
	}
	
	
	/**
	 * @return true if the reference is resolved.
	 */
	public boolean isResolved() {
		return delegate != null;
	}
	
	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getID() {
		return id;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPublicTypeDef() {
		return true;
	}
	
	// -----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		assertResolved();
		return delegate.getName();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLiteralValue() {
		assertResolved();
		return delegate.isLiteralValue();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public boolean isResourceReference() {
		assertResolved();
		return delegate.isResourceReference();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Datatype getDataType() {
		assertResolved();
		return delegate.getDataType();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setDataType(final Datatype type) {
		assertResolved();
		delegate.setDataType(type);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Set<Constraint> getConstraints() {
		assertResolved();
		return delegate.getConstraints();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setConstraints(Collection<Constraint> constraints) {
		assertResolved();
		delegate.setConstraints(constraints);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void addConstraint(Constraint constraint) {
		assertResolved();
		delegate.addConstraint(constraint);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getIdentifierString() {
		throw new UnsupportedOperationException();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setIdentifier(String identifierString) {
		throw new UnsupportedOperationException();
	}
	
	// -----------------------------------------------------
	
	private void assertResolved() {
		if (delegate == null) {
			throw new IllegalStateException("Type definition reference is not yet resolved.");
		}
	}

}
