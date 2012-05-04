/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import junit.framework.TestCase;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.impl.OldConstraintBuilder;


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
	 * This test makes some assertion about the general nature of ConstraintFactory.
	 *    <ul>
     *    	 <li> the one and only instance of ConstraintFactory is given by the class itself as singleton
     *    </ul>
     * </p>
	 */
	@SuppressWarnings("static-access")
	@Test (expected=IllegalArgumentException.class)
	public void testFactoryInCommon(){
		//Let's proof that singleton pattern of CardinalityFactory is still working
		OldConstraintBuilder factory = OldConstraintBuilder.getInstance();
		assertSame("getInstance() should deliver allways the same instance",
				factory,OldConstraintBuilder.getInstance());
		assertSame("getInstance() should deliver allways the same instance",
				factory,factory.getInstance());

	}//End of method testFactoryInCommon()

   //-------------------------------//

	/**
	 * <p>
	 * This test checks the possibility of raised {@link NullPointerException} during wrong buildConstraint()-calls.
     * </p>
	 */
	@SuppressWarnings("static-access")
   public void testNullPointerExceptionInBuildingConstraints(){
		OldConstraintBuilder f = OldConstraintBuilder.getInstance();
		boolean exceptionIsOccured=false;
		try{
		  String a =null;
		  f.buildLiteralConstraint(a);
		}catch(NullPointerException exe){
			exceptionIsOccured=true;
		}
		assertEquals(false,exceptionIsOccured);

		//For the following calls, no exception should be raised
		String a = null;
		f.buildLiteralConstraint(a);
		f.buildLiteralConstraint(new String());
		ResourceID id = null;
		f.buildResourceConstraint(id);
   }

  //-------------------------------//

  /**
   * TODO:.
   */
  @SuppressWarnings("static-access")
  public void testBuildResourceTypeConstraint(){
	   OldConstraintBuilder f = OldConstraintBuilder.getInstance();
	   ResourceID id = null;
	   Constraint c = f.buildResourceConstraint(id);
	   assertNotNull(c);
	   assertFalse(c.isResourceTypeConstraint());
	   assertNull(c.getLiteralConstraint());
	   assertNull(c.getResourceTypeConstraint());

	   id = new SimpleResourceID(
				"NAMESPACE",
				"IDENTIFIER");

	   c = f.buildResourceConstraint(id);
	   assertNotNull(c);
	   assertFalse(!c.isResourceTypeConstraint());
	   assertTrue(c.isResourceTypeConstraint());
	   assertNull(c.getLiteralConstraint());
	   assertEquals(c.getResourceTypeConstraint(), id);

  }

}
