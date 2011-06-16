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
import de.lichtflut.rb.core.schema.model.RBEntity;
import de.lichtflut.rb.core.schema.model.RBInvalidAttributeException;
import de.lichtflut.rb.core.schema.model.RBInvalidValueException;
import de.lichtflut.rb.core.schema.model.RBValidator;
import de.lichtflut.rb.core.schema.model.ResourceSchema;



/**
 * <p>
 * ReferenceImpl of {@link RBEntityImpl} for value-type {@link Object}.
 * </p>
 *
 * Created: May 17, 2011
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public class RBEntityImpl extends RBEntity<Object> {


	private HashMap<String, ValueHolder> internalRep = new HashMap<String, ValueHolder>();
	private HashMap<String, RBValidator<Object>> internalValidatorMap = new HashMap<String, RBValidator<Object>>();
	private HashMap<String, String> simpleAttributeNames = new HashMap<String, String>();
	private ResourceSchema schema;


	// --CONSTRUCTOR----------------------------------------

	/**
	 * <p>
	 * Construct an new entity-instance for a given {@link ResourceSchema}.
	 * </p>
	 * @param schema -
	 */
	public RBEntityImpl(final ResourceSchema schema){
		this.schema=schema;
		init();
	}

	/**
	 * <p>
	 * Construct an entity-instance for given {@link ResourceSchema} and an existing {@link ResourceNode}.
	 * </p>
	 * @param schema -
	 * @param node -
	 */
	public RBEntityImpl(final ResourceSchema schema, final ResourceNode node){
		super(node);
		this.schema=schema;
		init();
	}


	/** {@inheritDoc} */
	@Override
	public Collection<String> getAttributeNames(){
		return internalRep.keySet();
	}


	// -----------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public Integer generateTicketFor(final String attribute) throws RBInvalidValueException, RBInvalidAttributeException{

		if((!containsAttribute(attribute))){
			throw new RBInvalidAttributeException("The attribute " + attribute + " is not defined");
		}
		ValueHolder vHolder = this.internalRep.get(attribute);
		int ticket = vHolder.generateTicket();
		if(ticket==-1){
			throw new RBInvalidValueException(
					"The maximum count of values for "
					+ attribute + " is reached, which is " + vHolder.getMetaData(MetaDataKeys.MAX));
		}
		return ticket;
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public void releaseTicketFor(final String attribute, final int ticket) throws RBInvalidValueException,RBInvalidAttributeException {
		if((!containsAttribute(attribute))){
			throw new RBInvalidAttributeException("The attribute " + attribute + " is not defined");
		}
		ValueHolder vHolder = this.internalRep.get(attribute);
		if(!vHolder.removeTicket(ticket)){
			//TODO: Analyze the specific cases
			throw new RBInvalidValueException(
					"The ticket does not exists or the minimum count of values for "
					+ attribute + " is reached, which is " + vHolder.getMetaData(MetaDataKeys.MIN));
		}
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public Integer addValueFor(final String attribute,  final Object val) throws RBInvalidValueException, RBInvalidAttributeException {
		Object value = val;
		if(value==null){
			value="";
		}
		RBValidator<Object> validator = getValidatorFor(attribute);
		if((!containsAttribute(attribute)) || validator==null){
			throw new RBInvalidAttributeException(
					"The attribute " + attribute + " is not defined or does not have an assigned validator");
		}

		ValueHolder vHolder = this.internalRep.get(attribute);
		int ticket = generateTicketFor(attribute);
		try{
			validator.isValid(value);
		}catch(RBInvalidValueException exe){
			throw exe;
		}

		//Set the value
		if(!vHolder.setValue(value, ticket)){
			throw new RBInvalidValueException("The index " + ticket + " does not exists for attribute");
		}
		return ticket;
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public void addValueFor(final String attribute, final Object val,final int ticket)
		throws RBInvalidValueException, RBInvalidAttributeException {
		Object value = val;
		if(value==null){
			value="";
		}
		RBValidator<Object> validator = getValidatorFor(attribute);

		if((!containsAttribute(attribute)) || validator==null){
			throw new RBInvalidAttributeException(
					"The attribute " + attribute + " is not defined or does not have an assigned validator");
		}

		ValueHolder vHolder = this.internalRep.get(attribute);
		try{
			validator.isValid(value);
		}catch(RBInvalidValueException exe){
			throw exe;
		}

		//Set the value
		if(!vHolder.setValue(value, ticket)){
			throw new RBInvalidValueException("The index " + ticket + " does not exists for attribute");
		}
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public Object getMetaInfoFor(final String attribute, final MetaDataKeys key) {
		if(!containsAttribute(attribute)){ return null;}
		return this.internalRep.get(attribute).getMetaData(key);
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public Collection<Integer> getTicketsFor(final String attribute) {
		if(!containsAttribute(attribute)){ return null;}
		//Clone this collection to prevent external modification on the internal tickets-set
		return new ArrayList<Integer>(this.internalRep.get(attribute).getTickets());
	}


	// -----------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public RBValidator<Object> getValidatorFor(final String attribute) {
		return this.internalValidatorMap.get(attribute);
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public Object getValueFor(final String attribute, final int index) {
		return internalRep.get(attribute).getValue(index);
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public Collection<Object> getValuesFor(final String attribute) {
		if(internalRep.get(attribute)!=null){
			Collection<Object> values = internalRep.get(attribute).getValues();
			if(values!=null && values.size()!=0){ return values;}
		}
		return new ArrayList<Object>();
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public Collection<String> getAttributesNamesForSimple(final String simpleAttribute) {
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


	/** {@inheritDoc} */
	@Override
	public String getSimpleAttributeName(final String attribute) {
		return simpleAttributeNames.get(attribute);
	}

	// -----------------------------------------------------

	/**
	 * <p>
	 * 	This method does some initializing-stuff
	 * 	and might generate/fill values and tickets if this instance does already exists.
	 * </p>
	 */
	private void init(){
		Collection<PropertyAssertion> assertions = schema.getPropertyAssertions();
		for (final PropertyAssertion propertyAssertion : assertions) {
			final PropertyDeclaration pDec = propertyAssertion.getPropertyDeclaration();
			//Set up the validator for this property declaration
			//In this version, "Constraints" can be ignored
			this.internalValidatorMap.put(
					propertyAssertion.getPropertyDescriptor().getQualifiedName().toURI(), new RBValidator<Object>(){
				@Override
				public boolean isValid(final Object value)
						throws RBInvalidValueException {
					String valueTmp = value.toString().toLowerCase();
					//Try to trigger an exception and catch them finally
					try{
						switch(pDec.getElementaryDataType()){
							case RESOURCE : if(! (value instanceof ResourceID)){
								throw new Exception("");
								}
							break;
							//Check for boolean
							case BOOLEAN : if(!(valueTmp.equals("0")
									||valueTmp.equals("1")
									|| valueTmp.equals("true")
									|| valueTmp.equals("false"))){
								throw new Exception("");
							}
							break;
							case INTEGER: Integer.parseInt(valueTmp); break;
							case DECIMAL: Double.parseDouble(valueTmp); break;
							case DATE : Date.parse(value.toString()); break;
							default: break;
						}
					}catch(Exception any){
						throw new RBInvalidValueException(
								"\""+value
								+"\" is not a valid value for the expected type "
								+pDec.getElementaryDataType());
					}
					return true;
				}
			});
			//Validator has been added
			simpleAttributeNames.put(
					propertyAssertion.getPropertyDescriptor().getQualifiedName().toURI(),
					propertyAssertion.getPropertyDescriptor().getName());
			ValueHolder vHolder = new ValueHolder(
					getAssociationClients(propertyAssertion.getPropertyDescriptor()),
					propertyAssertion);
			internalRep.put(propertyAssertion.getPropertyDescriptor().getQualifiedName().toURI(),vHolder);
		}//End of for
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public ResourceSchema getResourceSchema() {
		return this.schema;
	}

	// -----------------------------------------------------

	/** {@inheritDoc} */
	@Override
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

		/**
		 *
		 * @param set -
		 * @param assertion -
		 */
		public ValueHolder(final Set<SemanticNode> set, final PropertyAssertion assertion){
			this.assertion = assertion;
			init(set);
		}

		// -----------------------------------------------------

		/**
		 * @param key the type of the MetaData you're asking for
		 * @return the MetaData as Object
		 */
		public Object getMetaData(final MetaDataKeys key) {
			switch(key){
			case MAX: return assertion.getCardinality().getMaxOccurs();
			case CURRENT: return tickets.size();
			case MIN: return assertion.getCardinality().getMinOccurs();
			case TYPE: return assertion.getPropertyDeclaration().getElementaryDataType();
			default: return null;
			}
		}

		// -----------------------------------------------------

		/**
		 * @return the set of existing tickets
		 */
		public Collection<Integer> getTickets(){
			return tickets;
		}

		// -----------------------------------------------------

		/**
		 * @return all the assigned values
		 */
		public Collection<Object> getValues(){
			ArrayList<Object> output = new ArrayList<Object>();
			for (Integer ticket : tickets) {
				output.add(values.get(ticket));
			}
			return output;
		}

		// -----------------------------------------------------

		/**
		 * <p>
		 * Set the value for a given ticket.
		 * </p>
		 * @param value -
		 * @param ticket -
		 * @return true is the value could be addes successfully, false if not
		 */
		public boolean setValue(final Object value, final int ticket){
			//Check if the ticket does exists
			if(!tickets.contains(ticket)){
				return false;
			}
			values.put(ticket, value);
			return true;
		}

		// -----------------------------------------------------

		/**
		 * @param ticket - the ticket you're asking for
		 * @return the Value for the given ticket
		 */
		public Object getValue(final int ticket){
			//Check if the ticket does exists
			if(!tickets.contains(ticket)){
				return null;
			}
			return values.get(ticket);
		}

		// -----------------------------------------------------

		/**
		 * @return the new generated ticket as integer
		 */
		public int generateTicket(){
			//If a ticket couldnt not be created
			int ticket =-1;
			if(tickets.size()< assertion.getCardinality().getMaxOccurs()){
				ticket = (ticketcnt++);
				tickets.add(ticket);
				//Set an initial value to null:
				setValue(null, ticket);
			}
			return ticket;
		}

		// -----------------------------------------------------

		/**
		 * @param ticket - the ticket you want to remove
		 * @return true if the ticket could be removed, false if not
		 */
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

		/**
		 * <p>
		 * Initalizes the ValueHolder for a given set of {@link SematicNode}'s
		 * and generating tickets.
		 * </p>
		 * @param set - the {@link Set} of {@link SemanticNode}'s
		 */
		private void init(final Set<SemanticNode> set){
			if(set==null || set.size()==0){
				return;
			}
			for (SemanticNode semanticNode : set) {
				ValueNode vNode = semanticNode.asValue();
				int ticket_id = generateTicket();
				setValue(vNode.getStringValue(), ticket_id);
			}
		}
	}

	//--SANTA's LITTLE HELPER----------------------------------

	/**
	 * <p>
	 * Checks if the attribute is contained.
	 * </p>
	 * @param attribute -
	 * @return true if the specified attribute is contained, false if not
	 */
	private boolean containsAttribute(final String attribute){
		return internalRep.containsKey(attribute);
	}


}

