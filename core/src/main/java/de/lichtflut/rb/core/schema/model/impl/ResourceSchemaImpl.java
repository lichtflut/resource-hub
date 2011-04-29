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
public final class ResourceSchemaImpl implements ResourceSchema{

	//Instance members
	private ResourceID resource;
	//LinkedList is chosen because it's more flexible compared to an index-driven list
	private final List<PropertyAssertion> propertyList = new LinkedList<PropertyAssertion>();
	
	// -----------------------------------------------------
	
	//Constructor stuff
	
	/**
	 * <p>
	 * This is the default constructor
	 * </p>
	 */
	public ResourceSchemaImpl(){
		//Generates a SimpleResourceID instance with an random UUID for namespace and identifier each
		this.resource = new SimpleResourceID(
				UUID.randomUUID().toString(),
				UUID.randomUUID().toString());
	}
	
	// -----------------------------------------------------
	
	/**
	 * <p>
	 * Constructor takes as argument an identifier which will define the ResourceIdentifier
	 * </p>
	 */
	public ResourceSchemaImpl(ResourceID id){
		this.resource = id;
	}
	
	// -----------------------------------------------------
	
	/**
	 * <p>
	 * Constructor takes as argument an identifier which will define the ResourceIdentifier
	 * </p>
	 */
	public ResourceSchemaImpl(final String nsUri, final String name) throws IllegalArgumentException{
		if(!(QualifiedName.isUri(nsUri + name) || QualifiedName.isUri(nsUri + name)))
			throw new IllegalArgumentException("The identifier " + nsUri + name + " is not a valid URI");
		this.resource = new SimpleResourceID(nsUri, name);
	}
	
	/**
	 * <p>
	 * Constructor takes as argument an identifier which will define the ResourceIdentifier
	 * </p>
	 */
	public ResourceSchemaImpl(final String name){
		if(!(QualifiedName.isUri(name) || QualifiedName.isUri(name)))
			this.resource = new SimpleResourceID(new QualifiedName(VoidNamespace.getInstance(),name));
		else
			this.resource = new SimpleResourceID(new QualifiedName(name));
	}
	
	// -----------------------------------------------------
	
	public Collection<PropertyAssertion> getPropertyAssertions() {
		return this.propertyList;
	}

	// -----------------------------------------------------
	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.model.ResourceSchema#getResourceID()
	 */
	public ResourceID getResourceID() {
		return this.resource;
	}

	// -----------------------------------------------------
	
	/**
	 * @return Returns the toString()-representation of ResourceID followed by a list of toString()-PropertyDeclaration separated in '\n'
	 */
	@Override
	public String toString(){
		
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("ResourceID " + getResourceID().getQualifiedName().toURI() + "\n");
		for (PropertyAssertion property : getPropertyAssertions()) {
			sBuffer.append("--p-r-o-p-e-r-t-y--\n" + property.toString() + "\n");
		}
		return sBuffer.toString();
	}

	// -----------------------------------------------------
	
	public void addPropertyAssertion(final PropertyAssertion assertion) {
		propertyList.add(assertion);
		
	}

	// -----------------------------------------------------
	
	public void setPropertyAssertions(final Collection<PropertyAssertion> assertions) {
		this.propertyList.clear();
		this.propertyList.addAll(assertions);
		
	}
	
	// -----------------------------------------------------
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof ResourceSchema)) return false;
		return this.resource.getQualifiedName().toURI().equals(((ResourceSchema) obj).getResourceID().getQualifiedName().toURI());
	}

	// -----------------------------------------------------
	

	public boolean isResolved() {
		for (PropertyAssertion assertion : this.propertyList) if(!assertion.isResolved()) return false;
		return true;
	}


	
}//End of class ResourceSchemaImpl
