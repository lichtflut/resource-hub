/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import org.junit.Test;

import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import junit.framework.TestCase;


/**
 * <p>
 *  Some tests to proof and specify the behavior of {@link CardinalityBuilder}.
 * </p>
 *
 *  <p>
 * 	 Created Jan 21, 2011
 *  </p>
 *
 * @author Nils Bleisch
 *
 */
public class CardinalityFactoryTest extends TestCase{
	/**
	 * <p>
	 * This test makes some assertion about the general nature of CardinalityFactory.
	 *    <ul>
     *    	 <li> the one and only instance of CardinalityFactory is given by the class itself as singleton
     *    	 <li> Possibility of raised IllegalArgumentException during wrong getAbsoluteCardinality-parameter calls
     *     </ul>
     * </p>
	 */
	@SuppressWarnings("static-access")
	@Test (expected=IllegalArgumentException.class)
	public void testFactoryInCommon(){
		//Let's proof that singleton pattern of CardinalityFactory is still working
		CardinalityBuilder factory = CardinalityBuilder.getInstance();
		assertSame("getInstance() should deliver allways the same instance",
				factory,CardinalityBuilder.getInstance());
		assertSame("getInstance() should deliver allways the same instance",
				factory,factory.getInstance());

		//An exception should be raised when max is smaller than min

		boolean exceptionIsOccured=false;
		try{
		  final Integer min = -3;
		  factory.getAbsoluteCardinality(6, -1);
		}catch(IllegalArgumentException exe){
			exceptionIsOccured=true;
		}
		assertEquals(true,exceptionIsOccured);

	}//End of method testFactoryInCommon()

   //-------------------------------//

	/**
	 *
	 */
  @SuppressWarnings("static-access")
   public void testBuildSingleCardinalities(){
	   CardinalityBuilder f = CardinalityBuilder.getInstance();
	   Cardinality c = f.hasExcactlyOne();
	   assertEquals(true, c.isSingle());
	   assertEquals(false, c.isUnbound());
	   assertEquals(1,c.getMaxOccurs());
	   assertEquals(1,c.getMinOccurs());

	   c = f.hasOptionalOne();
	   assertEquals(true, c.isSingle());
	   assertEquals(false, c.isUnbound());
	   assertEquals(1,c.getMaxOccurs());
	   assertEquals(0,c.getMinOccurs());
	   final int aMount = 5;
	   int amount_current = aMount;
	   c = f.hasExactly(amount_current);
	   assertEquals(false, c.isSingle());
	   assertEquals(false, c.isUnbound());
	   assertEquals(amount_current,c.getMaxOccurs());
	   assertEquals(amount_current,c.getMinOccurs());

	   amount_current = - aMount;
	   c = f.hasExactly(amount_current);
	   assertEquals(false, c.isSingle());
	   assertEquals(false, c.isUnbound());
	   assertEquals(Math.abs(amount_current),c.getMaxOccurs());
	   assertEquals(Math.abs(amount_current),c.getMinOccurs());
   }
  //-------------------------------//

  /**
   *
   */
  @SuppressWarnings("static-access")
  public void testBuildHABTMCardinalities(){
	   CardinalityBuilder f = CardinalityBuilder.getInstance();
	   Cardinality c = f.hasAtLeastOneToMany();
	   assertEquals(false, c.isSingle());
	   assertEquals(true, c.isUnbound());
	   assertEquals(Integer.MAX_VALUE,c.getMaxOccurs());
	   assertEquals(1,c.getMinOccurs());

	   c = f.hasOptionalOneToMany();
	   assertEquals(false, c.isSingle());
	   assertEquals(true, c.isUnbound());
	   assertEquals(Integer.MAX_VALUE,c.getMaxOccurs());
	   assertEquals(0,c.getMinOccurs());

	   final int aMount = 5;
	   int amount_current = aMount;
	   c = f.hasOptionalOneUpTo(amount_current);
	   assertEquals(false, c.isSingle());
	   assertEquals(false, c.isUnbound());
	   assertEquals(Math.abs(amount_current),c.getMaxOccurs());
	   assertEquals(0,c.getMinOccurs());

	   c = f.hasAtLeastOneUpTo(amount_current);
	   assertEquals(false, c.isSingle());
	   assertEquals(false, c.isUnbound());
	   assertEquals(Math.abs(amount_current),c.getMaxOccurs());
	   assertEquals(1,c.getMinOccurs());

  }

}
