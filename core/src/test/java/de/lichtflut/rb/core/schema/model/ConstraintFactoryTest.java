/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.util.Collection;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Test;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import junit.framework.TestCase;


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
public class ConstraintFactoryTest extends TestCase{
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
		ConstraintFactory factory = ConstraintFactory.getInstance();
		assertSame("getInstance() should deliver allways the same instance",
				factory,ConstraintFactory.getInstance());
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
		ConstraintFactory f = ConstraintFactory.getInstance();
		boolean exceptionIsOccured=false;
		try{
		  String[] a =null;
		  f.buildConstraint(a);
		}catch(NullPointerException exe){
			exceptionIsOccured=true;
		}
		assertEquals(true,exceptionIsOccured);

		exceptionIsOccured=false;
		try{
		  Collection<String> a =null;
		  f.buildConstraint(a);
		}catch(NullPointerException exe){
			exceptionIsOccured=true;
		}
		assertEquals(true,exceptionIsOccured);

		//For the following calls, no exception should be raised
		String a = null;
		f.buildConstraint(a);
		f.buildConstraint(new String[]{a});
		f.buildConstraint(new String[]{});
		ResourceID id = null;
		f.buildConstraint(id);
   }

  //-------------------------------//

/**
 * TODO:.
 */
  @SuppressWarnings("static-access")
   public void testBuildPatternConstraint(){
	   ConstraintFactory f = ConstraintFactory.getInstance();
	   String[] patterns = new String[]{"Pattern1", "Pattern2", null};
	   //check some constraints based on patterns-array

	   //1) verify that a null value is interpreted as blank ""
	   Constraint c = f.buildConstraint(patterns[2]);
	   assertNotNull(c);
	   assertTrue(c.isLiteralConstraint());
	   assertFalse(c.isResourceTypeConstraint());
	   assertEquals(c.getLiteralConstraint(),"");

	   //2) verify that a non null value pattern as parameter is equal compared to the value from getLiteralConstraint
	   c = f.buildConstraint(patterns[0]);
	   assertNotNull(c);
	   assertTrue(c.isLiteralConstraint());
	   assertFalse(c.isResourceTypeConstraint());
	   assertEquals(c.getLiteralConstraint(),patterns[0]);

	   //3) verify that a collection of patterns is concatenated to one whole pattern string
	   c = f.buildConstraint(patterns);
	   assertNotNull(c);
	   assertTrue(c.isLiteralConstraint());
	   assertFalse(c.isResourceTypeConstraint());
	   assertEquals(c.getLiteralConstraint(),patterns[0] + patterns[1] + "");

   }

  //-------------------------------//

  /**
   * TODO:.
   */
  @SuppressWarnings("static-access")
  public void testBuildResourceTypeConstraint(){
	   ConstraintFactory f = ConstraintFactory.getInstance();
	   ResourceID id = null;
	   Constraint c = f.buildConstraint(id);
	   assertNotNull(c);
	   assertFalse(c.isLiteralConstraint());
	   assertFalse(c.isResourceTypeConstraint());
	   assertNull(c.getLiteralConstraint());
	   assertNull(c.getResourceTypeConstraint());

	   id = new SimpleResourceID(
				"NAMESPACE",
				"IDENTIFIER");

	   c = f.buildConstraint(id);
	   assertNotNull(c);
	   assertFalse(c.isLiteralConstraint());
	   assertTrue(c.isResourceTypeConstraint());
	   assertNull(c.getLiteralConstraint());
	   assertEquals(c.getResourceTypeConstraint(), id);

  }

}
