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

import static org.mockito.Mockito.when;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.Test;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.webck.RBWebTest;
import de.lichtflut.rb.webck.data.RBEntityFactory;

/**
 * <p>
 * Testclass for {@link QuickInfoPopUpPanel}
 * </p>
 * Created: Sep 24, 2012
 *
 * @author Ravi Knox
 */
public class QuickInfoPopUpPanelTest extends RBWebTest {

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.entity.quickinfo.QuickInfoPopUpPanel#QuickInfoPanel(java.lang.String, IModel)}.
	 */
	@Test
	public void testQuickInfoPanel() {
		RBEntity entity = RBEntityFactory.createPersonEntity();
		Panel panel = new QuickInfoPopUpPanel("panel", new ListModel<RBField>(entity.getQuickInfo()), new Model<String>(entity.getLabel()));

		tester.startComponentInPage(panel);

		assertRenderedPanel(QuickInfoPopUpPanel.class, "panel");
		tester.assertNoErrorMessage();
		tester.assertContains(entity.getLabel());
		tester.assertListView("panel:quickInfo", entity.getQuickInfo());
	}

	@Override
	protected void setupTest() {
		QualifiedName qname = QualifiedName.from("quickInfoPanelTest");
		RBDomain domain = new RBDomain(qname);
		when(serviceContext.getDomain()).thenReturn(qname.toURI());
		when(authModule.getDomainManager()).thenReturn(domainManager);
		when(domainManager.findDomain(qname.toURI())).thenReturn(domain);
	}

}
