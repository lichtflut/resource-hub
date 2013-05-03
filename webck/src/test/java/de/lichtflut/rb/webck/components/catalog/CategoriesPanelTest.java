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

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.Model;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.Test;

import de.lichtflut.rb.webck.RBWebTest;
import de.lichtflut.rb.webck.components.common.PanelTitle;
import de.lichtflut.rb.webck.data.RBTestConstants;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;

/**
 * <p>
 * Testclass for {@link CategoriesPanel}.
 * </p>
 * Created: Jan 31, 2013
 *
 * @author Ravi Knox
 */
public class CategoriesPanelTest extends RBWebTest{

	private static final String resourcePrefix = "http://lichtflut.de/common#";

	private Set<SNClass> superCategories;

	// ------------- SetUp & tearDown -----------------------

	@Override
	protected void setupTest() {
		superCategories = getSuperCategories();
	}

	// ------------------------------------------------------

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.catalog.CategoriesPanel#SoftwareItemsCategoriesPanel(java.lang.String)}.
	 */
	@Test
	public void testSoftwareItemsCategoriesPanel() {
		CategoriesPanel panel = new CategoriesPanel("panel", Model.of(RBTestConstants.SOFTWARE_ITEM));

		tester.startComponentInPage(panel);

		assertRenderedPanel(CategoriesPanel.class, "panel");

		tester.assertComponent("panel:categoriesTitle", PanelTitle.class);
		tester.assertListView("panel:categoriesList", Collections.EMPTY_LIST);
		tester.assertComponent("panel:noSubclasses", Label.class);
	}

	@Test
	public void testGetAllCategories(){
		when(typeManager.getSubClasses(RBTestConstants.SOFTWARE_ITEM)).thenReturn(superCategories);

		CategoriesPanel panel = new CategoriesPanel("panel", Model.of(RBTestConstants.SOFTWARE_ITEM));

		tester.startComponentInPage(panel);

		assertRenderedPanel(CategoriesPanel.class, "panel");

		tester.assertComponent("panel:categoriesTitle", PanelTitle.class);
		tester.assertListView("panel:categoriesList", new ArrayList<SNClass>(superCategories));
		Iterator<SNClass> iterator = superCategories.iterator();
		tester.assertComponent("panel:categoriesList:0:link", AbstractLink.class);
		tester.assertLabel("panel:categoriesList:0:link:label", new ResourceLabelModel(iterator.next()).getObject());
		tester.assertLabel("panel:categoriesList:1:link:label", new ResourceLabelModel(iterator.next()).getObject());

		tester.assertInvisible("panel:noSubclasses");
	}

	// ------------------------------------------------------

	private Set<SNClass> getSuperCategories(){
		Set<SNClass> set = new LinkedHashSet<SNClass>();

		set.add(getSNClassFor("Application Server"));
		set.add(getSNClassFor("DataStore"));

		return set;
	}

	private SNClass getSNClassFor(final String label) {
		SNResource snResource = new SNResource(QualifiedName.fromURI(resourcePrefix + label));
		SNClass appServer = new SNClass(snResource);
		SNOPS.assure(appServer, RDFS.LABEL, new SNValue(ElementaryDataType.STRING, label));
		return appServer;
	}
}
