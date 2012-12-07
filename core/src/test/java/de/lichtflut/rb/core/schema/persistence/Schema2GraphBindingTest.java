/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.arastreju.sge.model.nodes.views.SNText;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
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

	/**
	 * Test method for {@link Schema2GraphBinding#Schema2GraphBinding()}.
	 */
	@Test
	public void testSchema2GraphBinding() {
		binding = new Schema2GraphBinding();
		assertThat(binding, notNullValue());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.Schema2GraphBinding#toModelObject(de.lichtflut.rb.core.schema.persistence.SNResourceSchema)}.
	 */
	@Test
	public void testToModelObjectSchema() {
		ResourceSchema schema = ResourceSchemaFactory.buildPersonSchema();

		SNResource node = new SNResource();
		SNResourceSchema snSchema = new SNResourceSchema(node);
		snSchema.setDescribedType(schema.getDescribedType());
		snSchema.setLabelExpression(new SNText(schema.getLabelBuilder().getExpression()));

		SNPropertyDeclaration predecessor = null;
		for(PropertyDeclaration decl : schema.getPropertyDeclarations()) {
			final SNPropertyDeclaration snDecl = new SNPropertyDeclaration();
			snDecl.setPropertyDescriptor(decl.getPropertyDescriptor());
			snDecl.setMinOccurs(new SNScalar(decl.getCardinality().getMinOccurs()));
			snDecl.setMaxOccurs(new SNScalar(decl.getCardinality().getMaxOccurs()));
			snDecl.setDatatype(decl.getDatatype());
			snDecl.setFieldLabelDefinition(decl.getFieldLabelDefinition());
            if (null != predecessor) {
				predecessor.setSuccessor(snDecl);
			}
			predecessor = snDecl;
			snSchema.addPropertyDeclaration(snDecl);
		}
		SNQuickInfo predecessorQuickInfo = null;
		for (PropertyDeclaration decl : schema.getQuickInfo()) {
			SNQuickInfo current = new SNQuickInfo(decl.getPropertyDescriptor());
			if(null != predecessorQuickInfo){
				predecessorQuickInfo.addSuccessor(current);
			}
			predecessorQuickInfo = current;
			snSchema.addQuickInfo(current);
		}
		ResourceSchema converted = getBinding().toModelObject(snSchema);
		assertThat(converted.getDescribedType(), equalTo(schema.getDescribedType()));
		assertThat(converted.getLabelBuilder().getExpression(), equalTo(schema.getLabelBuilder().getExpression()));
		assertThat(converted.getPropertyDeclarations().size(), is(schema.getPropertyDeclarations().size()));
		assertThat(converted.getQuickInfo().size(), is(schema.getQuickInfo().size()));
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
		assertThat(snr.getQuickInfo().size(), is(schema.getQuickInfo().size()));
		int counter = 0;
		while (counter < schema.getPropertyDeclarations().size()){
			assertDeclaration(snr.getPropertyDeclarations().get(counter), schema.getPropertyDeclarations().get(counter));
			counter++;
		}
		counter = 0;
		while(counter < schema.getQuickInfo().size()){
			assertThat(snr.getQuickInfo().get(counter).toURI(), equalTo(schema.getQuickInfo().get(counter).getPropertyDescriptor().toURI()));
			counter ++;
		}
	}

	/**
	 * @param converted The converted.
	 * @param decl The original declaration.
	 */
	private void assertDeclaration(final SNPropertyDeclaration converted, final PropertyDeclaration decl) {
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
		if(converted.hasConstraint() && converted.getConstraint().isPublic()) {
			assertThat(converted.getConstraint().getQualifiedName(), equalTo(decl.getConstraint().getQualifiedName()));
		}
		// Implement I18n in Fieldlabel-mapping
		// assertThat(snDecl.getFieldLabelDefinition().getSupportedLocales().size(),
		// is(decl.getFieldLabelDefinition().getSupportedLocales().size()));
		assertThat(converted.getPropertyDescriptor(), equalTo(decl.getPropertyDescriptor()));
	}

	private Schema2GraphBinding getBinding() {
		return new Schema2GraphBinding();
	}
}
