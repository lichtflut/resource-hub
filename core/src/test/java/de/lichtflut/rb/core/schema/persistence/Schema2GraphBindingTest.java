/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.arastreju.sge.model.nodes.views.SNText;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.mock.schema.ConstraintsFactory;
import de.lichtflut.rb.mock.schema.ResourceSchemaFactory;

/**
 * <p>
 * Testclass for {@link Schema2GraphBinding}.
 * </p>
 * Created: May 8, 2012
 *
 * @author Ravi Knox
 */
@RunWith(MockitoJUnitRunner.class)
public class Schema2GraphBindingTest {

	private Schema2GraphBinding binding;
	private ConstraintResolver resolver;
	private Context ctx;

	@Before
	public void setUp(){
		ctx = RBSchema.CONTEXT;
		resolver = mock(ConstraintResolver.class);
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.Schema2GraphBinding#Schema2GraphBinding(de.lichtflut.rb.core.schema.persistence.ConstraintResolver)}.
	 */
	@Test
	public void testSchema2GraphBinding() {
		binding = new Schema2GraphBinding(resolver);
		assertThat(binding, notNullValue());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.Schema2GraphBinding#toModelObject(de.lichtflut.rb.core.schema.persistence.SNResourceSchema)}.
	 */
	@Test
	public void testToModelObjectSchema() {
		ResourceSchema schema = ResourceSchemaFactory.buildPersonSchema();

		SNResource node = new SNResource();
		SNResourceSchema snSchema = new SNResourceSchema(node, RBSchema.CONTEXT);
		snSchema.setDescribedType(schema.getDescribedType(), RBSchema.CONTEXT);		
		snSchema.setLabelExpression(new SNText(schema.getLabelBuilder().getExpression()), RBSchema.CONTEXT);
		
		SNPropertyDeclaration predecessor = null;
		for(PropertyDeclaration decl : schema.getPropertyDeclarations()) {
			final SNPropertyDeclaration snDecl = new SNPropertyDeclaration();
			snDecl.setPropertyDescriptor(decl.getPropertyDescriptor(), RBSchema.CONTEXT);
			snDecl.setMinOccurs(new SNScalar(decl.getCardinality().getMinOccurs()), RBSchema.CONTEXT);
			snDecl.setMaxOccurs(new SNScalar(decl.getCardinality().getMaxOccurs()), RBSchema.CONTEXT);
			snDecl.setDatatype(decl.getDatatype(), RBSchema.CONTEXT);
			snDecl.setFieldLabelDefinition(decl.getFieldLabelDefinition(), RBSchema.CONTEXT);
			if(decl.hasConstraint()){
				snDecl.setConstraint(decl.getConstraint(), RBSchema.CONTEXT);
			}
			if (null != predecessor) {
				predecessor.setSuccessor(snDecl, RBSchema.CONTEXT);
			}
			predecessor = snDecl;
			snSchema.addPropertyDeclaration(snDecl);
		}
		ResourceSchema converted = getBinding().toModelObject(snSchema);
		assertThat(converted.getDescribedType(), equalTo(schema.getDescribedType()));
		assertThat(converted.getLabelBuilder().getExpression(), equalTo(schema.getLabelBuilder().getExpression()));
		assertThat(converted.getPropertyDeclarations().size(), is(schema.getPropertyDeclarations().size()));
	}
	
	/**
	 * Test method for
	 * {@link de.lichtflut.rb.core.schema.persistence.Schema2GraphBinding#toSemanticNode(de.lichtflut.rb.core.schema.model.ResourceSchema)}.
	 */
	@Test
	public void testToSemanticNode() {
		ResourceSchema schema = ResourceSchemaFactory.buildPersonSchema();

		SNResourceSchema snr = getBinding().toSemanticNode(schema);
		assertThat(snr, notNullValue());
		assertThat(snr.getDescribedType(), equalTo(schema.getDescribedType()));
		assertThat(snr.getPropertyDeclarations().size(), is(schema.getPropertyDeclarations().size()));
		assertThat(snr.getLabelExpression().getStringValue(), equalTo(schema.getLabelBuilder().getExpression()));
		int counter = 0;
		while (counter < schema.getPropertyDeclarations().size()){
			assertDeclaration(snr.getPropertyDeclarations().get(counter), schema.getPropertyDeclarations().get(counter));
			counter++;
		}
	}

	/**
	 * @param converted
	 * @param decl
	 */
	private void assertDeclaration(SNPropertyDeclaration converted, PropertyDeclaration decl) {
		assertThat(converted.getPropertyDescriptor(), equalTo(decl.getPropertyDescriptor()));
		assertThat(converted.getFieldLabelDefinition().getDefaultLabel(), equalTo(decl
				.getFieldLabelDefinition().getDefaultLabel()));
		if(decl.getCardinality().isUnbound()){
			assertThat(converted.getMaxOccurs().getIntegerValue().intValue(), is(-1));
		}else{
			assertThat(converted.getMaxOccurs().getIntegerValue().intValue(), equalTo(decl.getCardinality().getMaxOccurs()));
		}
		assertThat(converted.getMinOccurs(), equalTo(new SNScalar(decl.getCardinality().getMinOccurs())));
		assertThat(converted.getDatatype(), equalTo(decl.getDatatype()));
		if(converted.hasConstraint()){
			assertThat(converted.getConstraint().asResourceNode(), equalTo(decl.getConstraint().asResourceNode()));
		}
		// Implement I18n in Fieldlabel-mapping
		// assertThat(snDecl.getFieldLabelDefinition().getSupportedLocales().size(),
		// is(decl.getFieldLabelDefinition().getSupportedLocales().size()));
		assertThat(converted.getPropertyDescriptor(), equalTo(decl.getPropertyDescriptor()));
	}

	private Schema2GraphBinding getBinding() {
		return new Schema2GraphBinding(resolver);
	}
}