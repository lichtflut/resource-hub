/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.instance.persistence;

import java.util.Collection;

import junit.framework.TestCase;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;


import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;
import de.lichtflut.rb.core.schema.model.impl.CardinalityFactory;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.persistence.RBSchemaStore;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.core.spi.RBServiceProviderFactory;


/**
 * Testcase to test ResourceTypeInstance- validators, ticket-algorithms and constraints
 * 
 * Created: May 20, 2011
 *
 * @author Nils Bleisch
 */
public class ResourceTypeInstanceTest extends TestCase{

	@SuppressWarnings("unchecked")
	public void testResourceTypeInstance(){
		RBServiceProvider provider = RBServiceProviderFactory.getDefaultServiceProvider();
		
		ResourceSchema schema = createSchema();
		//Store the schema
		provider.getResourceSchemaManagement().storeOrOverrideResourceSchema(schema);
		
		//Build an instance
		ResourceTypeInstance<Object> instance = schema.generateTypeInstance();
		assertNotNull(instance);
		
		try{
			instance.addValueFor("http://lichtflut.de#hatGeburtstag", "test1");
			instance.addValueFor("http://lichtflut.de#hatEmail", "test1@test.com");
			instance.addValueFor("http://lichtflut.de#hatAlter", "24");
		
		}catch(Exception any){
			any.printStackTrace();
		}
		//Try to create this
		assertTrue(provider.getResourceTypeManagement().createOrUpdateRTInstance(instance));
		Collection<ResourceTypeInstance> instances = provider.getResourceTypeManagement().loadAllResourceTypeInstancesForSchema(schema);
		
		assertTrue(instances.size()==1);
		for (ResourceTypeInstance i : instances) {
			assertTrue(i.getValuesFor("http://lichtflut.de#hatGeburtstag").contains("test1"));
			assertTrue(i.getValuesFor("http://lichtflut.de#hatEmail").contains("test1@test.com"));
			assertTrue(i.getValuesFor("http://lichtflut.de#hatAlter").contains("24"));
		}	
	}
	
	
	
	
	private ResourceSchema createSchema() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl("http://lichtflut.de#","personschema");
		PropertyDeclarationImpl p1 = new PropertyDeclarationImpl(); 
		PropertyDeclarationImpl p2 = new PropertyDeclarationImpl(); 
		PropertyDeclarationImpl p3 = new PropertyDeclarationImpl(); 
		p1.setName("http://lichtflut.de#geburtsdatum");
		p2.setName("http://lichtflut.de#email");
		p3.setName("http://lichtflut.de#alter");
		
		p1.setElementaryDataType(ElementaryDataType.STRING);
		p2.setElementaryDataType(ElementaryDataType.STRING);
		p3.setElementaryDataType(ElementaryDataType.INTEGER);
		
		p2.addConstraint(ConstraintFactory.buildConstraint(".*@.*"));
		PropertyAssertionImpl pa1 = new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de#","hatGeburtstag"), p1);
		PropertyAssertionImpl pa2 = new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de#","hatEmail"), p2);
		PropertyAssertionImpl pa3 = new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de#","hatAlter"), p3);
		pa1.setCardinality(CardinalityFactory.hasExcactlyOne());
		pa2.setCardinality(CardinalityFactory.hasAtLeastOneUpTo(2));
		pa3.setCardinality(CardinalityFactory.hasExcactlyOne());
		
		schema.addPropertyAssertion(pa1);
		schema.addPropertyAssertion(pa2);
		schema.addPropertyAssertion(pa3);
		
		return schema;
	}
	
}