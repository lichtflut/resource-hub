/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import junit.framework.TestCase;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;


/**
 * <p>
 *  Some tests to proof and specify the behavior of {@link ConstaintFactory}.
 * </p>
 *
 *  <p>
 * 	 Created Apr 12, 2011
 *  </p>
 *
 * @author Nils Bleisch
 *
 */
public class ConstraintBuilderTest extends TestCase{

	/**
	 * <p>
	 * This test checks the possibility of raised {@link NullPointerException} during wrong buildConstraint()-calls.
     * </p>
	 */
	@SuppressWarnings("static-access")
   public void testNullPointerExceptionInBuildingConstraints(){
		boolean exceptionIsOccured=false;
		try{
		  String a =null;
		  ConstraintBuilder.buildLiteralConstraint(a);
		}catch(NullPointerException exe){
			exceptionIsOccured=true;
		}
		assertEquals(false,exceptionIsOccured);

		//For the following calls, no exception should be raised
		String a = null;
		ConstraintBuilder.buildLiteralConstraint(a);
		ConstraintBuilder.buildLiteralConstraint(new String());
		ResourceID id = null;
		ConstraintBuilder.buildResourceConstraint(id);
   }

  //-------------------------------//

  /**
   * TODO:.
   */
  @SuppressWarnings("static-access")
  public void testBuildResourceTypeConstraint(){
	   ResourceID id = null;
	   Constraint c = ConstraintBuilder.buildResourceConstraint(id);
	   assertNotNull(c);
	   assertFalse(c.isResourceReference());
	   assertNull(c.getLiteralConstraint());
	   assertNull(c.getResourceTypeConstraint());

	   id = new SimpleResourceID(
				"NAMESPACE",
				"IDENTIFIER");

	   c = ConstraintBuilder.buildResourceConstraint(id);
	   assertNotNull(c);
	   assertFalse(!c.isResourceReference());
	   assertTrue(c.isResourceReference());
	   assertNull(c.getLiteralConstraint());
	   assertEquals(c.getResourceTypeConstraint(), id);

  }

}
