/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.List;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.schema.model.INewRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.schema.model.IRBMetaInfo;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 *  Implementation of {@link INewRBEntity}.
 * </p>
 *
 * <p>
 * 	Created Aug 11, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class NewRBEntity implements INewRBEntity {

	private final ResourceNode node;
	private final ResourceSchema schema;

	// -----------------------------------------------------

	/**
	 * Creates a new entity without schema.
	 */
	public NewRBEntity() {
		this(new SNResource());
	}

	/**
	 * Creates an entity based on given node without schema.
	 * @param node The node.
	 */
	public NewRBEntity(final ResourceNode node) {
		this(node, null);
	}

	/**
	 * Creates a new entity with a given schema.
	 * @param schema The schema.
	 */
	public NewRBEntity(final ResourceSchema schema) {
		this(new SNResource(), schema);
	}

	/**
	 * Creates an entity based on node and schema.
	 * @param node The node.
	 * @param schema The schema.
	 */
	public NewRBEntity(final ResourceNode node, final ResourceSchema schema) {
		super();
		this.node = node;
		this.schema = schema;
	}

	// -----------------------------------------------------


	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getID() {
		// create a copy of Resource ID
		return new SimpleResourceID(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QualifiedName getQualifiedName() {
		return node.getQualifiedName();
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IRBField> getAllFields() {
		System.out.println(node.getAssociations());
		throw new NotYetImplementedException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addField(final IRBField field) {
		throw new NotYetImplementedException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IRBMetaInfo getRBMetaInfo() {
		throw new NotYetImplementedException();
	}

	/**
	 * TestMain.
	 * @param args -
	 */
	public static void main(final String[] args){
		NewRBEntity entity = new NewRBEntity(NewRBEntity.createPersonSchema());
		System.out.println(entity.getAllFields());
	}

	/**
	 * @return schema
	 */
	private static ResourceSchema createPersonSchema() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl(
				"http://lichtflut.de#", "personschema");
		PropertyDeclarationImpl p1 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p2 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p3 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p4 = new PropertyDeclarationImpl();
		p1.setName("http://lichtflut.de#geburtsdatum");
		p2.setName("http://lichtflut.de#email");
		p3.setName("http://lichtflut.de#alter");
		p4.setName("http://lichtflut.de#kind");

		p1.setElementaryDataType(ElementaryDataType.STRING);
		p2.setElementaryDataType(ElementaryDataType.STRING);
		p3.setElementaryDataType(ElementaryDataType.INTEGER);
		p4.setElementaryDataType(ElementaryDataType.RESOURCE);

		p2.addConstraint(ConstraintFactory.buildConstraint(".*@.*"));
		p4.addConstraint(ConstraintFactory.buildConstraint(schema
				.getDescribedResourceID()));

		PropertyAssertionImpl pa1 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hatGeburtstag"),
				p1);
		PropertyAssertionImpl pa2 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hatEmail"), p2);
		PropertyAssertionImpl pa3 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hatAlter"), p3);
		PropertyAssertionImpl pa4 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hatKind"), p4);

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

}
