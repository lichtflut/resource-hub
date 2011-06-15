/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.lichtflut.rb.core.rbentity.persistence.RBEntityTest;
import de.lichtflut.rb.core.schema.persistence.OSFPersistenceTest;

/**
 * <p>
 *  [DESCRIPTION].
 * </p>
 *
 * <p>
 * 	Created Jun 14, 2011
 * </p>
 *
 * @author Oliver Tigges
 */

@RunWith(Suite.class)
@SuiteClasses({RBEntityTest.class, OSFPersistenceTest.class})
public class RBCoreTestSuite {

}
