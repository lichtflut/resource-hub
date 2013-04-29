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
package de.lichtflut.rb.webck.components.typesystem.schema;

import static org.mockito.Mockito.when;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.webck.RBWebTest;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.data.schema.ResourceSchemaFactory;
/**
 * <p>
 *  Testcase for {@link SchemaDetailPanel}.
 * </p>
 *
 * <p>
 * 	Created Feb 27, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class SchemaDetailPanelTest extends RBWebTest{

	// ------------- SetUp & tearDown -----------------------

	@Override
	protected void setupTest() {
	}

	// ------------------------------------------------------

	@Test
	public void testSchemaDetailPanel(){
		ResourceSchema schema = ResourceSchemaFactory.buildPersonSchema();
		IModel<ResourceSchema> model = Model.of(schema);
		when(networkService.resolve(schema.getDescribedType())).thenReturn(schema.getDescribedType().asResource());
		when(serviceContext.getDomain()).thenReturn("test");
		when(pathBuilder.queryClasses("test", null)).thenReturn("blablabla");

		Panel panel = new SchemaDetailPanel("panel", model);
		tester.startComponentInPage(panel);
		assertRenderedPanel(SchemaDetailPanel.class, "panel");
		tester.assertNoErrorMessage();
		tester.assertNoInfoMessage();
		tester.assertComponent("panel:form:deleteButton", RBStandardButton.class);
		tester.assertComponent("panel:form:addButton", RBStandardButton.class);
		tester.assertContains(schema.getDescribedType().toURI());
	}

}
