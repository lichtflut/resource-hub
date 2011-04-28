/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import junit.framework.Assert;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityFactory;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
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
	

	@Test
	public void testStore() {

		 final ArastrejuGate gate = Arastreju.getInstance().rootContext();
		 
		 final RBSchemaStore store = new RBSchemaStore(gate);
		 
		 final ResourceSchema schema = createSchema();
		 
		 final SNResourceSchema snSchema = store.store(schema, null);
		 System.out.println(schema);
		 
		 Assert.assertNotNull(snSchema);
		 
		 Assert.assertEquals(3, snSchema.getPropertyAssertions().size());
		 
		 System.out.println(snSchema);
		 
		 ResourceSchema schema2 = store.convert(snSchema);
		 
		 System.out.println(schema2);
		 
		 Assert.assertNotNull(schema2);
		 
		 Assert.assertEquals(3, schema2.getPropertyAssertions().size());
		 
		 
	}
	
	// -----------------------------------------------------
	
	private ResourceSchema createSchema() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl("http://lichtflut.de#","personschema");
		PropertyDeclarationImpl p1 = new PropertyDeclarationImpl(); 
		PropertyDeclarationImpl p2 = new PropertyDeclarationImpl(); 
		PropertyDeclarationImpl p3 = new PropertyDeclarationImpl(); 
		p1.setName("http://lichtflut.de#geburtsdatum");
		p2.setName("http://lichtflut.de#email");
		p3.setName("http://lichtflut.de#alter");
		
		p1.setElementaryDataType(ElementaryDataType.DATE);
		p2.setElementaryDataType(ElementaryDataType.STRING);
		p3.setElementaryDataType(ElementaryDataType.INTEGER);
		
		p2.addConstraint(ConstraintFactory.buildConstraint(".*@.*"));
		PropertyAssertionImpl pa1 = new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de#","hatGeburtstag"), p1);
		PropertyAssertionImpl pa2 = new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de#","hatEmail"), p2);
		PropertyAssertionImpl pa3 = new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de#","hatAlter"), p3);
		pa1.setCardinality(CardinalityFactory.hasExcactlyOne());
		pa2.setCardinality(CardinalityFactory.hasAtLeastOneUpTo(3));
		pa3.setCardinality(CardinalityFactory.hasExcactlyOne());
		
		schema.addPropertyAssertion(pa1);
		schema.addPropertyAssertion(pa2);
		schema.addPropertyAssertion(pa3);
		
		return schema;
	}
	
}
