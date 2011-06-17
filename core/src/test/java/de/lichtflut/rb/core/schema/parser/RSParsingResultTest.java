/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import junit.framework.TestCase;

import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.parser.impl.RSParsingResultImpl;

/**
 * <p>
 *  Some tests to proof and specify the RSParsingResult.
 * </p>
 *
 *  <p>
 * 	 Created Apr. 26, 2011
 *  </p>
 *
 * @author Nils Bleisch
 *
 */
public class RSParsingResultTest extends TestCase{

	/**
	 *
	 */
	public void testParsingAndConstructing(){

		//Some declaration stuff
		final String[] cERRORMESSAGES = new String[]{
				"TEST ERROR MESSAGE 1",
				"TEST ERROR MESSAGE 2",
				"TEST ERROR MESSAGE 3",
				"TEST ERROR MESSAGE 4",
				};

		RSParsingResultImpl pResult1 = new RSParsingResultImpl();
		//Let's proof the errorHandling:
		//==================================
		//add a errorMessage for the default ErrorLevel which should be ALL
		pResult1.addErrorMessage(cERRORMESSAGES[0]);
		//check the level, it's logged for all, so system must be allowed
		assertEquals(cERRORMESSAGES[0], pResult1.getErrorMessagesAsString("",RSErrorLevel.SYSTEM));
		assertEquals(cERRORMESSAGES[0], pResult1.getErrorMessagesAsString("",RSErrorLevel.ALL));
		assertEquals(cERRORMESSAGES[0] + "\n", pResult1.getErrorMessagesAsString());
		assertTrue(pResult1.getErrorMessages().contains(cERRORMESSAGES[0]));
		//Set the ErrorLevel to System
		pResult1.setErrorLevel(RSErrorLevel.SYSTEM);
		//Add a Message
		pResult1.addErrorMessage(cERRORMESSAGES[1]);
		assertTrue(pResult1.getErrorMessagesAsString("",RSErrorLevel.SYSTEM).contains(cERRORMESSAGES[1]));
		assertTrue(pResult1.getErrorMessagesAsString("",RSErrorLevel.ALL).contains(cERRORMESSAGES[1]));
		assertFalse(pResult1.getErrorMessagesAsString("",RSErrorLevel.INTERPRETER).contains(cERRORMESSAGES[1]));

		//Define and add ResourceSchema to pResult1
		ResourceSchemaImpl rSchema = new ResourceSchemaImpl();
		PropertyDeclaration pDec = new PropertyDeclarationImpl("http://lichtflut.de#testProperty");
		rSchema.addPropertyAssertion(new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de","hasTestProp"), pDec));
		pResult1.addResourceSchema(new ResourceSchemaImpl());
		assertTrue(pResult1.getResourceSchemasIgnoreErrors().size()==1);
		assertTrue(pResult1.getPropertyDeclarationsIgnoreErrors().size()==0);
		assertTrue(pResult1.getErrorMessages().size()==2);

		RSParsingResultImpl pResult2 = new RSParsingResultImpl();
		//Set the ErrorLevel for Interpreter and Grammar
		pResult2.setErrorLevel(RSErrorLevel.INTERPRETER.add(RSErrorLevel.GRAMMAR));
		pResult2.addErrorMessage(cERRORMESSAGES[2]);
		pResult2.addErrorMessage(cERRORMESSAGES[(2 + 1)]);

		pResult2.addPropertyDeclaration(pDec);

		assertTrue(pResult2.getResourceSchemasIgnoreErrors().size()==0);
		assertTrue(pResult2.getPropertyDeclarationsIgnoreErrors().size()==1);
		assertTrue(pResult2.getErrorMessages().size()==2);
		assertTrue(pResult2.getErrorMessages(RSErrorLevel.INTERPRETER).size()==2);
		assertTrue(pResult2.getErrorMessages(RSErrorLevel.GRAMMAR).size()==2);
		assertTrue(pResult2.getErrorMessages(RSErrorLevel.SYSTEM).size()==0);

		/**
		 * Status Quo:
		 * pResult1 -> 1 Message for All, 1 Message for System, 1 ResourceSchema with assigned PDec
		 * pResult2 -> 2 Messages for Grammar and System, 1 PDec (Same as in pResult1)
		 * Next task is to make sure what must happen after merge
		 */

		pResult1.merge(pResult2);

		//Let's check the errormessages
		assertTrue(pResult1.getErrorMessages(RSErrorLevel.ALL).size()==(2+2));
		assertTrue(pResult1.getErrorMessages(RSErrorLevel.SYSTEM).size()==(1 + 1));
		assertTrue(pResult1.getErrorMessages(RSErrorLevel.GRAMMAR).size()==(2 + 1));
		assertTrue(pResult1.getErrorMessages(RSErrorLevel.INTERPRETER).size()==(2 + 1));
		//Let's check the ResourceSchemaModel
		assertTrue(pResult1.getResourceSchemasIgnoreErrors().size()==1);
		assertTrue(pResult1.getPropertyDeclarationsIgnoreErrors().size()==1);
		//And last but not least, make sure, that the pDec should not occur, when it's is already assigned to a ResourceSchema
		assertTrue(pResult1.getPropertyDeclarationsWithoutResourceAssocIgnoreErrors().size()==0);


	}
}
