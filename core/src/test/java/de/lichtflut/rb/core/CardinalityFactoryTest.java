/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core;

import de.lichtflut.rb.core.schema.impl.CardinalityFactory;
import junit.framework.TestCase;


/**
 * Some tests to proof the behavior of {@link CardinalityFactory}.
 */
public class CardinalityFactoryTest extends TestCase
{
	public void testFactoryInCommon(){
		//Lets proof that singleton pattersn of CardinalityFactory is still working
		CardinalityFactory factory = CardinalityFactory.getInstance();
		assertSame(factory,CardinalityFactory.getInstance());
		assertSame(factory,factory.getInstance());	
	}
}
