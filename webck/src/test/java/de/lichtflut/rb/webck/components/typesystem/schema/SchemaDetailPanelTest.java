/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.schema;

import static org.junit.Assert.fail;

import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.mock.schema.ResourceSchemaFactory;

/**
 * <p>
 * Testclass for {@link SchemaDetailPanel}.
 * </p>
 * Created: Sep 19, 2012
 *
 * @author Ravi Knox
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class SchemaDetailPanelTest {

	private WicketTester tester;

	@Autowired
	private ApplicationContext context;

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.typesystem.schema.SchemaDetailPanel#SchemaDetailPanel(java.lang.String, org.apache.wicket.model.IModel)}.
	 */
	@Test
	public void testSchemaDetailPanel() {
		WebApplication app = new MockApplication();
		//		ModelingConversation bean = (ModelingConversation) context.getBean("conversation");
		tester = new WicketTester(app);
		ResourceSchema schema = ResourceSchemaFactory.buildPersonSchema();
		SchemaDetailPanel panel = new SchemaDetailPanel("test", new Model<ResourceSchema>(schema));
		tester.startComponentInPage(panel);

		tester.assertNoErrorMessage();
	}

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.typesystem.schema.SchemaDetailPanel#saveAndUpdate()}.
	 */
	@Test
	public void testSaveAndUpdate() {
		fail("Not yet implemented");
	}

}
