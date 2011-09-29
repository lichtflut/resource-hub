/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import java.util.Collection;

import junit.framework.Assert;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;

/**
 * <p>
 *  Test case for storing and reading of Resource Schema Models to/from Arastreju.
 * </p>
 *
 * <p>
 * 	Created Apr 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBSchemaStoreTest {

	private ArastrejuGate gate;

	/**
	 * Set-Up method.
	 */
	@Before
	public void setUp() {
		gate = Arastreju.getInstance().rootContext();
	}

	/**
	 * Tear-Down method.
	 */
	@After
	public void tearDown() {
		gate.close();
	}


	/**
	 * Does test the RBSchemaStore, persist and load-functionality.
	 */
	@Test
	public void testStore() {
		 final RBSchemaStore store = new RBSchemaStore(gate);

		 Collection<TypeDefinition> pDecs = store.loadAllPropertyTypeDefinitions(null);

		 Assert.assertEquals(0, pDecs.size());

		 final ResourceSchema schema = createSchema();

		 final SNResourceSchema snSchema = store.store(schema,null);

		 pDecs = store.loadAllPropertyTypeDefinitions(null);
		 Assert.assertEquals(3, pDecs.size());

		 Assert.assertNotNull(snSchema);

		 Assert.assertEquals(3, snSchema.getPropertyAssertions().size());

		 ResourceSchema schema2 = store.convertResourceSchema(snSchema);

		 Assert.assertNotNull(schema2);

		 Assert.assertEquals(3, schema2.getPropertyAssertions().size());


		 Collection<ResourceSchema> schemas = store.loadAllResourceSchemas(null);
		 Assert.assertEquals(1,schemas.size());


		 //Test that schema can be found for the desrcibed resource (RT)
		 ResourceID describedResourceID = schema.getDescribedType();
		 Assert.assertNotNull(store.loadSchemaForResourceType(describedResourceID,null));
		 //Test that there is schema for a RT, that doesnt exists
		 Assert.assertNull(store.loadSchemaForResourceType(new SimpleResourceID(new QualifiedName("test")),null));

		 pDecs = store.loadAllPropertyTypeDefinitions(null);

		 Assert.assertEquals(3, pDecs.size());

	}

	// -----------------------------------------------------

	/**
	 * @return the created {@link ResourceSchema}
	 */
	private ResourceSchema createSchema() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl("http://lichtflut.de#","personschema");
		TypeDefinitionImpl p1 = new TypeDefinitionImpl();
		TypeDefinitionImpl p2 = new TypeDefinitionImpl();
		TypeDefinitionImpl p3 = new TypeDefinitionImpl();
		p1.setName("http://lichtflut.de#geburtsdatum");
		p2.setName("http://lichtflut.de#email");
		p3.setName("http://lichtflut.de#alter");

		p1.setElementaryDataType(ElementaryDataType.DATE);
		p2.setElementaryDataType(ElementaryDataType.STRING);
		p3.setElementaryDataType(ElementaryDataType.INTEGER);

		p2.addConstraint(ConstraintBuilder.buildConstraint(".*@.*"));
		PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#","hatGeburtstag"), p1);
		PropertyDeclarationImpl pa2 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#","hatEmail"), p2);
		PropertyDeclarationImpl pa3 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#","hatAlter"), p3);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		pa2.setCardinality(CardinalityBuilder.hasAtLeastOneUpTo(3));
		pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());

		schema.addPropertyAssertion(pa1);
		schema.addPropertyAssertion(pa2);
		schema.addPropertyAssertion(pa3);

		return schema;
	}

}
