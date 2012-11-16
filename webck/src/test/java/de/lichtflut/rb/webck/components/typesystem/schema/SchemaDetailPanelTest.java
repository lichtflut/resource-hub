/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.schema;

import static org.mockito.Mockito.when;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import de.lichtflut.rb.AbstractRBWebTest;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.mock.schema.ResourceSchemaFactory;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
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
public class SchemaDetailPanelTest extends AbstractRBWebTest{

	// ------------- SetUp & tearDown -----------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setupTest() {

	}

	// ------------------------------------------------------

	@Test
	public void testSchemaDetailPanel(){
		ResourceSchema schema = ResourceSchemaFactory.buildPersonSchema();
		IModel<ResourceSchema> model = Model.of(schema);
		when(conversation.resolve(schema.getDescribedType())).thenReturn(schema.getDescribedType().asResource());
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
