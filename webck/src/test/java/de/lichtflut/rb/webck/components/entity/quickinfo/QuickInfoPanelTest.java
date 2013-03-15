/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity.quickinfo;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.model.nodes.SNResource;
import org.junit.Test;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.webck.RBWebTest;

/**
 * <p>
 * Testclass for {@link QuickInfoPanel}.
 * </p>
 * Created: Feb 27, 2013
 *
 * @author Ravi Knox
 */
public class QuickInfoPanelTest extends RBWebTest {

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.entity.quickinfo.QuickInfoPanel#InfoPanel(java.lang.String, org.apache.wicket.model.IModel)}.
	 */
	@Test
	public void testInfoPanelNoQuickInfo() {
		RBEntity entity = createCity(false);
		Panel panel = new QuickInfoPanel("panel", Model.of(entity));

		tester.startComponentInPage(panel);

		assertRenderedPanel(QuickInfoPanel.class, "panel");
		tester.assertComponent("panel:container:noInfo", Label.class);
		tester.assertInvisible("panel:container:info");
	}

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.entity.quickinfo.QuickInfoPanel#InfoPanel(java.lang.String, org.apache.wicket.model.IModel)}.
	 */
	@Test
	public void testInfoPanelWithQuickInfo() {
		RBEntity entity = createCity(true);
		Panel panel = new QuickInfoPanel("panel", Model.of(entity));

		tester.startComponentInPage(panel);

		assertRenderedPanel(QuickInfoPanel.class, "panel");
		tester.assertListView("panel:container:info", entity.getQuickInfo());
		tester.assertInvisible("panel:container:noInfo");
	}

	// ------------------------------------------------------

	public RBEntity createCity(final boolean quickInfo) {
		RBEntity entity = new RBEntityImpl(new SNResource(), buildCitySchema(quickInfo));

		entity.getField(RB.HAS_ID).addValue("1");
		entity.getField(RB.HAS_NAME).addValue("Germany");
		entity.getField(RB.HAS_DESCRIPTION).addValue("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum blandit ullamcorper ligula, eu fringilla mauris gravida eu. Fusce quis tortor id est tempor scelerisque non sed neque. Fusce sem ante, rhoncus ac pellentesque eget, interdum id mi. In dui urna, hendrerit id hendrerit in, laoreet ac urna. ");

		return entity;
	}


	public static ResourceSchema buildCitySchema(final boolean quickInfo) {
		ResourceSchemaImpl schema = new ResourceSchemaImpl(RB.CITY);

		PropertyDeclaration id = new PropertyDeclarationImpl(RB.HAS_ID, Datatype.STRING);
		schema.addPropertyDeclaration(id);

		PropertyDeclaration name = new PropertyDeclarationImpl(RB.HAS_NAME, Datatype.STRING);
		schema.addPropertyDeclaration(name);

		PropertyDeclaration description = new PropertyDeclarationImpl(RB.HAS_DESCRIPTION, Datatype.STRING);
		schema.addPropertyDeclaration(description);


		if(quickInfo){
			schema.addQuickInfo(RB.HAS_NAME);
			schema.addQuickInfo(RB.HAS_DESCRIPTION);
		}
		return schema;
	}

}
