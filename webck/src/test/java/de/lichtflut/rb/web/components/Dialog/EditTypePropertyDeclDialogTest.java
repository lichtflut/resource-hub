/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.Dialog;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.tester.WicketTester;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Before;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
import de.lichtflut.rb.webck.components.dialogs.EditTypePropertyDeclDialog;

/**
 * <p>
 * [DESCRIPTION]
 * </p>
 * 
 * <p>
 * Created Mar 1, 2012
 * </p>
 * 
 * @author Ravi Knox
 */
public class EditTypePropertyDeclDialogTest {

	private IModel<List<PropertyDeclaration>> decls;
	private WicketTester tester;
	
	@Before
	public void setUp(){
		tester = new WicketTester();
	}
	
	@Test
	public void testdialogWithEmptyList(){
		List<PropertyDeclaration> list = new ArrayList<PropertyDeclaration>();
		decls = new ListModel<PropertyDeclaration>(list);
		EditTypePropertyDeclDialog panel = new EditTypePropertyDeclDialog("test", decls);
		tester.startComponentInPage(panel);
//		tester.assertErrorMessages("No Properties to edit");
		tester.assertNoInfoMessage();
		tester.assertContains("No Properties to edit");
	}
	
	@Test
	public void testdialogWithSinglePropertyDecl(){
		List<PropertyDeclaration> list = new ArrayList<PropertyDeclaration>();
		list.add(createDecl().get(0));
		decls = new ListModel<PropertyDeclaration>(list);
		EditTypePropertyDeclDialog panel = new EditTypePropertyDeclDialog("test", decls);
		tester.startComponentInPage(panel);
		tester.assertNoErrorMessage();
		tester.assertNoInfoMessage();
		tester.assertContains("Property");
		tester.assertContains("hatKind");
		tester.assertContains("Field Label");
		tester.assertContains("Kinder");
		tester.assertContains("Minimum");
		tester.assertContains("0");
		tester.assertContains("Maximum");
		tester.assertContains(String.valueOf(Integer.MAX_VALUE));
		tester.assertContains("Datatype");
		tester.assertContains("Resource");
		tester.assertContains("Constraints");
		tester.assertContains("Person");
	}
	
	@Test
	public void testDialogWithMultiplePropertyDecl(){
//		List<PropertyDeclaration> list = new ArrayList<PropertyDeclaration>();
//		list.addAll(createDecl());
//		decls = new ListModel<PropertyDeclaration>(list);
//		EditTypePropertyDeclDialog panel = new EditTypePropertyDeclDialog("test", decls);
//		tester.startComponentInPage(panel);
	}
	/**
	 * Tests.
	 * 
	 * @return shema
	 */
	private List<PropertyDeclaration> createDecl() {

		TypeDefinitionImpl p1 = new TypeDefinitionImpl();
		TypeDefinitionImpl p2 = new TypeDefinitionImpl();
		TypeDefinitionImpl p3 = new TypeDefinitionImpl();
		TypeDefinitionImpl p4 = new TypeDefinitionImpl();

		p1.setElementaryDataType(Datatype.STRING);
		p2.setElementaryDataType(Datatype.STRING);
		p3.setElementaryDataType(Datatype.INTEGER);
		p4.setElementaryDataType(Datatype.RESOURCE);

		ResourceSchemaImpl schema = new ResourceSchemaImpl(
	        		new SimpleResourceID("http://lf.de#", "Person"));
		 
		p2.addConstraint(ConstraintBuilder.buildConstraint(".*@.*"));
		p4.addConstraint(ConstraintBuilder.buildConstraint(schema.getDescribedType()));

		PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#",
				"hatGeburtstag"), p1);
		PropertyDeclarationImpl pa2 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#",
				"hatEmail"), p2);
		PropertyDeclarationImpl pa3 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#",
				"hatAlter"), p3);
		PropertyDeclarationImpl pa4 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#",
				"hatKind"), p4);

		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		pa2.setCardinality(CardinalityBuilder.hasAtLeastOneUpTo(2));
		pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());
		pa4.setCardinality(CardinalityBuilder.hasOptionalOneToMany());

		pa1.setFieldLabelDefinition(new FieldLabelDefinitionImpl("hat Geburtstag"));
		pa2.setFieldLabelDefinition(new FieldLabelDefinitionImpl("hat Email"));
		pa3.setFieldLabelDefinition(new FieldLabelDefinitionImpl("hat Alter"));
		pa4.setFieldLabelDefinition(new FieldLabelDefinitionImpl("Kinder"));

		List<PropertyDeclaration> list = new ArrayList<PropertyDeclaration>();
		list.add(pa4);
		list.add(pa3);
		list.add(pa2);
		list.add(pa1);
		
		return list;
	}
}