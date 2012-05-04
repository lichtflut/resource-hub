/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import static org.junit.Assert.*;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.Constraint;

/**
 * Testclass for {@link OldConstraintBuilder}.
 * <br>
 * Created: May 2, 2012
 *
 * @author Ravi Knox
 */
public class ConstraintBuilderTest {

	private String name = "Email";
	private String pattern = "*.@.*";
	private ResourceID resource = new SimpleResourceID("http://lichtflut.de/test#Person");
	
	@Test
	public void testBuildLiteralConstraint(){
		Constraint constraint = ConstraintBuilder.buildLiteralConstraint(pattern);
		
		assertNotNull(constraint);
		assertNull(constraint.getResourceTypeConstraint());
		assertEquals("Constraints are not equal", pattern, constraint.getLiteralConstraint());
		assertEquals("Constraintname is not as expected", pattern, constraint.getName());
	}
	
	@Test
	public void testPublicLiteralConstraint() {
		SimpleResourceID id = new SimpleResourceID();
		Constraint constraint = ConstraintBuilder.buildPublicLiteralConstraint(id, name, pattern);
		
		assertNotNull(constraint);
		assertNull(constraint.getResourceTypeConstraint());
		assertEquals("Constraints are not equal", pattern, constraint.getLiteralConstraint());
		assertEquals("Constraintname is not as expected", name, constraint.getName());
		assertEquals("ResourceID not as expected", id, constraint.getID());
	}
	
	@Test
	public void testResourcecConstraint() {
		Constraint constraint = ConstraintBuilder.buildResourceConstraint(resource);
		
		assertNotNull(constraint);
		assertNull(constraint.getLiteralConstraint());
		assertEquals("Constraints are not equal", resource.getQualifiedName(), constraint.getResourceTypeConstraint().getQualifiedName());
		assertEquals("Constraintname is not as expected", resource.toURI(), constraint.getName());
	}

}
