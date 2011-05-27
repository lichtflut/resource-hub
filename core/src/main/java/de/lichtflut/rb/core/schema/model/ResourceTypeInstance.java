/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.views.ResourceView;


/**
 * <p>
 * 	Represents an instance of a well defined ResourceType (RT)
 *  This type is either represented as key_value store and as graph-based ResourceView, including associations and so on.
 * </p>
 * 
 * Created: May 17, 2011
 *
 * @author Nils Bleisch
 */
public abstract class ResourceTypeInstance<T> extends ResourceView implements Serializable{

	/**
	 * Keys which help to get some meta data for a given attribute name during runtime
	 */
	public enum MetaDataKeys{
		MAX,
		MIN,
		CURRENT,
		TYPE
	}
	private static final long serialVersionUID = 535252848460633518L;

	//-- CONSTRUCTOR --------------------------------------
	
	/**
	 * Default constructor
	 */
	public ResourceTypeInstance(){
		//do nothing
	}
	
	/**
	 * Takes the node of this type as argument
	 * @param id the {@link ResourceNode} id
	 */
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
	
	/**
	 * Get a simple attribute-name representation of the origin ones. This is realized
	 * by removing the prefix-namespaces. Please note, that this is just designed for display purposes.
	 * All other methods still require the full qualified and unique attribute name
	 */
	public abstract String getSimpleAttributeName(String attribute);
	
	// -----------------------------------------------------
		
	
	/**
	 * @param attribute
	 * @param value
	 * @param ticket the reference to an existing value assignment 
	 * @throws <p>InvalidValueException when the value does not match the required datatype or constraints
	 * or if the maximum count of possible values is reached (depends on the cardinality).
	 * of if the ticket does not exists.
	 * InvalidAttributeException if this attribute does not exists.</p>
	 */
	public abstract void addValueFor(String attribute, String value, int ticket) throws RBInvalidValueException, RBInvalidAttributeException;
	
	
	/**
	 * 
	 */
	public abstract T getValueFor(String attribute, int index);
	
	// -----------------------------------------------------
	
	/**
	 * returns all the attribute-names of this instance
	 */
	public abstract Collection<String> getAttributeNames();
	
	// -----------------------------------------------------
	
	
	
	/**
	 * return all the values for a given attribute
	 */
	public abstract Collection<T> getValuesFor(String attribute);
	
	// -----------------------------------------------------
	
	
	/**
	 *  return all the existing tickets for a given attribute
	 */
	public abstract Collection<Integer> getTicketsFor(String attribute);
	
	// -----------------------------------------------------
	
	
	/**
	 * returns a {@RBValidator} for a given attribute
	 */
	public abstract RBValidator getValidatorFor(String attribute);
	
	// -----------------------------------------------------
	
	/**
	 * tries to generate and return a ticket for given attribute.
	 * @throws <p>InvalidValueException if the maximum count of possible values (tickets) is reached (depends on the cardinality).
	 * or if the ticket does not exists. InvalidAttributeException if this attribute does not exists.</p>
	 * @return TODO: reasearch, make a concept!?!?
	 */
	public abstract Integer generateTicketFor(String attribute) throws RBInvalidValueException, RBInvalidAttributeException;
	
	// -----------------------------------------------------
	
	/**
	 * tries to release and remove an exisiting ticket
	 * @throws <p>InvalidValueException if the minimum count of possible values (tickets) is reached (depends on the cardinality).
	 * or if the ticket does not exists. InvalidAttributeException if this attribute does not exists.</p>
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
	 * Creates associations based on the internal key-value map.
	 * The new association might replace an existing older one
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
				Object val=value;
				ElementaryDataType type = pAssertion.getPropertyDeclaration().getElementaryDataType();
				switch(type){
					case DATE: type = ElementaryDataType.STRING; 
				}
				Association.create(
				this,pAssertion.getPropertyDescriptor(),new SNValue(type,val),ctx);
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
				if(cnt!=0) output.append(", ");
				output.append(this.getSimpleAttributeName(attributes.get(cnt)) + "=" + values.get(0).toString());
			}	
		}
		return output.toString();
	}
}
