/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.schema;

import org.apache.wicket.model.Model;
import org.junit.Test;

import de.lichtflut.rb.AbstractRBWebTest;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.mock.schema.ResourceSchemaFactory;
import de.lichtflut.rb.webck.models.types.PropertyRowListModel;


/**
 * <p>
 * Testclass for {@link SchemaPropertyDetailListPanel}
 * </p>
 * Created: Nov 9, 2012
 *
 * @author Ravi Knox
 */
public class SchemaPropertyDetailListPanelTest extends AbstractRBWebTest{

	private final ResourceSchema schema = ResourceSchemaFactory.buildPersonSchema();

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.typesystem.schema.SchemaPropertyDetailListPanel#SchemaPropertyDetailListPanel(java.lang.String, org.apache.wicket.model.IModel)}.
	 */
	@Test
	public void testSchemaPropertyDetailListPanel() {
		SchemaPropertyDetailListPanel panel = new SchemaPropertyDetailListPanel("panel", Model.of(schema));
		PropertyRowListModel listModel = new PropertyRowListModel(Model.of(schema));
		tester.startComponentInPage(panel);

		assertRenderedPanel(SchemaPropertyDetailListPanel.class, "panel");
		tester.assertListView("panel:propertyrow", listModel.getObject());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setupTest() {

	}

}
