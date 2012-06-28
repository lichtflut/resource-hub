/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.constraints;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.mock.schema.ConstraintsFactory;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;

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
	public void setUp() {
		tester = new WicketTester();
	}

	/**
     * TODO: OT 28.6.2012 - reactivate when problem with spring beans is solved
     *
	 * Test method for
	 * {@link de.lichtflut.rb.webck.components.typesystem.constraints.ConstraintsEditorPanel#ConstraintsEditorPanel(java.lang.String, org.apache.wicket.model.IModel)}
	 * .
	 */
	@Ignore
	public void testConstraintsEditorPanel() {
		Constraint constraint = ConstraintsFactory.buildPublicEmailConstraint();
		IModel<PropertyRow> model = Model.of(new PropertyRow(constraint));
		model.getObject().setDataType(Datatype.STRING);
		Panel panel = new ConstraintsEditorPanel("test", model);
		tester.startComponentInPage(panel);
		tester.assertNoErrorMessage();
	}

}
