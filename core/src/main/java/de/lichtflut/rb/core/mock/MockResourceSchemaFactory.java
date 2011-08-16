/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.mock;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;

/**
 * <p>
 * This class provides static {@link ResourceSchema}s for testing purposes.
 * </p>
 * 
 * Created: Aug 16, 2011
 *
 * @author Ravi Knox
 */
public class MockResourceSchemaFactory {

	/**
	 * <p>
	 * Returns a {@link ResourceSchema} based on a Person (use-case: Employee, Student, etc.).
	 * </p>
	 * @return schema -
	 */
	public static ResourceSchema createPersonSchema() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl(
				"http://lichtflut.de#", "personschema");
		PropertyDeclarationImpl p1 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p2 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p3 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p4 = new PropertyDeclarationImpl();
		p1.setName("http://lichtflut.de#dateOfBirth");
		p2.setName("http://lichtflut.de#email");
		p3.setName("http://lichtflut.de#age");
		p4.setName("http://lichtflut.de#children");

		p1.setElementaryDataType(ElementaryDataType.DATE);
		p2.setElementaryDataType(ElementaryDataType.STRING);
		p3.setElementaryDataType(ElementaryDataType.INTEGER);
		p4.setElementaryDataType(ElementaryDataType.INTEGER);

		p2.addConstraint(ConstraintFactory.buildConstraint(".*@.*"));

		PropertyAssertionImpl pa1 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasDateOfBirth"),
				p1);
		PropertyAssertionImpl pa2 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasEmail"), p2);
		PropertyAssertionImpl pa3 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasAge"), p3);
		PropertyAssertionImpl pa4 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasChild"), p4);

		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		pa2.setCardinality(CardinalityBuilder.hasAtLeastOneUpTo(2));
		pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());
		pa4.setCardinality(CardinalityBuilder.hasOptionalOneToMany());

		schema.addPropertyAssertion(pa1);
		schema.addPropertyAssertion(pa2);
		schema.addPropertyAssertion(pa3);
		schema.addPropertyAssertion(pa4);

		return schema;
	}

	/**
	 * <p>
	 * Returns a {@link ResourceSchema} based on Strings only.
	 * </p>
	 * @return schema -
	 */
	public static ResourceSchema createOnlyStringSchema() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl(
				"http://lichtflut.de#", "Stringschema");
		PropertyDeclarationImpl p2 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p3 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p4 = new PropertyDeclarationImpl();
		p2.setName("http://lichtflut.de#email");
		p3.setName("http://lichtflut.de#age");
		p4.setName("http://lichtflut.de#children");

		p2.setElementaryDataType(ElementaryDataType.STRING);
		p3.setElementaryDataType(ElementaryDataType.STRING);
		p4.setElementaryDataType(ElementaryDataType.STRING);

		p2.addConstraint(ConstraintFactory.buildConstraint(".*@.*"));

		PropertyAssertionImpl pa2 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasEmail"), p2);
		PropertyAssertionImpl pa3 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasAge"), p3);
		PropertyAssertionImpl pa4 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasChild"), p4);

		pa2.setCardinality(CardinalityBuilder.hasAtLeastOneUpTo(2));
		pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());
		pa4.setCardinality(CardinalityBuilder.hasOptionalOneToMany());

		schema.addPropertyAssertion(pa2);
		schema.addPropertyAssertion(pa3);
		schema.addPropertyAssertion(pa4);

		return schema;
	}

	/**
	 * TODO: refactor for public use.
	 * @param referredSchema
	 * @return
	 */
	@SuppressWarnings("unused")
	private ResourceSchema createCarSchema(final ResourceSchema referredSchema) {

		ResourceSchemaImpl schema = new ResourceSchemaImpl(
				"http://lichtflut.de#", "autoschema");
		PropertyDeclarationImpl p1 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p2 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p3 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p4 = new PropertyDeclarationImpl();
		p1.setName("http://lichtflut.de#marke");
		p2.setName("http://lichtflut.de#modell");
		p3.setName("http://lichtflut.de#alter");
		p4.setName("http://lichtflut.de#halter");

		p1.setElementaryDataType(ElementaryDataType.STRING);
		p2.setElementaryDataType(ElementaryDataType.STRING);
		p3.setElementaryDataType(ElementaryDataType.INTEGER);
		p4.setElementaryDataType(ElementaryDataType.RESOURCE);

		p3.addConstraint(ConstraintFactory.buildConstraint("[1-3]"));
		p4.addConstraint(ConstraintFactory.buildConstraint(referredSchema
				.getDescribedResourceID()));

		PropertyAssertionImpl pa1 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hatMarke"), p1);
		PropertyAssertionImpl pa2 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hatModell"), p2);
		PropertyAssertionImpl pa3 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hatAlter"), p3);
		PropertyAssertionImpl pa4 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hatHalter"), p4);

		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		pa2.setCardinality(CardinalityBuilder.hasAtLeastOneUpTo(2));
		pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());
		pa4.setCardinality(CardinalityBuilder.hasAtLeast(1));

		schema.addPropertyAssertion(pa1);
		schema.addPropertyAssertion(pa2);
		schema.addPropertyAssertion(pa3);
		schema.addPropertyAssertion(pa4);

		return schema;
	}
}
