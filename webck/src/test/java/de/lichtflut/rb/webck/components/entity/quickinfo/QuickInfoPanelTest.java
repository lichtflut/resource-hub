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
package de.lichtflut.rb.webck.components.entity.quickinfo;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.util.ListModel;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SNResource;
import org.junit.Test;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
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
		when(networkService.resolve(any(ResourceID.class))).thenReturn(entity.getNode());
		Panel panel = new QuickInfoPanel("panel", new ListModel<RBField>(entity.getQuickInfo()));

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
		Panel panel = new QuickInfoPanel("panel", new ListModel<RBField>(entity.getQuickInfo()));

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
