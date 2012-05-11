/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNText;
import org.junit.Before;
import org.junit.Test;

import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.Datatype;

/**
 * <p>
 * Testclass for {@link SNConstraint};
 * </p>
 * Created: May 11, 2012
 *
 * @author Ravi Knox
 */
public class SNConstraintTest {

	private String emailPattern;
	private ResourceID publicID;
	private Context ctx;
	
	@Before
	public void setUp(){
		emailPattern = ".*@.*";
		ctx = RBSchema.CONTEXT;
		publicID = new SimpleResourceID("http://test.rb.de/common#EmailPattern");
	}
	
	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNConstraint#SNConstraint()}.
	 */
	@Test
	public void testSNConstraint() {
		SNConstraint snConstraint = new SNConstraint();
		assertThat(snConstraint, notNullValue());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNConstraint#SNConstraint(org.arastreju.sge.model.nodes.ResourceNode)}.
	 */
	@Test
	public void testSNConstraintResourceNode() {
		ResourceNode resource = new SNResource();
		SNConstraint constraint = new SNConstraint(resource);
		assertThat(constraint, notNullValue());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNConstraint#SNConstraint(java.lang.String, org.arastreju.sge.context.Context)}.
	 */
	@Test
	public void testSNConstraintPrivateLiteral() {
		List<Datatype> datatype = Collections.singletonList(Datatype.STRING);
		SNConstraint constraint = new SNConstraint(emailPattern, datatype, ctx);
		checkBasicLiteralConstraintData(constraint);
		assertThat(constraint.getID(), nullValue());
		assertThat(constraint.getName(), nullValue());
		assertThat(constraint.isPublic(), is(Boolean.FALSE));
		assertThat(constraint.getApplicableDatatypes().size(), is(datatype.size()));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNConstraint#SNConstraint(org.arastreju.sge.model.ResourceID, java.lang.String, java.lang.String, org.arastreju.sge.context.Context)}.
	 */
	@Test
	public void testSNConstraintPublicLiteral() {
		String name = "Email-Address";
		List<Datatype> datatypes = new ArrayList<Datatype>();
		datatypes.add(Datatype.STRING);
		datatypes.add(Datatype.TEXT);
		datatypes.add(Datatype.INTEGER);
		SNConstraint constraint = new SNConstraint(emailPattern, datatypes, ctx);
		constraint.setID(publicID, ctx);
		constraint.setName(name, ctx);
		constraint.setPublic(true, ctx);
		checkBasicLiteralConstraintData(constraint);
		assertThat(constraint.getID(), equalTo(publicID));
		assertThat(constraint.getName(), equalTo(name));
		assertThat(constraint.isPublic(), is(Boolean.TRUE));
		assertThat(constraint.getApplicableDatatypes().size(), is(datatypes.size()));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNConstraint#SNConstraint(org.arastreju.sge.model.ResourceID, org.arastreju.sge.context.Context)}.
	 */
	@Test
	public void testSNConstraintPrivateResource() {
		ResourceID type = new SimpleResourceID("http://test.rb.lf.de/common#Person");
		SNConstraint constraint = new SNConstraint(type, ctx);
		checkBasicResourceConstraintData(type, constraint);
		assertThat(constraint.getID(), nullValue());
		assertThat(constraint.getName(), nullValue());
		assertThat(constraint.isPublic(), is(Boolean.FALSE));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNConstraint#SNConstraint(org.arastreju.sge.model.ResourceID, java.lang.String, org.arastreju.sge.model.ResourceID, org.arastreju.sge.context.Context)}.
	 */
	@Test
	public void testSNConstraintPublicResource() {
		ResourceID type = new SimpleResourceID("http://test.rb.lf.de/common#Person");
		String name = "Person-Constraint";
		SNConstraint constraint = new SNConstraint(type, ctx);
		constraint.setID(publicID, ctx);
		constraint.setName(name, ctx);
		constraint.setPublic(true, ctx);
		checkBasicResourceConstraintData(type, constraint);
		assertThat(constraint.getID(), equalTo(publicID));
		assertThat(constraint.getName(), equalTo(name));
		assertThat(constraint.isPublic(), equalTo(Boolean.TRUE));
		assertThat(constraint.getApplicableDatatypes().size(), is(1));
	}

	/**
	 * @param constraint
	 */
	private void checkBasicLiteralConstraintData(SNConstraint constraint) {
		assertThat(constraint, notNullValue());
		assertThat(constraint.getConstraintValue().asValue().asText(), equalTo(new SNText(emailPattern)));
		assertThat(constraint.isResourceConstraint(), is(Boolean.FALSE));
	}
	
	/**
	 * @param type
	 * @param constraint
	 */
	private void checkBasicResourceConstraintData(ResourceID type, SNConstraint constraint) {
		assertThat(constraint, notNullValue());
		assertThat(constraint.getConstraintValue().asResource(), equalTo(type));
		assertThat(constraint.isResourceConstraint(), is(Boolean.TRUE));
	}
	
}
