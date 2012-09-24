/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import static org.mockito.Mockito.when;

import org.apache.wicket.model.Model;
import org.arastreju.sge.model.ResourceID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.lichtflut.rb.AbstractRBWebTest;
import de.lichtflut.rb.mock.RBMock;

/**
 * <p>
 * Testclass for {@link TypeHierarchyPanel}.
 * </p>
 * Created: Sep 21, 2012
 *
 * @author Ravi Knox
 */
@RunWith(MockitoJUnitRunner.class)
public class TypeHierarchyPanelTest extends AbstractRBWebTest {

	private TypeHierarchyPanel panel;
	private final ResourceID id = RBMock.PERSON;

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.typesystem.TypeHierarchyPanel#TypeHierarchyPanel(java.lang.String, org.apache.wicket.model.IModel)}.
	 */
	@Test
	public void testTypeHierarchyPanel() {
		when(conversation.resolve(id)).thenReturn(id.asResource());
		when(serviceContext.getDomain()).thenReturn("test");
		when(pathBuilder.queryClasses("test", null)).thenReturn("blablabla");
		tester.startComponentInPage(panel);

		tester.assertNoErrorMessage();
	}

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.typesystem.TypeHierarchyPanel#addSuperClass(org.arastreju.sge.model.ResourceID)}.
	 */
	@Test
	public void testAddSuperClass() {
		// TODO implement
	}

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.typesystem.TypeHierarchyPanel#removeSuperClass(org.arastreju.sge.model.ResourceID)}.
	 */
	@Test
	public void testRemoveSuperClass() {
		// TODO implement
	}

	// ------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setupTest() {
		panel = new TypeHierarchyPanel("panel", new Model<ResourceID>(id));
	}

}
