/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/**
 * [TODO Insert description here.]
 * 
 * Created: May 17, 2011
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public class ResocureTypeInstanceImpl implements ResourceTypeInstance<String> {

	private HashMap<String, ValueHolder> internalRep = new HashMap<String, ValueHolder>();
	private HashMap<String, RBValidator<String>> internalValidatorMap = new HashMap<String, RBValidator<String>>();
	private ResourceSchema schema;
	
	// --CONSTRUCTOR----------------------------------------
	
	public ResocureTypeInstanceImpl(ResourceSchema schema){
		this.schema=schema;
		init();
	}
	
	public Collection<String> getAttributeNames(){
		return internalRep.keySet();
	}
	
	
	// -----------------------------------------------------
	
	public int generateTicketFor(String attribute) throws RBInvalidValueException, RBInvalidAttributeException{
		
		if((!internalRep.containsKey(attribute)) ){
			throw new RBInvalidAttributeException("The attribute " + attribute + " is not defined or does not have an assigned validator");
		}
		ValueHolder vHolder = this.internalRep.get(attribute);
		int ticket = vHolder.generateTicket();
		if(ticket==-1){
			throw new RBInvalidValueException("The maximum count of values for " + attribute + " is reached, which is " + vHolder.getMetaData(MetaDataKeys.MAX));
		}
		return ticket;
	}
	
	public int addValueFor(String attribute, String value) throws RBInvalidValueException, RBInvalidAttributeException {
		if(value==null) value="";
		RBValidator<String> validator = getValidatorFor(attribute);
		
		if((!internalRep.containsKey(attribute)) || validator==null){
			throw new RBInvalidAttributeException("The attribute " + attribute + " is not defined or does not have an assigned validator");
		}
	
		ValueHolder vHolder = this.internalRep.get(attribute);
		int ticket = vHolder.generateTicket();
		if(ticket==-1){
			throw new RBInvalidValueException("The maximum count of values for " + attribute + " is reached, which is " + vHolder.getMetaData(MetaDataKeys.MAX));
		}
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
	
	public int addValueFor(String attribute, String value, int ticket) throws RBInvalidValueException, RBInvalidAttributeException {
		if(value==null) value="";
		RBValidator<String> validator = getValidatorFor(attribute);
		
		if((!internalRep.containsKey(attribute)) || validator==null){
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
		return ticket;
	}

	public int geMetaInfoFor(String attribute, MetaDataKeys key) {
		return (Integer) this.internalRep.get(attribute).getMetaData(key);
	}
	
	
	// -----------------------------------------------------
	
	public RBValidator<String> getValidatorFor(String attribute) {
		return this.internalValidatorMap.get(attribute);
	}

	// -----------------------------------------------------
	
	public String getValueFor(String attribute, int index) {
		return internalRep.get(attribute).getValue(index);
	}
	
	// -----------------------------------------------------

	public Collection<String> getValuesFor(String attribute) {
		ArrayList<String> values = internalRep.get(attribute).values;
		if(values!=null && values.size()!=0) return values;
		return null;
	}
	
	// -----------------------------------------------------

	private void init(){
		Collection<PropertyAssertion> assertions = schema.getPropertyAssertions();
		for (final PropertyAssertion propertyAssertion : assertions) {
			final PropertyDeclaration pDec = propertyAssertion.getPropertyDeclaration();
			//Setting up the validator for this property declaration
			//In this version, constraints can be ignored
			this.internalValidatorMap.put(propertyAssertion.getPropertyDescriptor().getQualifiedName().toURI(), new RBValidator<String>(){
				@Override
				public boolean isValid(String value)
						throws RBInvalidValueException {
					String valueTmp = value.toLowerCase();
					//Try to trigger an exception and catch them finally
					try{
						switch(pDec.getElementaryDataType()){
							//Check for boolean
							case BOOLEAN : if(!(valueTmp.equals("0") || valueTmp.equals("1") || valueTmp.equals("true") || valueTmp.equals("false"))){
								throw new Exception("");
							} break;
							case INTEGER: Integer.parseInt(valueTmp); break;
							case DECIMAL: Double.parseDouble(valueTmp); break;
							case DATE : Date.parse(value); break;
						}
					}catch(Exception any){
						throw new RBInvalidValueException(value + " is not a valid value for the expected type " + pDec.getElementaryDataType());
					}
					
					// TODO Auto-generated method stub
					return false;
				}
			});
			//Validator has been added
			internalRep.put(propertyAssertion.getPropertyDescriptor().getQualifiedName().toURI(),new ValueHolder(propertyAssertion.getCardinality()));
		}//End of for
	}
	
	
	class ValueHolder{
		private ArrayList<String> values = new ArrayList<String>();
		private ArrayList<Integer> tickets = new ArrayList<Integer>();
		private Cardinality c;
		
		public ValueHolder(Cardinality c){
			this.c =c;
		}
		
		public Integer getMetaData(MetaDataKeys key) {
			switch(key){
			case MAX: return c.getMaxOccurs();
			case CURRENT: return tickets.size();
			case MIN: return c.getMinOccurs();
			}
			return null;
		}

		// -----------------------------------------------------
		
		public Collection<String> getValues(){
			ArrayList<String> output = new ArrayList<String>();
			for (Integer ticket : tickets) {
				output.add(values.get(ticket));
			}
			return values;
		}
		
		// -----------------------------------------------------
		
		public boolean setValue(String value, int ticket){
			//Check if the ticket does exists
			if(!tickets.contains(ticket)) return false;
			if(ticket>=values.size()){
				values.add(ticket, value);
			}else{
				if(values.get(ticket)!=null){
					values.set(ticket,value);
				}else{
					values.add(ticket, value);
				}
			}
			
			return true;
		}
		
		// -----------------------------------------------------
		
		public String getValue(int ticket){
			//Check if the ticket does exists
			if(!tickets.contains(ticket)) return null;
			return values.get(ticket);
		}
		
		// -----------------------------------------------------
		
		public int generateTicket(){
			//If a ticket couldnt not be created
			int ticket =-1;
			if(tickets.size()< c.getMaxOccurs()){
				ticket = tickets.size();
				tickets.add(ticket,ticket);
				setValue(null, ticket);
			}
			return ticket;
		}
		
		// -----------------------------------------------------
		
		public int removeTicket(int ticket){
			//If a ticket couldnt not be created
			if(tickets.size()> c.getMinOccurs()){
				tickets.remove(ticket);
			}else{
				ticket=-1;
			}
			return ticket;
		}
		
		
	}
	
}

