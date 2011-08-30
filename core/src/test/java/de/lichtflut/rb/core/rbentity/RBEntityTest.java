/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.rbentity;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.RBEntity;
import de.lichtflut.rb.core.schema.model.RBEntity.MetaDataKeys;
import de.lichtflut.rb.core.schema.model.RBInvalidAttributeException;
import de.lichtflut.rb.core.schema.model.RBInvalidValueException;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.spi.INewRBServiceProvider;
import de.lichtflut.rb.core.spi.RBServiceProviderFactory;

/**
 * <p>
 * Testcase to test ResourceTypeInstance- validators, ticket-algorithms and
 * constraints.
 * </p>
 *
 * Created: May 20, 2011
 *
 * @author Nils Bleisch
 */
public final class RBEntityTest {

    /**
     *
     */
    @Test
    public void testNewRBEntityManagement() {

    }

    /**
     * @param
     */
    @Test
    public void testResourceTypeInstance() {
        // Generate an instance for a given schema
        RBEntity<Object> instance = createPersonSchema().generateRBEntity();
        Assert.assertNotNull(instance);

        // Generate some tickets for fields

        int ticket_hatGeburtstag1 = -1, ticket_hatGeburtstag2 = -1, // hatGeburtstag
                                                                    // is a
                                                                    // hasExacltyOne
                                                                    // Property
        ticket_unknownAttribute = -1, // This attribute does not exists
        ticket_hatEmail1 = -1, ticket_hatEmail2 = -1, ticket_hatAlter = -1;

        try {
            ticket_hatGeburtstag1 = instance
                    .generateTicketFor("http://lichtflut.de#hatGeburtstag");
            ticket_hatEmail1 = instance
                    .generateTicketFor("http://lichtflut.de#hatEmail");
            ticket_hatEmail2 = instance
                    .generateTicketFor("http://lichtflut.de#hatEmail");
            ticket_hatAlter = instance
                    .generateTicketFor("http://lichtflut.de#hatAlter");
            // Try to generate a second ticket for 'Geburtstag' which should not
            // be possible an trigger an exception
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(ticket_hatGeburtstag2 == -1
                && ticket_unknownAttribute == -1);
        Assert.assertTrue(ticket_hatGeburtstag1 != -1 && ticket_hatEmail1 != -1
                && ticket_hatEmail2 != -1 && ticket_hatAlter != -1);
        // Try to write an value to hatGeburtstag with the given ticket.
        boolean exceptionOccured = false;
        try {
            instance.addValueFor("http://lichtflut.de#xyz", "hans");
            instance.addValueFor("http://lichtflut.de#hatGeburtstag", "test",
                    ticket_hatGeburtstag1);
            Assert.assertTrue((instance.getValueFor(
                    "http://lichtflut.de#hatGeburtstag", ticket_hatGeburtstag1))
                    .equals("test"));
            // Try to revoke the ticket, which should not be possible caused by
            // the hasExacltyOneCardinality
            exceptionOccured = false;
            instance.releaseTicketFor("http://lichtflut.de#hatGeburtstag",
                    ticket_hatGeburtstag1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            exceptionOccured = true;
        }

        Assert.assertTrue(exceptionOccured);
        exceptionOccured = false;

        int ticket_hatEmail3 = -1;

        try {
            /*
             * Try to generate a new Ticket for hatEmail, which should not be
             * possible, because there are already two existing ones, which is
             * the maximum
             */
            ticket_hatEmail3 = instance
                    .generateTicketFor("http://lichtflut.de#hatEmail");
        } catch (Exception any) {
            System.out.println("ok! " + any.getMessage());
        }
        // ticket_hatEmail3 must be still on it's intial value '-1'
        Assert.assertTrue(ticket_hatEmail3 == -1);

        try {
            exceptionOccured = true;
            instance.addValueFor("http://lichtflut.de#hatEmail", "email@1",
                    ticket_hatEmail1);
            instance.addValueFor("http://lichtflut.de#hatEmail", "email@2",
                    ticket_hatEmail2);
            Assert.assertTrue((instance.getValueFor(
                    "http://lichtflut.de#hatEmail", ticket_hatEmail1))
                    .equals("email@1"));
            Assert.assertTrue((instance.getValueFor(
                    "http://lichtflut.de#hatEmail", ticket_hatEmail2))
                    .equals("email@2"));
            instance.releaseTicketFor("http://lichtflut.de#hatEmail",
                    ticket_hatEmail1);
            // Generate hatEmail3-ticket
            ticket_hatEmail3 = instance
                    .generateTicketFor("http://lichtflut.de#hatEmail");
            // Check for ticket1, which should not exists anymore
            Assert.assertNull(instance.getValueFor(
                    "http://lichtflut.de#hatEmail", ticket_hatEmail1));
            exceptionOccured = false;
        } catch (Exception any) {
            System.out.println("ok! " + any.getMessage());
        }
        Assert.assertFalse(exceptionOccured);
        exceptionOccured = false;

        try {
            Assert.assertNull((instance.getValueFor(
                    "http://lichtflut.de#hatEmail", ticket_hatEmail3)));
            instance.addValueFor("http://lichtflut.de#hatEmail", "email@3",
                    ticket_hatEmail3);
            Assert.assertTrue((instance.getValueFor(
                    "http://lichtflut.de#hatEmail", ticket_hatEmail3))
                    .equals("email@3"));
        } catch (Exception any) {
            any.printStackTrace();
        }

        // Check the build-in validation stuff. hatAlter's Datatype is Integer,
        // let's try to add an alphanumeric string

        String message = "";
        try {
            instance.addValueFor("http://lichtflut.de#hatAlter", "abc0153",
                    ticket_hatAlter);
        } catch (Exception any) {
            message = any.getMessage();
        }
        Assert.assertTrue(message.toLowerCase().contains(
                "is not a valid value for the expected type integer"));
        Assert.assertNull(instance.getValueFor("http://lichtflut.de#hatAlter",
                ticket_hatAlter));

    }

    /**
     * @param
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void testResourceType() {

        INewRBServiceProvider provider = RBServiceProviderFactory
                .getDefaultServiceProvider();

        boolean invalidValueExeptionThrown = false;

        ResourceSchema personSchema = createPersonSchema();

        ResourceSchema carSchema = createCarSchema(personSchema);

        provider.getResourceSchemaManagement().storeOrOverrideResourceSchema(
                personSchema);
        RBEntity<Object> person0 = personSchema.generateRBEntity();
        Assert.assertNotNull(person0);
        RBEntity<Object> person1 = personSchema.generateRBEntity();
        Assert.assertNotNull(person1);

        provider.getResourceSchemaManagement().storeOrOverrideResourceSchema(
                carSchema);
        RBEntity<Object> car0 = carSchema.generateRBEntity();

        int ticket0 = -1;
        int ticket1 = -1;
        try {

            person0.addValueFor("http://lichtflut.de#hatGeburtstag", "test");
            ticket0 = person0.addValueFor("http://lichtflut.de#hatEmail",
                    "hans@hans.de");
            person0.addValueFor("http://lichtflut.de#hatAlter", "5");

            person1.addValueFor("http://lichtflut.de#hatGeburtstag", "test1");
            person1.addValueFor("http://lichtflut.de#hatEmail", "peter@hans.de");
            person1.addValueFor("http://lichtflut.de#hatAlter", "32");
            person1.addValueFor("http://lichtflut.de#hatKind", person0);

            car0.addValueFor("http://lichtflut.de#hatMarke", "Audi quattro");
            car0.addValueFor("http://lichtflut.de#hatModell", "a");
            String invalidValueMessage = "";
            try {
                car0.addValueFor("http://lichtflut.de#hatAlter", 4);

            } catch (RBInvalidValueException e) {
                invalidValueMessage = e.getMessage();
            }
            Assert.assertTrue(invalidValueMessage
                    .equals("\"4\" does not match \"[1-3]\""));
            ticket1 = car0
                    .addValueFor("http://lichtflut.de#hatHalter", person1);

        } catch (RBInvalidValueException e) {
            e.printStackTrace();
            invalidValueExeptionThrown = true;
        } catch (RBInvalidAttributeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertFalse(invalidValueExeptionThrown);

        RBEntity entity = (RBEntity) car0.getValueFor(
                "http://lichtflut.de#hatHalter", ticket1);

        System.out.println("-->"
                + car0.getMetaInfoFor("http://lichtflut.de#hatHalter",
                        MetaDataKeys.TYPE));

        Assert.assertTrue(((String) entity.getValueFor(
                "http://lichtflut.de#hatEmail", ticket0))
                .contains("peter@hans.de"));

    }

    /**
     * Test sortByAttribute method of RBEntity.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testSortByAttribute() {
        List<RBEntity> list = new ArrayList<RBEntity>();
        String attribute = "http://lichtflut.de#hatEmail";
        String emailA = "a@web.de";
        String emailG = "gTest@web.de";
        String emailAAF = "aaf@xyz.com";
        String emailAV = "av@yahoo.com";
        String emailM = "m@web.de";

        // Generate and fill RBEntites
        RBEntity e1 = createPersonSchema().generateRBEntity();
        RBEntity e2 = createPersonSchema().generateRBEntity();
        RBEntity e3 = createPersonSchema().generateRBEntity();
        RBEntity e4 = createPersonSchema().generateRBEntity();
        RBEntity e5 = createPersonSchema().generateRBEntity();
        try {
            e4.addValueFor(attribute, emailG);
            e1.addValueFor(attribute, emailA);
            e2.addValueFor(attribute, emailM);
            e3.addValueFor(attribute, emailAV);
            e5.addValueFor(attribute, emailAAF);
        } catch (RBInvalidValueException e) {
            e.printStackTrace();
        } catch (RBInvalidAttributeException e) {
            e.printStackTrace();
        }
        list.add(e1);
        list.add(e2);
        list.add(e3);
        list.add(e4);
        list.add(e5);
        RBEntity.sortByAttribute(list, attribute, true);

        Assert.assertTrue(list.get(0).getValueFor(attribute, 0).equals(emailA));
        Assert.assertTrue(list.get(1).getValueFor(attribute, 0)
                .equals(emailAAF));
        Assert.assertTrue(list.get(2).getValueFor(attribute, 0).equals(emailAV));
        Assert.assertTrue(list.get(3).getValueFor(attribute, 0).equals(emailG));
        Assert.assertTrue(list.get(4).getValueFor(attribute, 0).equals(emailM));

        RBEntity.sortByAttribute(list, attribute, false);
        System.out.println(list.get(4).getValueFor(attribute, 0));
        Assert.assertTrue(list.get(0).getValueFor(attribute, 0).equals(emailM));
        Assert.assertTrue(list.get(1).getValueFor(attribute, 0).equals(emailG));
        Assert.assertTrue(list.get(2).getValueFor(attribute, 0).equals(emailAV));
        Assert.assertTrue(list.get(3).getValueFor(attribute, 0)
                .equals(emailAAF));
        Assert.assertTrue(list.get(4).getValueFor(attribute, 0).toString()
                .equals(emailA));
    }

    /**
     *
     */
    @Test
    public void testNonSchemaProperties() {
        ResourceSchema schema = createPersonSchema();
        RBEntity instance = schema.generateRBEntity();
        try {
            int a = instance.addValueFor("http://lichtflut.de#hatOhren", true);
            instance.addValueFor("http://lichtflut.de#hatOhren", false, a);
            System.out.println(instance.getValueFor(
                    "http://lichtflut.de#hatOhren", a));
        } catch (Exception any) {
            any.printStackTrace();
        }
    }

    /**
     * @return schema
     */
    private ResourceSchema createPersonSchema() {
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

    /**
     * @return schema
     * @param referredSchema
     *            -
     */
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
