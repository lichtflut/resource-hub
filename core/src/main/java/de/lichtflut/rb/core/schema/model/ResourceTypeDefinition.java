/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.util.Collection;
import java.util.Set;

import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  View above {@link TypeDefinition}. Assures validity. 
 * </p>
 *
 * <p>
 * 	Created Oct 14, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceTypeDefinition implements TypeDefinition {

	private final TypeDefinition def;

	// -----------------------------------------------------
	
	/**
	 * Create a resource reference view for given Type Definition.
	 */
	public static ResourceTypeDefinition view(final TypeDefinition def) {
		return new ResourceTypeDefinition(def);
	}
	
	// -----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	protected ResourceTypeDefinition(final TypeDefinition def) {
		this.def = def;
		validate();
	}
	
	// -- EXTENDED METHODS --------------------------------
	
	public ResourceID getResourceTypeConstraint() {
		validate();
		if (getConstraints().isEmpty()) {
			return null;
		} else {
			final Constraint constraint = getConstraints().iterator().next();
			return constraint.getResourceTypeConstraint();
		}
	}
	
	// -- DELEGATE METHODS --------------------------------
	
	/**
	 * @return
	 * @see de.lichtflut.rb.core.schema.model.TypeDefinition#getID()
	 */
	public ResourceID getID() {
		return def.getID();
	}

	/**
	 * @return
	 * @see de.lichtflut.rb.core.schema.model.TypeDefinition#getName()
	 */
	public String getName() {
		return def.getName();
	}

	/**
	 * @return
	 * @see de.lichtflut.rb.core.schema.model.TypeDefinition#isPublicTypeDef()
	 */
	public boolean isPublicTypeDef() {
		return def.isPublicTypeDef();
	}

	/**
	 * @return
	 * @see de.lichtflut.rb.core.schema.model.TypeDefinition#isLiteralValue()
	 */
	public boolean isLiteralValue() {
		return def.isLiteralValue();
	}

	/**
	 * @return
	 * @see de.lichtflut.rb.core.schema.model.TypeDefinition#isResourceReference()
	 */
	public boolean isResourceReference() {
		return def.isResourceReference();
	}

	/**
	 * @return
	 * @see de.lichtflut.rb.core.schema.model.TypeDefinition#getDataType()
	 */
	public Datatype getDataType() {
		return def.getDataType();
	}

	/**
	 * @param type
	 * @see de.lichtflut.rb.core.schema.model.TypeDefinition#setDataType(org.arastreju.sge.model.ElementaryDataType)
	 */
	public void setDataType(Datatype type) {
		def.setDataType(type);
	}

	/**
	 * @return
	 * @see de.lichtflut.rb.core.schema.model.TypeDefinition#getConstraints()
	 */
	public Set<Constraint> getConstraints() {
		return def.getConstraints();
	}

	/**
	 * @param constraints
	 * @see de.lichtflut.rb.core.schema.model.TypeDefinition#setConstraints(java.util.Collection)
	 */
	public void setConstraints(Collection<Constraint> constraints) {
		def.setConstraints(constraints);
	}

	/**
	 * @param constraint
	 * @see de.lichtflut.rb.core.schema.model.TypeDefinition#addConstraint(de.lichtflut.rb.core.schema.model.Constraint)
	 */
	public void addConstraint(Constraint constraint) {
		def.addConstraint(constraint);
	}

	/**
	 * @return
	 * @deprecated
	 * @see de.lichtflut.rb.core.schema.model.TypeDefinition#getIdentifierString()
	 */
	public String getIdentifierString() {
		return def.getIdentifierString();
	}

	/**
	 * @param identifierString
	 * @deprecated
	 * @see de.lichtflut.rb.core.schema.model.TypeDefinition#setIdentifier(java.lang.String)
	 */
	public void setIdentifier(String identifierString) {
		def.setIdentifier(identifierString);
	}

	/**
	 * @param obj
	 * @return
	 * @see de.lichtflut.rb.core.schema.model.TypeDefinition#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return def.equals(obj);
	}

	/**
	 * @return
	 * @see de.lichtflut.rb.core.schema.model.TypeDefinition#hashCode()
	 */
	public int hashCode() {
		return def.hashCode();
	}
	
	// -----------------------------------------------------
	
	private void validate() {
		if (!isResourceReference()) {
			throw new IllegalStateException("type definition is not about resource references.");
		} 
		if (getConstraints().size() > 1) {
			throw new IllegalStateException("Given type definition has more than one constraint. " + getConstraints());
		} 
		if (!getConstraints().isEmpty() && !getConstraints().iterator().next().isResourceReference()) {
			throw new IllegalStateException("Constraint is not a resource type constraint.");
		}
	}
	
}
