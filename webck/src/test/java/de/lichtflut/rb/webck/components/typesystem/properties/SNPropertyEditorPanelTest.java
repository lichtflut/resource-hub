/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.properties;

import static org.mockito.Mockito.mock;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.Test;

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
public class SNPropertyEditorPanelTest extends AbstractBaseTest{

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.typesystem.properties.SNPropertyEditorPanel#SNPropertyEditorPanel(java.lang.String, org.apache.wicket.model.IModel)}.
	 */
	@Test
	public void testSNPropertyEditorPanel() {
		QualifiedName property = RBMock.HAS_ADDRESS.getQualifiedName();
		IModel<SNProperty> snPropertyModel = new Model<SNProperty>(new SNProperty(property));
		SNPropertyEditorPanel panel = new SNPropertyEditorPanel("test", snPropertyModel);
		getTester().startComponentInPage(panel);

		getTester().assertNoErrorMessage();
		getTester().assertLabel("test:propertyUri", property.toURI());
		getTester().assertLabel("test:propertyLabel", property.getSimpleName());
	}

	// ------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setupTest() {
		TypeManager typeManager = mock(TypeManager.class);
		ModelingConversation conversation = mock(ModelingConversation.class);
		addMock("typeManager", typeManager);
		addMock("conversation", conversation);
	}

}
