/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.Dialog;

import java.util.ArrayList;
import java.util.List;


import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.lichtflut.rb.mock.schema.PropertyDeclarationFactory;
import de.lichtflut.rb.webck.components.dialogs.EditPropertyDeclDialog;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;

/**
 * <p>
 * Test for {@link EditPropertyDeclDialog}.
 * </p>
 * 
 * <p>
 * Created Mar 1, 2012
 * </p>
 * 
 * @author Ravi Knox
 */
public class EditTypePropertyDeclDialogTest {

	private IModel<List<PropertyRow>> decls;
	private WicketTester tester;
	
	@Before
	public void setUp(){
		tester = new WicketTester();
	}
	
	@Test
	public void testdialogWithEmptyList(){
		List<PropertyRow> list = new ArrayList<PropertyRow>();
		decls = new ListModel<PropertyRow>(list);
		EditPropertyDeclDialog panel = new EditPropertyDeclDialog("test", decls);
		tester.startComponentInPage(panel);
		tester.assertNoInfoMessage();
		tester.assertContains("No Properties to edit");
	}
	
	@Test
	@Ignore(value="Fix me")
	public void testDialogWithSinglePropertyDecl(){
		List<PropertyRow> list = new ArrayList<PropertyRow>();
		list.add(new PropertyRow(PropertyDeclarationFactory.buildPersonPropertyDecls().get(0)));
		decls = new ListModel<PropertyRow>(list);
		EditPropertyDeclDialog panel = new EditPropertyDeclDialog("test", decls);
		tester.startComponentInPage(panel);
		tester.assertNoErrorMessage();
		tester.assertNoInfoMessage();
		tester.assertContains("Property");
		tester.assertContains("hatKind");
		tester.assertContains("Field Label");
		tester.assertContains("Kinder");
		tester.assertContains("Cardinality");
		tester.assertContains("[1..2]");
		tester.assertContains("Datatype");
		tester.assertContains("Resource");
		tester.assertContains("Constraints");
		tester.assertContains("Person");
	}
	 
	@Test
	@Ignore(value="Fix me")
	public void testDialogWithMultiplePropertyDecl(){
		List<PropertyRow> list = new ArrayList<PropertyRow>();
		list.add(new PropertyRow(PropertyDeclarationFactory.buildPersonPropertyDecls().get(0)));
		list.add(new PropertyRow(PropertyDeclarationFactory.buildPersonPropertyDecls().get(1)));
		decls = new ListModel<PropertyRow>(list);
		EditPropertyDeclDialog panel = new EditPropertyDeclDialog("test", decls);
		tester.startComponentInPage(panel);
		tester.assertNoErrorMessage();
		tester.assertNoInfoMessage();
		tester.assertContains("Property");
		tester.assertContainsNot("hatKind");
		tester.assertContains("Field Label");
		tester.assertContains("Kinder, hat Alter");
		tester.assertContains("Cardinality");
		tester.assertContains("[1..2]");
		tester.assertContains("Datatype");
		tester.assertContains("Resource");
		tester.assertContains("Constraints");
		tester.assertContains("Person");
	}
	
}