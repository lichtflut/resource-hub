/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.common;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.persistence.SNResourceSchema;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * <p>
 *  Testclass for {@link SchemaIdentifyingType},
 * </p>
 *
 * <p>
 *  Created: Feb 4, 2013
 * </p>
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
        SNClass clazz = new SNClass();
		SNClass superClass = new SNClass();

		node.addAssociation(RDF.TYPE, clazz);
        clazz.addAssociation(RDFS.SUB_CLASS_OF, superClass);
		superClass.addAssociation(RBSchema.HAS_SCHEMA, new SNResourceSchema());
		superClass.addAssociation(RDF.TYPE, new SNResource());
		superClass.addAssociation(RDF.TYPE, RB.CITY);

		SNClass schemaClass = SchemaIdentifyingType.of(node);

		assertThat(schemaClass, equalTo(superClass));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.common.SchemaIdentifyingType#of(org.arastreju.sge.model.nodes.ResourceNode)}.
	 */
	@Test
	public void testOfResourceNodeSchemaViaCascadeOfSuperClasses() {
		ResourceNode node = new SNResource();
        SNClass superClass1 = new SNClass();
        SNClass superClass2 = new SNClass();
		SNClass superClass3 = new SNClass();

		node.addAssociation(RDF.TYPE, new SNResource());
		superClass1.addAssociation(RDF.TYPE, new SNResource());
		superClass2.addAssociation(RDF.TYPE, new SNResource());
		superClass3.addAssociation(RDF.TYPE, new SNResource());

		node.addAssociation(RDF.TYPE, superClass1);
		superClass1.addAssociation(RDFS.SUB_CLASS_OF, superClass2);
		superClass2.addAssociation(RDFS.SUB_CLASS_OF, superClass3);
		superClass3.addAssociation(RBSchema.HAS_SCHEMA, new SNResourceSchema());

		SNClass schemaClass = SchemaIdentifyingType.of(node);

		assertThat(schemaClass, equalTo(superClass3));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.common.SchemaIdentifyingType#of(org.arastreju.sge.model.nodes.ResourceNode)}.
	 */
	@Test
	public void testOfResourceNodeSchemaGetFirstSchemaInCascadeNotLast() {
		ResourceNode node = new SNResource();
		SNClass superClass1 = new SNClass();
        SNClass superClass2 = new SNClass();
        SNClass superClass3 = new SNClass();

		node.addAssociation(RDF.TYPE, new SNResource());
		superClass1.addAssociation(RDF.TYPE, new SNResource());
		superClass2.addAssociation(RDF.TYPE, new SNResource());
		superClass3.addAssociation(RDF.TYPE, new SNResource());

		node.addAssociation(RDF.TYPE, superClass1);
		superClass1.addAssociation(RDFS.SUB_CLASS_OF, superClass2);
        superClass2.addAssociation(RDFS.SUB_CLASS_OF, superClass3);
		superClass2.addAssociation(RBSchema.HAS_SCHEMA, new SNResourceSchema());
		superClass3.addAssociation(RBSchema.HAS_SCHEMA, new SNResourceSchema());

		SNClass schemaClass = SchemaIdentifyingType.of(node);

		assertThat(schemaClass, equalTo(superClass2));
	}

}
