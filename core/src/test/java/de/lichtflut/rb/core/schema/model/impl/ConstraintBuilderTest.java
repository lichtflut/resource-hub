/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;

/**
 * Testclass for {@link OldConstraintBuilder}. <br>
 * Created: May 2, 2012
 * 
 * @author Ravi Knox
 */
public class ConstraintBuilderTest {

	private String name = "Email";
	private String publicResConstrName = "Person";
	private String pattern = "*.@.*";
	private ResourceID resource = new SimpleResourceID("http://lichtflut.de/test#Person");

	@Test
	public void testBuildLiteralConstraint() {
		Constraint constraint = ConstraintBuilder.buildLiteralConstraint(pattern);

		assertNotNull("Constraint is null", constraint);
		assertNull("Id should be null", constraint.getID());
		assertNull(constraint.getResourceTypeConstraint());
		assertEquals("Constraints are not equal", pattern, constraint.getLiteralConstraint());
		assertEquals("Constraintname is not as expected", pattern, constraint.getName());
	}

	@Test
	public void testBuildPublicLiteralConstraintWithSingleDatatype() {
		SimpleResourceID id = new SimpleResourceID();
		Constraint constraint = ConstraintBuilder.buildPublicLiteralConstraint(id, name, pattern, Datatype.STRING);

		assertNotNull(constraint);
		assertNull(constraint.getResourceTypeConstraint());
		assertEquals("Constraints are not equal", pattern, constraint.getLiteralConstraint());
		assertEquals("Constraintname is not as expected", name, constraint.getName());
		assertEquals("Constraint-id not as expected", id, constraint.getID());
		assertEquals("Applicable datatypes do not match", Datatype.STRING, constraint.getApplicableDatatypes().get(0));
	}

	@Test
	public void testBuildPublicLiteralConstraintMultipleDatatypes() {
		SimpleResourceID id = new SimpleResourceID();
		List<Datatype> list = new ArrayList<Datatype>();
		list.add(Datatype.STRING);
		list.add(Datatype.TEXT);
		list.add(Datatype.DATE);
		Constraint constraint = ConstraintBuilder.buildPublicLiteralConstraint(id, name, pattern, list);
		
		assertNotNull(constraint);
		assertNull(constraint.getResourceTypeConstraint());
		assertEquals("Constraints are not equal", pattern, constraint.getLiteralConstraint());
		assertEquals("Constraintname is not as expected", name, constraint.getName());
		assertEquals("Constraint-id not as expected", id, constraint.getID());
		assertEquals("Applicable datatypes doe not match", Datatype.STRING, constraint.getApplicableDatatypes().get(0));
		assertEquals("Number of applicable Datatypes do not match", list.size(), constraint.getApplicableDatatypes().size());
		for (Datatype dt : constraint.getApplicableDatatypes()) {
			assertTrue("Datatype isnot contained in constraint", list.contains(dt));
		}
	}
	
	@Test
	public void testBuildResourceConstraint() {
		Constraint constraint = ConstraintBuilder.buildResourceConstraint(resource);

		assertNotNull(constraint);
		assertNull("Id should be null", constraint.getID());
		assertNull(constraint.getLiteralConstraint());
		assertEquals("Constraints are not equal", resource.getQualifiedName(), constraint.getResourceTypeConstraint()
				.getQualifiedName());
		assertEquals("Constraintname is not as expected", resource.toURI(), constraint.getName());
	}

	@Test
	public void testBuildPublicResourceConstraint() {
		ResourceID id = new SimpleResourceID();
		Constraint constraint = ConstraintBuilder.buildPublicResourceConstraint(id, publicResConstrName, resource);

		assertNotNull(constraint);
		assertNotNull("Id should not be null", constraint.getID());
		assertEquals("Id is not as expected", id, constraint.getID());
		assertNull(constraint.getLiteralConstraint());
		assertEquals("Constraints are not equal", resource.getQualifiedName(), constraint.getResourceTypeConstraint()
				.getQualifiedName());
		System.out.println(constraint.getName());
		assertEquals("Constraintname is not as expected", publicResConstrName, constraint.getName());
	}
	
	@Test
	public void testEmptyConstraint() {
		Constraint c = ConstraintBuilder.emptyConstraint();
		assertNull(c.getApplicableDatatypes());
		assertNull(c.getID());
		assertNull(c.getLiteralConstraint());
		assertNull(c.getName());
		assertNull(c.getResourceTypeConstraint());
	}

}
