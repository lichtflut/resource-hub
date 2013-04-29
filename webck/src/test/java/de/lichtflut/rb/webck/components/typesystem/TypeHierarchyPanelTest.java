/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.components.typesystem;

import static org.mockito.Mockito.when;

import org.apache.wicket.model.Model;
import org.arastreju.sge.model.ResourceID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.webck.RBWebTest;

/**
 * <p>
 * Testclass for {@link TypeHierarchyPanel}.
 * </p>
 * Created: Sep 21, 2012
 *
 * @author Ravi Knox
 */
@RunWith(MockitoJUnitRunner.class)
public class TypeHierarchyPanelTest extends RBWebTest {

	private TypeHierarchyPanel panel;
	private final ResourceID id = RB.PERSON;

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.typesystem.TypeHierarchyPanel#TypeHierarchyPanel(java.lang.String, org.apache.wicket.model.IModel)}.
	 */
	@Test
	public void testTypeHierarchyPanel() {
		when(networkService.resolve(id)).thenReturn(id.asResource());
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
