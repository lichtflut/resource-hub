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

import java.util.UUID;

import org.apache.wicket.model.Model;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SNValue;
import org.junit.Test;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.webck.RBWebTest;

/**
 * <p>
 * Testclass for {@link CatalogItemInfoPanel}.
 * </p>
 * Created: Feb 8, 2013
 *
 * @author Ravi Knox
 */
public class CatalogItemInfoPanelTest extends RBWebTest {

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.catalog.CatalogItemInfoPanel#CatalogItemInfoPanel(java.lang.String, org.apache.wicket.model.IModel)}.
	 */
	@Test
	public void testCatalogItemInfoPanelNoDescription() {
		ResourceNode node = new SNResource();

		CatalogItemInfoPanel panel = new CatalogItemInfoPanel("panel", new Model<ResourceNode>(node));

		tester.startComponentInPage(panel);

		assertRenderedPanel(CatalogItemInfoPanel.class, "panel");
		tester.assertLabel("panel:info", panel.getLocalizer().getString("label.no-description", panel));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.catalog.CatalogItemInfoPanel#CatalogItemInfoPanel(java.lang.String, org.apache.wicket.model.IModel)}.
	 */
	@Test
	public void testCatalogItemInfoPanelWithDescription() {
		String description = UUID.randomUUID().toString();
		ResourceNode node = new SNResource();
		node.addAssociation(RB.HAS_DESCRIPTION, new SNValue(ElementaryDataType.STRING, description));

		CatalogItemInfoPanel panel = new CatalogItemInfoPanel("panel", new Model<ResourceNode>(node));

		tester.startComponentInPage(panel);

		assertRenderedPanel(CatalogItemInfoPanel.class, "panel");
		tester.assertLabel("panel:info", description);
	}

}
