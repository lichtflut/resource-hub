/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.rbentity.persistence;

import java.util.Collections;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.junit.Assert;
import org.junit.Test;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.entity.RBEntityReference;
import de.lichtflut.rb.core.entity.impl.AbstractRBField;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
import de.lichtflut.rb.core.entity.impl.UndeclaredRBField;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.services.impl.DefaultRBServiceProvider;


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
        RBEntityImpl e1 = new RBEntityImpl(schema);
        RBEntityImpl e2 = new RBEntityImpl(schema);

        ServiceProvider sp = new DefaultRBServiceProvider(new RBConfig());
        
        // Get Entitymanager
        EntityManager m = sp.getEntityManager();
        SchemaManager sm = sp.getSchemaManager();

        sm.store(schema);
        System.out.println("**********"+sm.findAllResourceSchemas().size());

        // Add a Email to the entities
        e1.getField(new SimpleResourceID("http://lichtflut.de#hatEmail")).addValue("mutter@fam.com");

        // Store entity
        m.store(e1);

        // Add Field to entity
        e2.getField(new SimpleResourceID("http://lichtflut.de#hatEmail")).addValue("kind@fam.com");

        // Add a custom Field
        AbstractRBField newField = new UndeclaredRBField(
        		new SimpleResourceID("http://lichtflut.de#whatever"), 
        		Collections.<SemanticNode>emptySet());
        newField.addValue("haha");
        newField.addValue("hoho");
        newField.addValue("muhahaha");
        e2.addField(newField);

        // Add entity as field
        e1.getField(new SimpleResourceID("http://lichtflut.de#hatKind"))
        	.addValue(new RBEntityReference(e2));

        // store entities
        m.store(e1);
        m.store(e2);

        // Tests
        System.out.println(m.find(e1.getID()).getAllFields());
        Assert.assertEquals(4, m.find(e1.getID()).getAllFields().size());
        Assert.assertEquals(2, m.findByType(new SimpleResourceID("http://lichtflut.de#personschema")).size());

        m.delete(e1);
        System.out.println("-->"+m.find(e1.getID()));
        // System.out.println(MockNewRBEntityFactory.createNewRBEntity().getID());
    }

    /**
     * Tests.
     * @return shema
     */
    private ResourceSchema createSchema() {
        ResourceSchemaImpl schema = new ResourceSchemaImpl(
        		new SimpleResourceID("http://lichtflut.de#", "personschema"));
        
        TypeDefinitionImpl p1 = new TypeDefinitionImpl();
        TypeDefinitionImpl p2 = new TypeDefinitionImpl();
        TypeDefinitionImpl p3 = new TypeDefinitionImpl();
        TypeDefinitionImpl p4 = new TypeDefinitionImpl();

        p1.setElementaryDataType(ElementaryDataType.STRING);
        p2.setElementaryDataType(ElementaryDataType.STRING);
        p3.setElementaryDataType(ElementaryDataType.INTEGER);
        p4.setElementaryDataType(ElementaryDataType.RESOURCE);

        p2.addConstraint(ConstraintBuilder.buildConstraint(".*@.*"));
        p4.addConstraint(ConstraintBuilder.buildConstraint(schema
                .getDescribedType()));

        PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(
                new SimpleResourceID("http://lichtflut.de#", "hatGeburtstag"),
                p1);
        PropertyDeclarationImpl pa2 = new PropertyDeclarationImpl(
                new SimpleResourceID("http://lichtflut.de#", "hatEmail"), p2);
        PropertyDeclarationImpl pa3 = new PropertyDeclarationImpl(
                new SimpleResourceID("http://lichtflut.de#", "hatAlter"), p3);
        PropertyDeclarationImpl pa4 = new PropertyDeclarationImpl(
                new SimpleResourceID("http://lichtflut.de#", "hatKind"), p4);

        pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
        pa2.setCardinality(CardinalityBuilder.hasAtLeastOneUpTo(2));
        pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());
        pa4.setCardinality(CardinalityBuilder.hasOptionalOneToMany());
        
        pa1.setFieldLabelDefinition(new FieldLabelDefinitionImpl("hatGeburtstag"));
        pa2.setFieldLabelDefinition(new FieldLabelDefinitionImpl("hatEmail"));
        pa3.setFieldLabelDefinition(new FieldLabelDefinitionImpl("hatAlter"));
        pa4.setFieldLabelDefinition(new FieldLabelDefinitionImpl("hatKind"));

        schema.addPropertyDeclaration(pa1);
        schema.addPropertyDeclaration(pa2);
        schema.addPropertyDeclaration(pa3);
        schema.addPropertyDeclaration(pa4);

        return schema;
    }

}
