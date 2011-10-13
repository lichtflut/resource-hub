/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.VoidNamespace;

import de.lichtflut.rb.core.schema.model.LabelBuilder;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 *  Common implementation of {@link ResourceSchema}
 *  Please note:
 *  This class is flagged as final.
 *  That means: If you're willing to remove it to get ability to inherit or sth. like that, please be absolutely sure,
 *  that you know everything about this class when you want to override some members e.g. (See the test cases)
 * </p>
 *
 * <p>
 * 	Created Mar 01, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
@SuppressWarnings({ "serial" })
public final class ResourceSchemaImpl implements ResourceSchema {

	private final ResourceID id;
	
	private final List<PropertyDeclaration> declarations = new LinkedList<PropertyDeclaration>();

	private LabelBuilder labelBuilder = LabelBuilder.DEFAULT;
	
	private ResourceID describedType;

	// -----------------------------------------------------

	//Constructor stuff

	/**
	 * <p>
	 * This is the default constructor.
	 * </p>
	 */
	public ResourceSchemaImpl() {
		//Generates a SimpleResourceID instance with an random UUID.
		this.id = new SimpleResourceID(VoidNamespace.getInstance(), UUID.randomUUID().toString());
	}

	// -----------------------------------------------------

	/**
	 * <p>
	 * Constructor takes as argument the internal ResourceID of this schema
	 * This is necessary to make some override operations, instead of persisting a new schema in store.
	 * </p>
	 * @param id - the internal ResourceID
	 */
	public ResourceSchemaImpl(final ResourceID id) {
		this.id = id;
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getID() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PropertyDeclaration> getPropertyDeclarations() {
		return this.declarations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPropertyDeclaration(final PropertyDeclaration decl) {
		declarations.add(decl);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getDescribedType() {
		return this.describedType;
	}

	/**
	 * <p>
	 * Set up the describedResourceID, also known as ResourceType.
	 * </p>
	 * @param id - the ResourceId
	 * @return This.
	 */
	public ResourceSchemaImpl setDescribedType(final ResourceID id) {
		this.describedType = id;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LabelBuilder getLabelBuilder() {
		return labelBuilder;
	}
	
	/**
	 * Set the {@link LabelBuilder}.
	 * @param labelBuilder the labelBuilder to set
	 * @return This.
	 */
	public ResourceSchemaImpl setLabelBuilder(final LabelBuilder labelBuilder) {
		this.labelBuilder = labelBuilder;
		return this;
	}

	// -----------------------------------------------------

	/**
	 * @return Returns the toString()-representation of ResourceID followed
	 * by a list of toString()-PropertyDeclaration separated in '\n'
	 */
	@Override
	public String toString(){
		final StringBuilder sb = new StringBuilder();
		sb.append("Resource Schema " + getID().getQualifiedName() + "\n");
		sb.append("Described Type " + getDescribedType().getQualifiedName() + "\n");
		for (PropertyDeclaration decl : getPropertyDeclarations()) {
			sb.append(" + " + decl.toString() + "\n");
		}
		return sb.toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj){
		if(!(obj instanceof ResourceSchema)){
			return false;
		}
		return getDescribedType().getQualifiedName().toURI().equals(((ResourceSchema) obj)
				.getDescribedType().getQualifiedName().toURI());
	}


	@Override
	public int hashCode(){
		return super.hashCode();
	}

}
