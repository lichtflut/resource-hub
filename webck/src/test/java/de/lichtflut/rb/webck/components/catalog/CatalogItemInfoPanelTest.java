/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
