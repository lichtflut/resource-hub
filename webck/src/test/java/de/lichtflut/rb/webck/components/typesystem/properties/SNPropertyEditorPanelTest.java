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
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.lichtflut.rb.AbstractBaseTest;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.mock.RBMock;

/**
 * <p>
 * Testclass for {@link SNPropertyEditorPanel}.
 * </p>
 * Created: Sep 19, 2012
 * 
 * @author Ravi Knox
 */
@RunWith(MockitoJUnitRunner.class)
public class SNPropertyEditorPanelTest extends AbstractBaseTest {

	@Mock
	private TypeManager typeManager;

	@Mock
	private ModelingConversation conversation;

	private QualifiedName property;

	private SNPropertyEditorPanel panel;

	/**
	 * Test method for
	 * {@link de.lichtflut.rb.webck.components.typesystem.properties.SNPropertyEditorPanel#SNPropertyEditorPanel(java.lang.String, org.apache.wicket.model.IModel)}
	 * .
	 */
	@Test
	public void testSNPropertyEditorPanel() {
		initTestData();

		getTester().startComponentInPage(panel);
		getTester().assertNoErrorMessage();
		getTester().assertLabel("test:propertyUri", property.toURI());
		getTester().assertLabel("test:propertyLabel", property.getSimpleName());
		getTester().assertListView("test:superProps", Arrays.asList(new SNProperty(RBMock.HAS_CONTACT_DATA.asResource())));

		FormTester formTester = getTester().newFormTester("test:form");
		formTester.submit();
		getTester().executeAjaxEvent("test:form:save", "onCLick");

		verify(conversation, times(1)).resolve(RBMock.HAS_ADDRESS);
	}

	// ------------------------------------------------------

	@Override
	protected void setupTest() {
		ResourceNode node = RBMock.HAS_ADDRESS.asResource();
		when(conversation.resolve(RBMock.HAS_ADDRESS)).thenReturn(node);
		addMock("typeManager", typeManager);
		addMock("conversation", conversation);
	}

	// ------------------------------------------------------

	private void initTestData() {
		property = RBMock.HAS_ADDRESS.getQualifiedName();
		SNProperty prop = new SNProperty(property);
		prop.addAssociation(RDFS.SUB_PROPERTY_OF, RBMock.HAS_CONTACT_DATA);

		IModel<SNProperty> snPropertyModel = new Model<SNProperty>(prop);
		panel = new SNPropertyEditorPanel("test", snPropertyModel);
	}

}
