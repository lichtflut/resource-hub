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
package de.lichtflut.rb.webck.components.Dialog;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.webck.components.dialogs.EditPropertyDeclDialog;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.data.schema.PropertyDeclarationFactory;
import de.lichtflut.rb.webck.data.schema.ResourceSchemaFactory;

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

	private IModel<PropertyRow> decl;
	private WicketTester tester;

	@Before
	public void setUp(){
		tester = new WicketTester();
	}

	@Test
	@Ignore(value="Fix me")
	public void testDialogWithSinglePropertyDecl(){
		decl = Model.of(new PropertyRow(PropertyDeclarationFactory.buildPersonPropertyDecls().get(0)));
		ResourceSchema schema = ResourceSchemaFactory.buildPersonSchema();
		EditPropertyDeclDialog panel = new EditPropertyDeclDialog("test", decl, Model.of(schema));
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

}