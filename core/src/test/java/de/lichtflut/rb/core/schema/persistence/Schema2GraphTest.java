/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */

package de.lichtflut.rb.core.schema.persistence;

import junit.framework.Assert;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.junit.Test;
import org.openrdf.model.impl.CalendarLiteralImpl;

import com.sun.corba.se.spi.resolver.Resolver;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
import de.lichtflut.rb.core.services.impl.DefaultRBServiceProvider;

/**
 * <p>
 *  Test case for converting Resource Schemas to Arastreju Graphs.
 * </p>
 *
 * <p>
 * 	Created Dez 10, 2011
 * </p>
 *
 * @author Raphael Esterle
 */
public class Schema2GraphTest {
	
	private Schema2GraphBinding binding;
	
	@Test
	public void testToSemanticNode(){
		ResourceSchema schema = createSchema();
		Assert.assertNotNull(schema);
		
		SNResourceSchema snr = getBinding().toSemanticNode(schema);
		
		Assert.assertNotNull(snr);
		Assert.assertTrue(snr.getDescribedType().equals(schema.getDescribedType()));
		Assert.assertTrue(snr.getPropertyDeclarations().size()==schema.getPropertyDeclarations().size());
	}
	
	@Test
	public void testToModelConstraint(){
		
		TypeDefinition td = new TypeDefinitionImpl();
		td.setElementaryDataType(Datatype.STRING);
		Constraint c = ConstraintBuilder.buildConstraint("*.A*");
		td.addConstraint(c);
		
		Assert.assertNotNull(td);
		
		SNPropertyTypeDefinition snp = getBinding().toSemanticNode(td);
		
		Assert.assertNotNull(snp);
		Assert.assertEquals(Datatype.STRING, snp.getDatatype());
		SNConstraint snc = (SNConstraint) snp.getConstraints().toArray()[0];
		
		Assert.assertEquals(c.getLiteralConstraint(), getBinding().toModelConstraint(snc).getLiteralConstraint());
	}
	
	@Test
	public void testToModelObject(){
		
		ResourceSchema schema = createSchema();
		SNResourceSchema snSchema = getBinding().toSemanticNode(schema);
		ResourceSchema cSchema = getBinding().toModelObject(snSchema);
		
		Assert.assertEquals(schema, cSchema);
		
	}
	
	@Test
	public void testBuildCardinalety(){
		
		TypeDefinition tDef = new TypeDefinitionImpl();
		tDef.setElementaryDataType(Datatype.STRING);
		tDef.addConstraint(ConstraintBuilder.buildConstraint("*@*"));
		
		
	}
	
	@Test 
	public void testBuildConstrains(){
		
	}
	
	private Schema2GraphBinding getBinding(){
		if(null==binding){
			binding = new Schema2GraphBinding(null);
		}
		return binding;
	}
	
	private ResourceSchema createSchema() {
        ResourceSchemaImpl schema = new ResourceSchemaImpl(
        		new SimpleResourceID("http://lf.de#", "Person"));
        
        TypeDefinitionImpl p1 = new TypeDefinitionImpl();
        TypeDefinitionImpl p2 = new TypeDefinitionImpl();
        TypeDefinitionImpl p3 = new TypeDefinitionImpl();
        TypeDefinitionImpl p4 = new TypeDefinitionImpl();

        p1.setElementaryDataType(Datatype.STRING);
        p2.setElementaryDataType(Datatype.STRING);
        p3.setElementaryDataType(Datatype.INTEGER);
        p4.setElementaryDataType(Datatype.RESOURCE);

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
