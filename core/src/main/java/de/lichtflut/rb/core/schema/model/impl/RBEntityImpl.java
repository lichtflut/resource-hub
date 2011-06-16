/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.ValueNode;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.RBEntityFactory;
import de.lichtflut.rb.core.schema.model.RBInvalidAttributeException;
import de.lichtflut.rb.core.schema.model.RBInvalidValueException;
import de.lichtflut.rb.core.schema.model.RBValidatorFactory;
import de.lichtflut.rb.core.schema.model.ResourceSchema;



/**
 * ReferenceImpl of {@link RBEntityImpl} for value-type {@link Object}
 *
 * Created: May 17, 2011
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public class RBEntityImpl extends RBEntityFactory<Object> {

	private HashMap<String, ValueHolder> internalRep = new HashMap<String, ValueHolder>();
	private HashMap<String, RBValidatorFactory<Object>> internalValidatorMap = new HashMap<String, RBValidatorFactory<Object>>();
	private HashMap<String, String> simpleAttributeNames = new HashMap<String, String>();
	private ResourceSchema schema;
	
	// --CONSTRUCTOR----------------------------------------
	
	public RBEntityImpl(ResourceSchema schema){
		this.schema=schema;
		init();
	}
	
	public RBEntityImpl(ResourceSchema schema, ResourceNode node){
		super(node);
		this.schema=schema;
		init();
	}
	
	
	public Collection<String> getAttributeNames(){
		return internalRep.keySet();
	}
	
	
	// -----------------------------------------------------
	
	public Integer generateTicketFor(String attribute) throws RBInvalidValueException, RBInvalidAttributeException{
		
		if((!containsAttribute(attribute))){
			throw new RBInvalidAttributeException("The attribute " + attribute + " is not defined");
		}
		ValueHolder vHolder = this.internalRep.get(attribute);
		int ticket = vHolder.generateTicket();
		if(ticket==-1){
			throw new RBInvalidValueException("The maximum count of values for " + attribute + " is reached, which is " + vHolder.getMetaData(MetaDataKeys.MAX));
		}
		return ticket;
	}
	
	// -----------------------------------------------------
	
	public void releaseTicketFor(String attribute, int ticket ) throws RBInvalidValueException,RBInvalidAttributeException {
		if((!containsAttribute(attribute))){
			throw new RBInvalidAttributeException("The attribute " + attribute + " is not defined");
		}
		ValueHolder vHolder = this.internalRep.get(attribute);
		if(!vHolder.removeTicket(ticket)){
			//TODO: Analyze the specific cases
			throw new RBInvalidValueException("The ticket does not exists or the minimum count of values for " + attribute + " is reached, which is " + vHolder.getMetaData(MetaDataKeys.MIN));
		}
	}	
	
	// -----------------------------------------------------
	
	
	public Integer addValueFor(String attribute, Object value) throws RBInvalidValueException, RBInvalidAttributeException {
		if(value==null) value="";
		RBValidatorFactory<Object> validator = getValidatorFor(attribute);
		if((!containsAttribute(attribute)) || validator==null){
			throw new RBInvalidAttributeException("The attribute " + attribute + " is not defined or does not have an assigned validator");
		}
	
		ValueHolder vHolder = this.internalRep.get(attribute);
		int ticket = generateTicketFor(attribute);
		try{
			validator.isValid(value);
		}catch(RBInvalidValueException exe){
			throw exe;
		}
		
		//Set the value
		if(!vHolder.setValue(value, ticket)) throw new RBInvalidValueException("The index " + ticket + " does not exists for attribute");
		return ticket;
	}
	
	// -----------------------------------------------------
	
	public void addValueFor(String attribute, Object value, int ticket) throws RBInvalidValueException, RBInvalidAttributeException {
		if(value==null) value="";
		RBValidatorFactory<Object> validator = getValidatorFor(attribute);
		
		if((!containsAttribute(attribute)) || validator==null){
			throw new RBInvalidAttributeException("The attribute " + attribute + " is not defined or does not have an assigned validator");
		}
	
		ValueHolder vHolder = this.internalRep.get(attribute);
		try{
			validator.isValid(value);
		}catch(RBInvalidValueException exe){
			throw exe;
		}
		
		//Set the value
		if(!vHolder.setValue(value, ticket)) throw new RBInvalidValueException("The index " + ticket + " does not exists for attribute");
	}

	// -----------------------------------------------------
	
	public Object getMetaInfoFor(String attribute, MetaDataKeys key) {
		if(!containsAttribute(attribute)) return null;
		return this.internalRep.get(attribute).getMetaData(key);
	}
	
	// -----------------------------------------------------
	
	public Collection<Integer> getTicketsFor(String attribute) {
		if(!containsAttribute(attribute)) return null;
		//Clone this collection to prevent external modification on the internal tickets-set
		return new ArrayList<Integer>(this.internalRep.get(attribute).getTickets());
	}
	
	
	// -----------------------------------------------------
	
	public RBValidatorFactory<Object> getValidatorFor(String attribute) {
		return this.internalValidatorMap.get(attribute);
	}

	// -----------------------------------------------------
	
	public Object getValueFor(String attribute, int index) {
		return internalRep.get(attribute).getValue(index);
	}
	
	// -----------------------------------------------------

	public Collection<Object> getValuesFor(String attribute) {
		if(internalRep.get(attribute)!=null){
			Collection<Object> values = internalRep.get(attribute).getValues();
			if(values!=null && values.size()!=0) return values;
		}
		return new ArrayList<Object>();
	}
	
	// -----------------------------------------------------

	public Collection<String> getAttributesNamesForSimple(String simpleAttribute) {
		List<String> output = new ArrayList<String>();
		Set<String> keys = simpleAttributeNames.keySet();
		for (String key : keys) {
			if(simpleAttributeNames.get(key).equals(simpleAttribute)){
				output.add(key);
			}
		}
		return output;
	}
	
	// -----------------------------------------------------

	
	public String getSimpleAttributeName(String attribute) {
		return simpleAttributeNames.get(attribute);
	}
	
	// -----------------------------------------------------

	private void init(){
		Collection<PropertyAssertion> assertions = schema.getPropertyAssertions();
		for (final PropertyAssertion propertyAssertion : assertions) {
			final PropertyDeclaration pDec = propertyAssertion.getPropertyDeclaration();
			//Setting up the validator for this property declaration
			//In this version, constraints can be ignored
			this.internalValidatorMap.put(propertyAssertion.getPropertyDescriptor().getQualifiedName().toURI(), new RBValidatorFactory<Object>(){
				@Override
				public boolean isValid(Object value)
						throws RBInvalidValueException {
					String valueTmp = value.toString().toLowerCase();
					//Try to trigger an exception and catch them finally
					try{
						switch(pDec.getElementaryDataType()){
							case RESOURCE : if(! (value instanceof ResourceID)){throw new Exception(""); } break;
							//Check for boolean
							case BOOLEAN : if(!(valueTmp.equals("0") || valueTmp.equals("1") || valueTmp.equals("true") || valueTmp.equals("false"))){
								throw new Exception("");
							} break;
							case INTEGER: Integer.parseInt(valueTmp); break;
							case DECIMAL: Double.parseDouble(valueTmp); break;
							case DATE : Date.parse(value.toString()); break;
						}
					}catch(Exception any){
						throw new RBInvalidValueException("\"" + value + "\" is not a valid value for the expected type " + pDec.getElementaryDataType());
					}
					return true;
				}
			});
			//Validator has been added
			simpleAttributeNames.put(propertyAssertion.getPropertyDescriptor().getQualifiedName().toURI(), propertyAssertion.getPropertyDescriptor().getName());
			ValueHolder vHolder = new ValueHolder(getAssociationClients(propertyAssertion.getPropertyDescriptor()), propertyAssertion);
			internalRep.put(propertyAssertion.getPropertyDescriptor().getQualifiedName().toURI(),vHolder);
		}//End of for
	}
	
	// -----------------------------------------------------
	
	public ResourceSchema getResourceSchema() {
		return this.schema;
	}

	// -----------------------------------------------------
	
	public ResourceID getResourceTypeID() {
		return this.schema.getDescribedResourceID();
	}
	
	// -----------------------------------------------------
	
	//Value Holder helper class
	
	/**
	 * 
	 */
	private class ValueHolder implements Serializable{
		private HashMap<Integer,Object> values = new HashMap<Integer,Object>();
		private ArrayList<Integer> tickets = new ArrayList<Integer>();
		private PropertyAssertion assertion;
		//Auto increment ticket counter
		private Integer ticketcnt=0;
		
		public ValueHolder(Set<SemanticNode> set, PropertyAssertion assertion){
			this.assertion = assertion;
			init(set);
		}
		
		// -----------------------------------------------------
		
		public Object getMetaData(MetaDataKeys key) {
			switch(key){
			case MAX: return assertion.getCardinality().getMaxOccurs();
			case CURRENT: return tickets.size();
			case MIN: return assertion.getCardinality().getMinOccurs();
			case TYPE: return assertion.getPropertyDeclaration().getElementaryDataType();
			}
			return null;
		}

		// -----------------------------------------------------
		
		public Collection<Integer> getTickets(){
			return tickets;
		}
		
		
		// -----------------------------------------------------
		
		public Collection<Object> getValues(){
			ArrayList<Object> output = new ArrayList<Object>();
			for (Integer ticket : tickets) {
				output.add(values.get(ticket));
			}
			return output;
		}
		
		// -----------------------------------------------------
		
		public boolean setValue(Object value, int ticket){
			//Check if the ticket does exists
			if(!tickets.contains(ticket)) return false;
			values.put(ticket, value);
			return true;
		}
		
		// -----------------------------------------------------
		
		public Object getValue(int ticket){
			//Check if the ticket does exists
			if(!tickets.contains(ticket)) return null;
			return values.get(ticket);
		}
		
		// -----------------------------------------------------
		
		public int generateTicket(){
			//If a ticket couldnt not be created
			int ticket =-1;
			if(tickets.size()< assertion.getCardinality().getMaxOccurs()){
				ticket = (ticketcnt+=1);
				tickets.add(ticket);
				//Set an initial value to null:
				setValue(null, ticket);
			}
			return ticket;
		}
		
		// -----------------------------------------------------
		
		public boolean removeTicket(final Integer ticket){
			
			//If this ticket does not exists
			if(tickets.contains(ticket)){
				//If the current amount of tickets is greater than the minimal cardinality
				if(tickets.size()> assertion.getCardinality().getMinOccurs()){
					tickets.remove((Integer) ticket);
					//Remove tickets entry:
					values.remove((Integer) ticket);
					return true;
				}
			}
			return false;
		}
		
		// -----------------------------------------------------
		
		private void init(Set<SemanticNode> set) {
			if(set==null || set.size()==0) return;
			for (SemanticNode semanticNode : set) {
				ValueNode vNode = semanticNode.asValue();
				int ticket_id = generateTicket();
				setValue(vNode.getStringValue(), ticket_id);
			}
		}
	}
	
	//--SANTA's LITTLE HELPER----------------------------------
	
	
	private boolean containsAttribute(String attribute){
		return internalRep.containsKey(attribute);
	}


	
}

