/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.naming.VoidNamespace;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.RBEntityFactory;
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
@SuppressWarnings("serial")
public final class ResourceSchemaImpl implements ResourceSchema{

	//Instance members
	private ResourceID internalResource;
	private ResourceID describedResource;
	//LinkedList is chosen because it's more flexible compared to an index-driven list
	private final List<PropertyAssertion> propertyList = new LinkedList<PropertyAssertion>();

	// -----------------------------------------------------

	//Constructor stuff

	/**
	 * <p>
	 * This is the default constructor.
	 * </p>
	 */
	public ResourceSchemaImpl(){
		//Generates a SimpleResourceID instance with an random UUID for namespace and identifier each
		this.describedResource = new SimpleResourceID(
				UUID.randomUUID().toString(),
				UUID.randomUUID().toString());
	}

	// -----------------------------------------------------

	/**
	 * <p>
	 * Constructor takes as argument the internal ResourceID of this schema.
	 * This is necessary to make some override operations, instead of persisting a new schema in store
	 * </p>
	 * @param id -
	 */
	public ResourceSchemaImpl(final ResourceID id){
		this.internalResource = id;
	}

	// -----------------------------------------------------

	/**
	 * <p>
	 * Constructor takes as argument an identifier which will define the ResourceIdentifier.
	 * @param nsUri -
	 * @param name -
	 * </p>
	 */
	public ResourceSchemaImpl(final String nsUri, final String name){
		if(!(QualifiedName.isUri(nsUri + name))){
			throw new IllegalArgumentException("The identifier " + nsUri + name + " is not a valid URI");
		}
		this.describedResource = new SimpleResourceID(nsUri, name);
	}

	/**
	 * <p>
	 * Constructor takes as argument an identifier which will define the ResourceIdentifier.
	 * </p>
	 * @param name -
	 */
	public ResourceSchemaImpl(final String name){
		if(!(QualifiedName.isUri(name))){
			this.describedResource = new SimpleResourceID(new QualifiedName(VoidNamespace.getInstance(),name));

		}else{
			this.describedResource = new SimpleResourceID(new QualifiedName(name));
		}
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PropertyAssertion> getPropertyAssertions() {
		return this.propertyList;
	}

	// -----------------------------------------------------
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getResourceID() {
		return this.internalResource;
	}

	// -----------------------------------------------------

	/**
	 * Overriding toString method.
	 * @return Returns the toString()-representation of ResourceID followed by a list of
	 * toString()-PropertyDeclaration separated in '\n'
	 */
	@Override
	public String toString(){

		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("Described ResourceID " + getDescribedResourceID().getQualifiedName().toURI() + "\n");
		sBuffer.append("Internal ResourceID " + ((getResourceID()==null) ? "null" : getResourceID()
					.getQualifiedName().toURI()) + "\n");
		for (PropertyAssertion property : getPropertyAssertions()) {
			sBuffer.append("--p-r-o-p-e-r-t-y--\n" + property.toString() + "\n");
		}
		return sBuffer.toString();
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPropertyAssertion(final PropertyAssertion assertion) {
		propertyList.add(assertion);

	}

	// -----------------------------------------------------
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPropertyAssertions(final Collection<PropertyAssertion> assertions) {
		this.propertyList.clear();
		this.propertyList.addAll(assertions);
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj){
		if(!(obj instanceof ResourceSchema)){
			return false;
		}
		return getDescribedResourceID().getQualifiedName().toURI().equals(((ResourceSchema) obj)
				.getDescribedResourceID().getQualifiedName().toURI());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode(){
		return super.hashCode();
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isResolved() {
		for (PropertyAssertion assertion : this.propertyList){
			if(!assertion.isResolved()){
			return false;
			}
		}
		return true;
	}

	// -----------------------------------------------------
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getDescribedResourceID() {
		return this.describedResource;
	}

	// -----------------------------------------------------

	/**
	 * TODO: DESCRIPTION.
	 * @param id -
	 */
	public void setDescribedResourceID(final ResourceID id) {
		this.describedResource = id;
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RBEntityFactory<Object> generateRBEntity() {
		return new RBEntityImpl(this);
	}

}
