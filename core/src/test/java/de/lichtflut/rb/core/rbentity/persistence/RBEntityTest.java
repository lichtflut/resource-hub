/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.rbentity.persistence;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;
<<<<<<< HEAD
import org.junit.Assert;
=======
>>>>>>> 7cf153d55197264c0e0c547b580f2980bb05874d
import org.junit.Test;

import de.lichtflut.rb.core.api.RBEntityManager;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.NewRBEntity;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.RBField;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.spi.RBServiceProviderFactory;


/**
 * Testcase to test ResourceTypeInstance- validators, ticket-algorithms and constraints.
 *
 * Created: May 20, 2011
 *
 * @author Nils Bleisch
 * TODO: FIX TESTS!!!
 */
public final class RBEntityTest {

//    /**
//     * Test to persist and find entities.
//     */
//    @SuppressWarnings("rawtypes")
//    @Test
//    public void testPersistAndFindRBEntities() {
//        RBServiceProvider provider = RBServiceProviderFactory
//                .getDefaultServiceProvider();
//
//        ResourceSchema schema = createSchema();
//        // Store the schema
//        provider.getResourceSchemaManagement().storeOrOverrideResourceSchema(
//                schema);
//
//        // Build an instance
//        RBEntity<Object> instance = schema.generateRBEntity();
//        Assert.assertNotNull(instance);
//
//        try {
//            instance.addValueFor("http://lichtflut.de#hatGeburtstag", "test1");
//            instance.addValueFor("http://lichtflut.de#hatEmail",
//                    "test1@test.com");
//            instance.addValueFor("http://lichtflut.de#hatAlter", "24");
//            instance.addValueFor("http://lichtflut.de#hatOhren", "zwei");
//
//        } catch (Exception any) {
//            any.printStackTrace();
//        }
//        // Try to create this
//        Assert.assertTrue(provider.getRBEntityManagement()
//                .createOrUpdateEntity(instance));
//        RBEntityManagementImpl rbManagement = (RBEntityManagementImpl) provider
//                .getRBEntityManagement();
//        Collection<RBEntity> instances = rbManagement
//                .loadAllEntitiesForSchema(schema);
//
//        Assert.assertTrue(instances.size() == 1);
//        for (RBEntity i : instances) {
//            Assert.assertTrue(i.getValuesFor(
//                    "http://lichtflut.de#hatGeburtstag").contains("test1"));
//            Assert.assertTrue(i.getValuesFor("http://lichtflut.de#hatEmail")
//                    .contains("test1@test.com"));
//            Assert.assertTrue(i.getValuesFor("http://lichtflut.de#hatAlter")
//                    .contains("24"));
//            // Assert.assertTrue(i.getValuesFor(null).contains("zwei"));
//        }
//    }
//
//    /**
//     * Test to persist and find the specific entity.
//     */
//    @SuppressWarnings("rawtypes")
//    @Test
//    public void testPersistAndFindASpecificEntity() {
//        RBServiceProvider provider = RBServiceProviderFactory
//                .getDefaultServiceProvider();
//
//        ResourceSchema schema = createSchema();
//        // Store the schema
//        provider.getResourceSchemaManagement().storeOrOverrideResourceSchema(
//                schema);
//        // Build an instance
//        RBEntity<Object> p0 = schema.generateRBEntity();
//        Assert.assertNotNull(p0);
//        RBEntity<Object> p1 = schema.generateRBEntity();
//        Assert.assertNotNull(p1);
//        try {
//
//            p0.addValueFor("http://lichtflut.de#hatGeburtstag", "test2");
//            p0.addValueFor("http://lichtflut.de#hatEmail", "hans@test.com");
//            p0.addValueFor("http://lichtflut.de#hatAlter", "2");
//
//            provider.getRBEntityManagement().createOrUpdateEntity(p0);
//
//            p1.addValueFor("http://lichtflut.de#hatGeburtstag", "test1");
//            p1.addValueFor("http://lichtflut.de#hatEmail", "test1@test.com");
//            p1.addValueFor("http://lichtflut.de#hatAlter", "24");
//            // p1.addValueFor("http://lichtflut.de#hatKind", p0);
//
//        } catch (Exception any) {
//            any.printStackTrace();
//        }
//
//        Assert.assertTrue(provider.getRBEntityManagement()
//                .createOrUpdateEntity(p0));
//        Assert.assertTrue(provider.getRBEntityManagement()
//                .createOrUpdateEntity(p1));
//        Collection<RBEntity> instances = provider.getRBEntityManagement()
//                .loadAllEntitiesForSchema(schema);
//        // Assert.assertTrue(instances.size()==2);
//        RBEntity entity = new ArrayList<RBEntity>(instances).get(0);
//        // Made some proofs
//
//        Assert.assertEquals(
//                provider.getRBEntityManagement().loadEntity(entity), entity);
//        Assert.assertEquals(
//                provider.getRBEntityManagement().loadEntity(
//                        entity.getQualifiedName()), entity);
//        Assert.assertEquals(
//                provider.getRBEntityManagement().loadEntity(
//                        entity.getQualifiedName().toURI()), entity);
//        // Add a hash on the entities identifier to generate an identifier which
//        // shouldnt exists.
//        // Now try to assert that loadRBEntity should return null for those
//        // identifiers
//        Assert.assertNull(provider.getRBEntityManagement().loadEntity(
//                entity.getQualifiedName().toURI() + "#"));
//    }
//
    /**
     *
     */
    @Test
    public void testNewRBEntity() {
    	// Create a shema
        ResourceSchema schema = createSchema();

        // Create two entitys with given shema
        NewRBEntity e = new NewRBEntity(schema);
        NewRBEntity e1 = new NewRBEntity(schema);

        // Get Entitymanager
        RBEntityManager m = RBServiceProviderFactory.getDefaultServiceProvider().getRBEntityManagement();

        // Add a Email to the entities
        e.getField("http://lichtflut.de#email").getFieldValues()
                .add("mutter@fam.com");

        // Store entity
        m.store(e);

        // Add Field to entity
        e1.getField("http://lichtflut.de#email").getFieldValues()
                .add("kind@fam.com");

        // Add a custom Field
        RBField newField = new RBField(new SimpleResourceID("http://lichtflut.de#whatever"), null);
        newField.getFieldValues().add("haha");
        newField.getFieldValues().add("hoho");
        newField.getFieldValues().add("muhahaha");
        e1.addField(newField);

        // Add entity as field
        e.getField("http://lichtflut.de#kind").getFieldValues().add(e1);

        // store entities
        m.store(e);
<<<<<<< HEAD
        m.store(e1);

        // Tests
        Assert.assertTrue(m.find(e.getID()).getAllFields().size()==3);
        Assert.assertTrue(m.findAllByType(new SimpleResourceID("http://lichtflut.de#personschema")).size()==2);

=======
        System.out.println("type:"+m.find(e.getID()).getAllFields().size());
        System.out.println("id-->"+m.findAllByType(new SimpleResourceID("http://lichtflut.de#personschema")).size());
>>>>>>> 7cf153d55197264c0e0c547b580f2980bb05874d
        // System.out.println(MockNewRBEntityFactory.createNewRBEntity().getID());
    }

    /**
     * Tests.
     * @return shema
     */
    private ResourceSchema createSchema() {
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
