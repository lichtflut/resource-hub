/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
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
	
	//Default constructor
	public ResourceSchemaImpl(){
		//Generates a SimpleResourceID instance with an random UUID for namespace and identifier each
		this.resource = new SimpleResourceID(
				UUID.randomUUID().toString(),
				UUID.randomUUID().toString());
	}
	
	// -----------------------------------------------------
	
	//Constructor takes as argument an identifier which will help to define the Resource Identifier
	public ResourceSchemaImpl(final String nsUri, final String name) throws IllegalArgumentException{
		this.resource = new SimpleResourceID(nsUri, name);
	}
	
	//Constructor takes as argument an identifier which will help to define the Resource Identifier
	/**
	 * TODO: ToImplement
	 */
	public ResourceSchemaImpl(final String name) throws IllegalArgumentException{
		this();
	}
	
	public List<PropertyAssertion> getPropertyAssertions() {
		return this.propertyList;
	}

	// -----------------------------------------------------
	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.model.Constraint#isLiteralConstraint()
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
		sBuffer.append("ResourceID " + getResourceID().toString() + "\n");
		for (PropertyAssertion property : getPropertyAssertions()) {
			sBuffer.append("--p-r-o-p-e-r-t-y--\n" + property.toString() + "\n");
		}
		return sBuffer.toString();
	}

	public void addPropertyAssertion(final PropertyAssertion assertion) {
		propertyList.add(assertion);
		
	}

	public boolean resolveAssertions() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setPropertyAssertions(final List<PropertyAssertion> assertions) {
		this.propertyList.clear();
		this.propertyList.addAll(assertions);
		
	}
	
	// -----------------------------------------------------
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof ResourceSchema)) return false;
		return this.resource.getQualifiedName().toURI().equals(((ResourceSchema) obj).getResourceID().getQualifiedName().toURI());
	}
	
}//End of class ResourceSchemaImpl
