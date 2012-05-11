/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.typesystem;


import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.mock.schema.ResourceSchemaFactory;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.components.typesystem.schema.SchemaDetailPanel;

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
public class SchemaDetailPanelTest{
	
	private WicketTester tester;
	
	@Before
	public void setUp() {
		tester = new WicketTester();
	}

	@Test
	@Ignore(value="Fix me")
	public void testPanel(){
		ResourceSchema schema = ResourceSchemaFactory.buildPersonSchema();
		IModel<ResourceSchema> model = Model.of(schema);
		Panel panel = new SchemaDetailPanel("test", model);
		tester.startComponentInPage(panel);
		tester.assertNoErrorMessage();
		tester.assertNoInfoMessage();
		tester.assertComponent("test:form:deleteButton", RBStandardButton.class); 
		tester.assertComponent("test:form:addButton", RBStandardButton.class);
		tester.assertComponent("test:form:editButton", RBStandardButton.class);
		tester.assertContains(">http://lichtflut.de#Person");
		tester.assertContains("#TestRes");
		tester.assertContains(">hatAlter");
		tester.assertContains(">\\[1\\.\\.3868686\\]");
		tester.assertContains(">.*@.*");
	}
}
