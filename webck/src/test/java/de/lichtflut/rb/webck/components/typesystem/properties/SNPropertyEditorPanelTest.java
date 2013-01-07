/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.properties;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.FormTester;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.lichtflut.rb.AbstractRBWebTest;
import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;

/**
 * <p>
 * Testclass for {@link SNPropertyEditorPanel}.
 * </p>
 * Created: Sep 19, 2012
 *
 * @author Ravi Knox
 */
@RunWith(MockitoJUnitRunner.class)
public class SNPropertyEditorPanelTest extends AbstractRBWebTest {

	private QualifiedName property;

	private SNPropertyEditorPanel panel;

	// ------------------------------------------------------

	/**
	 * Test method for
	 * {@link de.lichtflut.rb.webck.components.typesystem.properties.SNPropertyEditorPanel#SNPropertyEditorPanel(java.lang.String, org.apache.wicket.model.IModel)}
	 * .
	 * TODO FIX TEST
	 */
	@Test
	@Ignore
	public void testSNPropertyEditorPanel() {
		initTestData();

		// Assert Components
		tester.startComponentInPage(panel);
		tester.assertNoErrorMessage();
		tester.assertLabel("panel:propertyUri", property.toURI());
		tester.assertLabel("panel:propertyLabel", property.getSimpleName());
		tester.assertListView("panel:superProps", Arrays.asList(new SNProperty(RB.HAS_CONTACT_DATA.asResource())));
		tester.assertComponent("panel:form:save", RBStandardButton.class);
		tester.assertComponent("panel:form:delete", RBStandardButton.class);
		tester.assertComponent("panel:form:cancel", RBCancelButton.class);


		// Assert Behavior
		FormTester formTester = tester.newFormTester("panel:form");
		formTester.submit();
		tester.executeAjaxEvent("panel:form:save", "onCLick");

		verify(conversation, times(1)).resolve(RB.HAS_ADDRESS);
	}

	// ------------------------------------------------------

	@Override
	protected void setupTest() {
		ResourceNode node = RB.HAS_ADDRESS.asResource();
		when(conversation.resolve(RB.HAS_ADDRESS)).thenReturn(node);
	}

	// ------------------------------------------------------

	private void initTestData() {
		property = RB.HAS_ADDRESS.getQualifiedName();
		SNProperty prop = new SNProperty(property);
		prop.addAssociation(RDFS.SUB_PROPERTY_OF, RB.HAS_CONTACT_DATA);

		IModel<SNProperty> snPropertyModel = new Model<SNProperty>(prop);
		panel = new SNPropertyEditorPanel("panel", snPropertyModel);
	}

}
