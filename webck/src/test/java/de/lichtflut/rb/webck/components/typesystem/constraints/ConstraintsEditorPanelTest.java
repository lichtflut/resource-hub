/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.constraints;

import static org.junit.Assert.*;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.mock.schema.ConstraintsFactory;
import de.lichtflut.rb.mock.schema.ResourceSchemaFactory;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.components.typesystem.schema.SchemaDetailPanel;

/**
 * <p>
 * [TODO Insert description here.]
 * </p>
 * Created: May 14, 2012
 *
 * @author Ravi Knox
 */
public class ConstraintsEditorPanelTest {

	private WicketTester tester;
	
	@Before
	public void setUp(){
		tester = new WicketTester();
	}
	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.typesystem.constraints.ConstraintsEditorPanel#ConstraintsEditorPanel(java.lang.String, org.apache.wicket.model.IModel)}.
	 */
//	@Test
//	public void testConstraintsEditorPanel() {
//		Constraint constraint= ConstraintsFactory.buildPublicEmailConstraint();
//		IModel<PropertyRow> model = Model.of(new PropertyRow(constraint));
//		Panel panel = new ConstraintsEditorPanel("test", model);
//		tester.startComponentInPage(panel);
//		tester.assertNoErrorMessage();
//	}

}
