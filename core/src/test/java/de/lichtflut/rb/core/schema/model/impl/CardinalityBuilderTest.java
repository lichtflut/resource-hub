/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.schema.model.impl;

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
	public void testExtractFromStringHasExactlyOne() {
		String pattern = "[1..1]";
		Cardinality cardinality = CardinalityBuilder.extractFromString(pattern);
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
	public void testExtractFromStringOptionalOne() {
		String pattern = "[n..1]";
		Cardinality cardinality = CardinalityBuilder.extractFromString(pattern);
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
	public void testExtractFromStringOneToMany() {
		String pattern = "[1..n]";
		Cardinality cardinality = CardinalityBuilder.extractFromString(pattern);
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
	public void testExtractFromStringHasAtLeast() {
		String pattern = "["+min+"..n]";
		Cardinality cardinality = CardinalityBuilder.extractFromString(pattern);
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
	public void testExtractFromStringOptionalyOneToMany() {
		String pattern = "[n..n]";
		Cardinality cardinality = CardinalityBuilder.extractFromString(pattern);
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
	public void testExtractFromStringAtLeastOneToMany() {
		String pattern = "[1.."+max+"]";
		Cardinality cardinality = CardinalityBuilder.extractFromString(pattern);
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
	public void testExtractFromStringHasOptionalOneUpTo() {
		String pattern = "[n.."+max+"]";
		Cardinality cardinality = CardinalityBuilder.extractFromString(pattern);
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
	public void testExtractFromStringHasExactly() {
		String pattern = "["+max+".."+max+"]";
		Cardinality cardinality = CardinalityBuilder.extractFromString(pattern);
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
	public void testExtractFromStringBetween() {
		String pattern = "["+min+".."+max+"]";
		Cardinality cardinality = CardinalityBuilder.extractFromString(pattern);
		assertTrue("Cardinality max is not as expected", cardinality.getMaxOccurs() == max);
		assertTrue("Cardinality min is not as expected", cardinality.getMinOccurs() == min);
		assertFalse("Cardinality should not be unbound", cardinality.isUnbound());
	}
	
	@Test
	public void testExtractStringWithNonsense(){
		String pattern = "[34rew]";
		Cardinality cardinality = CardinalityBuilder.extractFromString(pattern);
		assertTrue("Cardinality is not unbound", cardinality.getMaxOccurs() == Integer.MAX_VALUE);
		assertTrue("Cardinality min is not as expected", cardinality.getMinOccurs() == 0);
		assertTrue("Cardinality should be unbound", cardinality.isUnbound());
	}
	
	@Test
	public void testExtractStringWithNull(){
		String pattern = null;
		Cardinality cardinality = CardinalityBuilder.extractFromString(pattern);
		assertTrue("Cardinality is not unbound", cardinality.getMaxOccurs() == Integer.MAX_VALUE);
		assertTrue("Cardinality min is not as expected", cardinality.getMinOccurs() == 0);
		assertTrue("Cardinality should be unbound", cardinality.isUnbound());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBetweenWithNegativNumber(){
		CardinalityBuilder.between(-1, 100);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBetweenWithMinGreaterThenMax(){
		CardinalityBuilder.between(500, 100);
	}
	
}
