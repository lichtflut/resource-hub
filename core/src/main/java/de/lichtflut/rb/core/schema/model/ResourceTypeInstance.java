/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.views.ResourceView;


/**
 * <p>
 * 	Represents an instance of a well defined ResourceType (RT)
 * </p>
 * 
 * Created: May 17, 2011
 *
 * @author Nils Bleisch
 */
public abstract class ResourceTypeInstance<T> extends ResourceView implements Serializable{

	public enum MetaDataKeys{
		MAX,
		MIN,
		CURRENT,
		TYPE
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 535252848460633518L;

	
	
	//-- CONSTRUCTOR --------------------------------------
	
	
	public ResourceTypeInstance(){
		//do nothing
	}
		
	
	public ResourceTypeInstance(ResourceNode id){
		super(id);
	}
	
	/**
	 * @param attribute
	 * @param value
	 * @throws <p>InvalidValueException when the value does not match the required datatype or constraints
	 * or if the maximum count of possible values is reached (depends on the cardinality).
	 * InvalidAttributeException if this attribute does not exists.</p>
	 * @return the ticket where this value has been stored
	 */
	public abstract Integer addValueFor(String attribute, T value) throws RBInvalidValueException, RBInvalidAttributeException;
	
	// -----------------------------------------------------
	
	
	public abstract String getSimpleAttributeName(String attribute);
	
	// -----------------------------------------------------
		
	
	/**
	 * 
	 */
	public abstract void addValueFor(String attribute, String value, int ticket) throws RBInvalidValueException, RBInvalidAttributeException;
	
	
	/**
	 * 
	 */
	public abstract T getValueFor(String attribute, int index);
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public abstract Collection<String> getAttributeNames();
	
	// -----------------------------------------------------
	
	
	
	/**
	 * 
	 */
	public abstract Collection<T> getValuesFor(String attribute);
	
	// -----------------------------------------------------
	
	
	/**
	 * 
	 */
	public abstract Collection<Integer> getTicketsFor(String attribute);
	
	// -----------------------------------------------------
	
	
	/**
	 * 
	 */
	public abstract RBValidator getValidatorFor(String attribute);
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public abstract Integer generateTicketFor(String attribute) throws RBInvalidValueException, RBInvalidAttributeException;
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public abstract void releaseTicketFor(String attribute, int ticket ) throws RBInvalidValueException,RBInvalidAttributeException;
		
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public abstract Object  getMetaInfoFor(String attribute, MetaDataKeys key);
	
	// -----------------------------------------------------

	/**
	 * 
	 */
	public abstract ResourceSchema getResourceSchema();
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public  abstract ResourceID getResourceTypeID();
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public void createAssociations(Context ctx){
		Association.create(this,RDF.TYPE, getResourceTypeID(),ctx);
		ResourceSchema schema = getResourceSchema();
		Collection<PropertyAssertion> assertions = schema.getPropertyAssertions();
		for (PropertyAssertion pAssertion : assertions) {
			try{
				this.removeAssociations(pAssertion.getPropertyDescriptor());
			}catch(org.neo4j.graphdb.NotFoundException e){
				//If this node was not found, do nothing
			}
			Collection<T> values = this.getValuesFor(pAssertion.getPropertyDescriptor().getQualifiedName().toURI());
			for (T value : values) {
				Association.create(
				this,pAssertion.getPropertyDescriptor(),new SNValue(pAssertion.getPropertyDeclaration().getElementaryDataType(),value),ctx);
			}//End of inner for loop
		}//End of outer for loop
	}
	
	/**
	 * 
	 */
	@Override
	public String toString(){
		//Show the first 2 Values
		int maxVal=2;
		String schemaName = this.getResourceSchema().getDescribedResourceID().getQualifiedName().getSimpleName();
		StringBuilder output = new StringBuilder("[" + schemaName + "] ");
		ArrayList<String> attributes = new ArrayList<String>(this.getAttributeNames());
		for(int cnt = 0;(cnt < maxVal && cnt < attributes.size()); cnt++){
			ArrayList<T> values = new ArrayList<T>(this.getValuesFor(attributes.get(cnt)));
			if(values.size()>0){
				output.append(this.getSimpleAttributeName(attributes.get(cnt)) + "=" + values.get(0).toString() + ", ");
			}	
		}
		return output.toString();
	}
}
