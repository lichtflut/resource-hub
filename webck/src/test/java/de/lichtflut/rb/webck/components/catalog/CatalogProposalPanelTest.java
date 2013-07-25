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
package de.lichtflut.rb.webck.components.catalog;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.webck.RBWebTest;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.data.RBTestConstants;
import de.lichtflut.rb.webck.data.schema.ResourceSchemaFactory;
import org.apache.wicket.model.Model;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

/**
 * <p>
 * Testclass for {@link CatalogProposalPanel}.
 * </p>
 * Created: Feb 5, 2013
 *
 * @author Ravi Knox
 */
public class CatalogProposalPanelTest extends RBWebTest{

	private ResourceNode type;

	// ------------- SetUp & tearDown -----------------------

	@Override
	protected void setupTest() {
		type = new SNResource(RBTestConstants.DATA_CENTER.getQualifiedName());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.catalog.CatalogProposalPanel#CatalogProposalPanel(java.lang.String, org.apache.wicket.model.IModel)}.
	 */
	@Test
	public void testCatalogProposalPanelViewState() {
		ResourceSchema schema = ResourceSchemaFactory.buildDataCenter();

		Set<SNClass> superclasses = new HashSet<SNClass>();
		superclasses.add(SNClass.from(RBTestConstants.PHYSICAL_MACHINE));
		when(schemaManager.findSchemaForType(type)).thenReturn(schema);
		when(typeManager.getSuperClasses(RBTestConstants.PHYSICAL_MACHINE)).thenReturn(superclasses);
		RBWebSession session = (RBWebSession) tester.getSession();
		session.getHistory().view(new EntityHandle());

		CatalogProposalPanel panel = new CatalogProposalPanel("panel", Model.of(RBTestConstants.DATA_CENTER));

		tester.startComponentInPage(panel);

		assertRenderedPanel(CatalogProposalPanel.class, "panel");
		tester.assertInvisible("panel");
	}

}
