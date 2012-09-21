/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import static org.mockito.Mockito.when;

import org.apache.wicket.model.Model;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.ResourceID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.lichtflut.rb.AbstractRBWebTest;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.mock.RBMock;
import de.lichtflut.rb.webck.config.QueryServicePathBuilder;

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

	@Mock
	private TypeManager typeManager;

	@Mock
	private ModelingConversation conversation;

	@Mock
	private QueryServicePathBuilder pathBuilder;

	@Mock
	private ServiceContext serviceContext;

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
		getTester().startComponentInPage(panel);

		getTester().assertNoErrorMessage();
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
		addMock("typeManager", typeManager);
		addMock("conversation", conversation);
		addMock("queryServicePathBuilder", pathBuilder);
		addMock("context", serviceContext);
		panel = new TypeHierarchyPanel("panel", new Model<ResourceID>(id));
	}

}
