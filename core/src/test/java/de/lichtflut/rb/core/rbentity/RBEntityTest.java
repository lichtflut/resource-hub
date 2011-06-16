/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.rbentity;

import junit.framework.Assert;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.schema.model.RBEntityFactory;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityFactory;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;


/**
 * <p>
 * Testcase to test ResourceTypeInstance- validators, ticket-algorithms and constraints.
 * </p>
 *
 * Created: May 20, 2011
 *
 * @author Nils Bleisch
 */
public class RBEntityTest {

	/**
	 * @param
	 */
	public final void testResourceTypeInstance(){
		//Generate an instance for a given schema
		RBEntityFactory<Object> instance = createSchema().generateRBEntity();
		Assert.assertNotNull(instance);

		//Generate some tickets for fields

		int ticket_hatGeburtstag1 =-1,
			ticket_hatGeburtstag2 =-1, //hatGeburtstag is a hasExacltyOne Property
			ticket_unknownAttribute =-1, //This attribute does not exists
			ticket_hatEmail1 = -1,
			ticket_hatEmail2 = -1,
			ticket_hatAlter = -1;

		try {
			ticket_hatGeburtstag1 = instance.generateTicketFor("http://lichtflut.de#hatGeburtstag");
			ticket_hatEmail1 = instance.generateTicketFor("http://lichtflut.de#hatEmail");
			ticket_hatEmail2 = instance.generateTicketFor("http://lichtflut.de#hatEmail");
			ticket_hatAlter = instance.generateTicketFor("http://lichtflut.de#hatAlter");
			//Try to generate a second ticket for 'Geburtstag' which should not be possible an trigger an exception
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertTrue(ticket_hatGeburtstag2 == -1 && ticket_unknownAttribute == -1);
		Assert.assertTrue(ticket_hatGeburtstag1 != -1 && ticket_hatEmail1 != -1 && ticket_hatEmail2 != -1 && ticket_hatAlter != -1);
		//Try to write an value to hatGeburtstag with the given ticket.
		boolean exceptionOccured=false;
		try {
			instance.addValueFor("http://lichtflut.de#hatGeburtstag", "test", ticket_hatGeburtstag1);
			Assert.assertTrue((instance.getValueFor("http://lichtflut.de#hatGeburtstag",
					ticket_hatGeburtstag1)).equals("test"));
			//Try to revoke the ticket, which should not be possible caused by the hasExacltyOneCardinality
			exceptionOccured=true;
			instance.releaseTicketFor("http://lichtflut.de#hatGeburtstag", ticket_hatGeburtstag1);
			exceptionOccured=false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertTrue(exceptionOccured);
		exceptionOccured=false;

		int ticket_hatEmail3 = -1;

		try{
			/*Try to generate a new Ticket for hatEmail, which should not be possible,
			 * because there are already two existing ones, which is the maximum
			 */
			ticket_hatEmail3 = instance.generateTicketFor("http://lichtflut.de#hatEmail");
		}catch(Exception any){
			any.printStackTrace();
		}
		//ticket_hatEmail3 must be still on it's intial value '-1'
		Assert.assertTrue(ticket_hatEmail3==-1);


		try{
			exceptionOccured=true;
			instance.addValueFor("http://lichtflut.de#hatEmail", "email@1", ticket_hatEmail1);
			instance.addValueFor("http://lichtflut.de#hatEmail", "email@2", ticket_hatEmail2);
			Assert.assertTrue((instance.getValueFor("http://lichtflut.de#hatEmail", ticket_hatEmail1)).equals("email@1"));
			Assert.assertTrue((instance.getValueFor("http://lichtflut.de#hatEmail", ticket_hatEmail2)).equals("email@2"));
			instance.releaseTicketFor("http://lichtflut.de#hatEmail", ticket_hatEmail1);
			//Generate hatEmail3-ticket
			ticket_hatEmail3 = instance.generateTicketFor("http://lichtflut.de#hatEmail");
			//Check for ticket1, which should not exists anymore
			Assert.assertNull(instance.getValueFor("http://lichtflut.de#hatEmail", ticket_hatEmail1));
			exceptionOccured=false;
		}catch(Exception any){
			any.printStackTrace();
		}
		Assert.assertFalse(exceptionOccured);
		exceptionOccured=false;

		try{
			Assert.assertNull((instance.getValueFor("http://lichtflut.de#hatEmail", ticket_hatEmail3)));
			instance.addValueFor("http://lichtflut.de#hatEmail", "email@3", ticket_hatEmail3);
			Assert.assertTrue((instance.getValueFor("http://lichtflut.de#hatEmail", ticket_hatEmail3)).equals("email@3"));
		}catch(Exception any){
			any.printStackTrace();
		}


		//Check the build-in validation stuff. hatAlter's Datatype is Integer, let's try to add an alphanumeric string

		String message="";
		try{
			instance.addValueFor("http://lichtflut.de#hatAlter", "abc0153", ticket_hatAlter);
		}catch(Exception any){
			message = any.getMessage();
		}
		Assert.assertTrue(message.toLowerCase().contains("is not a valid value for the expected type integer"));
		Assert.assertNull(instance.getValueFor("http://lichtflut.de#hatAlter", ticket_hatAlter));


	}



	/**
	 * @return shema
	 */
	private ResourceSchema createSchema(){
		ResourceSchemaImpl schema = new ResourceSchemaImpl("http://lichtflut.de#","personschema");
		PropertyDeclarationImpl p1 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p2 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p3 = new PropertyDeclarationImpl();
		p1.setName("http://lichtflut.de#geburtsdatum");
		p2.setName("http://lichtflut.de#email");
		p3.setName("http://lichtflut.de#alter");

		p1.setElementaryDataType(ElementaryDataType.STRING);
		p2.setElementaryDataType(ElementaryDataType.STRING);
		p3.setElementaryDataType(ElementaryDataType.INTEGER);

		p2.addConstraint(ConstraintFactory.buildConstraint(".*@.*"));
		PropertyAssertionImpl pa1 = new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de#","hatGeburtstag"), p1);
		PropertyAssertionImpl pa2 = new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de#","hatEmail"), p2);
		PropertyAssertionImpl pa3 = new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de#","hatAlter"), p3);
		pa1.setCardinality(CardinalityFactory.hasExcactlyOne());
		pa2.setCardinality(CardinalityFactory.hasAtLeastOneUpTo(2));
		pa3.setCardinality(CardinalityFactory.hasExcactlyOne());

		schema.addPropertyAssertion(pa1);
		schema.addPropertyAssertion(pa2);
		schema.addPropertyAssertion(pa3);

		return schema;
	}

}
