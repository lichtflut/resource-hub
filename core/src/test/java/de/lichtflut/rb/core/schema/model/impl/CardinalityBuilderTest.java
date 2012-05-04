/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.lichtflut.rb.core.schema.model.Cardinality;

/**
 * <p>
 * Some tests to proof and specify the behavior of {@link CardinalityBuilder}.
 * </p>
 * 
 * <p>
 * Created Jan 21, 2011
 * </p>
 * 
 * @author Nils Bleisch
 */
public class CardinalityBuilderTest {

	private Cardinality cardinality;
	private int max = 50;
	private int min = 50;


	@Test
	public void testHasExcactlyOne() {
		cardinality = CardinalityBuilder.hasExcactlyOne();
		assertTrue("Cardinality has more than one", cardinality.getMaxOccurs() == 1);
		assertTrue("Cardinality has less than one", cardinality.getMinOccurs() == 1);
		assertFalse("Cardinality should not be unbound", cardinality.isUnbound());
	}

	@Test
	public void testHasOptionalOne() {
		cardinality = CardinalityBuilder.hasOptionalOne();
		assertTrue("Cardinality has more than one", cardinality.getMaxOccurs() == 1);
		assertTrue("Cardinality min is not zero", cardinality.getMinOccurs() == 0);
		assertFalse("Cardinality should not be unbound", cardinality.isUnbound());
	}

	@Test
	public void testHasAtLeastOneToMany() {
		cardinality = CardinalityBuilder.hasAtLeastOneToMany();
		assertTrue("Cardinality is not unbound", cardinality.getMaxOccurs() == Integer.MAX_VALUE);
		assertTrue("Cardinality min is not one", cardinality.getMinOccurs() == 1);
		assertTrue("Cardinality should be unbound", cardinality.isUnbound());
	}

	@Test
	public void testHasAtLeast() {
		cardinality = CardinalityBuilder.hasAtLeast(min);
		assertTrue("Cardinality is not unbound", cardinality.getMaxOccurs() == Integer.MAX_VALUE);
		assertTrue("Cardinality min is not as expected", cardinality.getMinOccurs() == min);
		assertTrue("Cardinality should be unbound", cardinality.isUnbound());
	}

	@Test
	public void testHasOptionalOneToMany() {
		cardinality = CardinalityBuilder.hasOptionalOneToMany();
		assertTrue("Cardinality is not unbound", cardinality.getMaxOccurs() == Integer.MAX_VALUE);
		assertTrue("Cardinality min is not as expected", cardinality.getMinOccurs() == 0);
		assertTrue("Cardinality should be unbound", cardinality.isUnbound());
	}

	@Test
	public void testHasAtLeastOneUpTo() {
		cardinality = CardinalityBuilder.hasAtLeastOneUpTo(max);
		assertTrue("Cardinality max is not as expected", cardinality.getMaxOccurs() == max);
		assertTrue("Cardinality min is not as expected", cardinality.getMinOccurs() == 1);
		assertFalse("Cardinality should not be unbound", cardinality.isUnbound());
	}

	@Test
	public void testHasOptionalOneUpTo() {
		cardinality = CardinalityBuilder.hasOptionalOneUpTo(max);
		assertTrue("Cardinality max is not as expected", cardinality.getMaxOccurs() == max);
		assertTrue("Cardinality min is not as expected", cardinality.getMinOccurs() == 0);
		assertFalse("Cardinality should not be unbound", cardinality.isUnbound());
	}

	@Test
	public void testHasExactly() {
		cardinality = CardinalityBuilder.hasExactly(max);
		assertTrue("Cardinality max is not as expected", cardinality.getMaxOccurs() == max);
		assertTrue("Cardinality min is not as expected", cardinality.getMinOccurs() == max);
		assertFalse("Cardinality should not be unbound", cardinality.isUnbound());
	}

	@Test
	public void testBetween() {
		cardinality = CardinalityBuilder.between(min, max);
		assertTrue("Cardinality max is not as expected", cardinality.getMaxOccurs() == max);
		assertTrue("Cardinality min is not as expected", cardinality.getMinOccurs() == min);
		assertFalse("Cardinality should not be unbound", cardinality.isUnbound());
	}
	
	@Test
	public void testBuildSingleCardinalities() {
		Cardinality c = CardinalityBuilder.hasExcactlyOne();
		assertEquals(false, c.isUnbound());
		assertEquals(1, c.getMaxOccurs());
		assertEquals(1, c.getMinOccurs());

		c = CardinalityBuilder.hasOptionalOne();
		assertEquals(false, c.isUnbound());
		assertEquals(1, c.getMaxOccurs());
		assertEquals(0, c.getMinOccurs());
		final int aMount = 5;
		int amount_current = aMount;
		c = CardinalityBuilder.hasExactly(amount_current);
		assertEquals(false, c.isUnbound());
		assertEquals(amount_current, c.getMaxOccurs());
		assertEquals(amount_current, c.getMinOccurs());

		amount_current = -aMount;
		c = CardinalityBuilder.hasExactly(amount_current);
		assertEquals(false, c.isUnbound());
		assertEquals(Math.abs(amount_current), c.getMaxOccurs());
		assertEquals(Math.abs(amount_current), c.getMinOccurs());
	}
	
	@Test
	public void testBuildHABTMCardinalities() {
		Cardinality c = CardinalityBuilder.hasAtLeastOneToMany();
		assertEquals(true, c.isUnbound());
		assertEquals(Integer.MAX_VALUE, c.getMaxOccurs());
		assertEquals(1, c.getMinOccurs());

		c = CardinalityBuilder.hasOptionalOneToMany();
		assertEquals(true, c.isUnbound());
		assertEquals(Integer.MAX_VALUE, c.getMaxOccurs());
		assertEquals(0, c.getMinOccurs());

		final int aMount = 5;
		int amount_current = aMount;
		c = CardinalityBuilder.hasOptionalOneUpTo(amount_current);
		assertEquals(false, c.isUnbound());
		assertEquals(Math.abs(amount_current), c.getMaxOccurs());
		assertEquals(0, c.getMinOccurs());

		c = CardinalityBuilder.hasAtLeastOneUpTo(amount_current);
		assertEquals(false, c.isUnbound());
		assertEquals(Math.abs(amount_current), c.getMaxOccurs());
		assertEquals(1, c.getMinOccurs());

	}

}
