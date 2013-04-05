/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.common;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.junit.Test;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;

/**
 * <p>
 * Testclass for {@link SchemaIdentifyingType},
 * </p>
 * Created: Feb 4, 2013
 *
 * @author Ravi Knox
 */
public class SchemaIdentifyingTypeTest {

	/**
	 * Test method for {@link de.lichtflut.rb.core.common.SchemaIdentifyingType#of(org.arastreju.sge.model.nodes.ResourceNode)}.
	 */
	@Test
	public void testOfNullNode() {
		SNClass schemaClass = SchemaIdentifyingType.of(null);

		assertThat(schemaClass, nullValue());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.common.SchemaIdentifyingType#of(org.arastreju.sge.model.nodes.ResourceNode)}.
	 */
	@Test
	public void testOfResourceNodeSchemaViaDirectIdentifyingSchema() {
		ResourceNode node = new SNResource();
		node.addAssociation(RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE, RB.PERSON);
		node.addAssociation(RDF.TYPE, RB.CITY);

		SNClass schemaClass = SchemaIdentifyingType.of(node);

		assertThat(schemaClass, equalTo(RB.PERSON));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.common.SchemaIdentifyingType#of(org.arastreju.sge.model.nodes.ResourceNode)}.
	 */
	@Test
	public void testOfResourceNodeType() {
		ResourceNode node = new SNResource();
		node.addAssociation(RDF.TYPE, RB.CITY);

		SNClass schemaClass = SchemaIdentifyingType.of(node);

		assertThat(schemaClass, equalTo(RB.CITY));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.common.SchemaIdentifyingType#of(org.arastreju.sge.model.nodes.ResourceNode)}.
	 */
	@Test
	public void testOfResourceNodeSchemaViaDirectSuperClass() {
		ResourceNode node = new SNResource();
		ResourceNode superClass = new SNResource();

		node.addAssociation(RDFS.SUB_CLASS_OF, superClass);
		node.addAssociation(RDF.TYPE, new SNResource());
		node.addAssociation(RDF.TYPE, RB.CITY);
		superClass.addAssociation(RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE, RB.PERSON);
		superClass.addAssociation(RDF.TYPE, new SNResource());
		superClass.addAssociation(RDF.TYPE, RB.CITY);

		SNClass schemaClass = SchemaIdentifyingType.of(node);

		assertThat(schemaClass, equalTo(RB.PERSON));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.common.SchemaIdentifyingType#of(org.arastreju.sge.model.nodes.ResourceNode)}.
	 */
	@Test
	public void testOfResourceNodeSchemaViaCascadeOfSuperClasses() {
		ResourceNode node = new SNResource();
		ResourceNode superClass1 = new SNResource();
		SNResource superClass2 = new SNResource();
		SNResource superClass3 = new SNResource();

		node.addAssociation(RDF.TYPE, new SNResource());
		superClass1.addAssociation(RDF.TYPE, new SNResource());
		superClass2.addAssociation(RDF.TYPE, new SNResource());
		superClass3.addAssociation(RDF.TYPE, new SNResource());

		node.addAssociation(RDFS.SUB_CLASS_OF, superClass1);
		superClass1.addAssociation(RDFS.SUB_CLASS_OF, superClass2);
		superClass2.addAssociation(RDFS.SUB_CLASS_OF, superClass3);
		superClass3.addAssociation(RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE, RB.PERSON);

		SNClass schemaClass = SchemaIdentifyingType.of(node);

		assertThat(schemaClass, equalTo(RB.PERSON));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.common.SchemaIdentifyingType#of(org.arastreju.sge.model.nodes.ResourceNode)}.
	 */
	@Test
	public void testOfResourceNodeSchemaGetFirstSchemaInCascadeNotLast() {
		ResourceNode node = new SNResource();
		ResourceNode superClass1 = new SNResource();
		SNResource superClass2 = new SNResource();
		SNResource superClass3 = new SNResource();

		node.addAssociation(RDF.TYPE, new SNResource());
		superClass1.addAssociation(RDF.TYPE, new SNResource());
		superClass2.addAssociation(RDF.TYPE, new SNResource());
		superClass3.addAssociation(RDF.TYPE, new SNResource());

		node.addAssociation(RDFS.SUB_CLASS_OF, superClass1);
		superClass1.addAssociation(RDFS.SUB_CLASS_OF, superClass2);
		superClass2.addAssociation(RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE, RB.CITY);
		superClass2.addAssociation(RDFS.SUB_CLASS_OF, superClass3);
		superClass3.addAssociation(RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE, RB.PERSON);

		SNClass schemaClass = SchemaIdentifyingType.of(node);

		assertThat(schemaClass, equalTo(RB.CITY));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.common.SchemaIdentifyingType#of(org.arastreju.sge.model.nodes.ResourceNode)}.
	 */
	@Test
	public void testOfResourceNodeSchemaNoSchemaButType() {
		ResourceNode node = new SNResource();


		ResourceNode superClass1 = new SNResource();
		superClass1.addAssociation(RDF.TYPE, RB.CITY);
		node.addAssociation(RDFS.SUB_CLASS_OF, superClass1);

		SNResource superClass2 = new SNResource();
		superClass2.addAssociation(RDF.TYPE, new SNResource());
		superClass1.addAssociation(RDFS.SUB_CLASS_OF, superClass2);

		SNResource superClass3 = new SNResource();
		superClass3.addAssociation(RDF.TYPE, RB.PERSON);
		superClass2.addAssociation(RDFS.SUB_CLASS_OF, superClass3);

		SNClass schemaClass = SchemaIdentifyingType.of(node);

		assertThat(schemaClass, equalTo(RB.CITY));
	}
}
