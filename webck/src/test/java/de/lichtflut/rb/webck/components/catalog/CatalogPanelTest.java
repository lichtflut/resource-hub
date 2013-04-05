/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.catalog;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.Test;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteComponent;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.webck.RBWebTest;
import de.lichtflut.rb.webck.data.RBTestConstants;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;

/**
 * <p>
 * Testclass for {@link CatalogPanel}.
 * </p>
 * Created: Jan 28, 2013
 *
 * @author Ravi Knox
 */
public class CatalogPanelTest extends RBWebTest {

	private static final String resourcePrefix = "http://lichtflut.de/common#";

	private Set<SNClass> superCategories;
	private Set<SNClass> lvlOneCategories;

	// ------------- SetUp & tearDown -----------------------

	@Override
	protected void setupTest() {
		superCategories = getSuperCategories();
		lvlOneCategories = getLvlOneCategories();
	}

	// ------------------------------------------------------

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.catalog.CatalogPanel#SoftwareCatalogPanel(java.lang.String)}.
	 */
	@Test
	public void testSoftwareCatalogPanel() {
		simulatePathbuilder();
		CatalogPanel panel = new CatalogPanel("panel", Model.of(RBTestConstants.SOFTWARE_ITEM));

		tester.startComponentInPage(panel);

		assertRenderedPanel(CatalogPanel.class, "panel");

		tester.assertComponent("panel:categories", CategoriesPanel.class);
		tester.assertComponent("panel:form:searchbox", AutocompleteComponent.class);
		tester.assertComponent("panel:form:create", AjaxButton.class);
		tester.assertInvisible("panel:specifyingList");
	}

	@Test
	public void testShowSubCategoryOnClick(){
		Model<ResourceID> model = Model.of(RBTestConstants.SOFTWARE_ITEM);

		simulatePathbuilder();
		when(typeManager.getSubClasses(RBTestConstants.SOFTWARE_ITEM)).thenReturn(superCategories);
		when(typeManager.getSubClasses(superCategories.iterator().next())).thenReturn(lvlOneCategories);
		ResourceNode resource = model.getObject().asResource();
		resource.addAssociation(RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE, RBTestConstants.SOFTWARE_ITEM);
		when(networkService.find(superCategories.iterator().next().getQualifiedName())).thenReturn(resource);
		CatalogPanel panel = new CatalogPanel("panel", model);

		tester.startComponentInPage(panel);

		assertRenderedPanel(CatalogPanel.class, "panel");

		tester.executeAjaxEvent("panel:categories:categoriesList:0:link", "onclick");

		tester.assertVisible("panel:specifyingList");
		tester.assertComponent("panel:specifyingList:0:itemListTitle", Label.class);
		tester.assertComponent("panel:specifyingList:0:createLink", AjaxLink.class);
		tester.assertComponent("panel:specifyingList:0:info", CatalogItemInfoPanel.class);
		tester.assertVisible("panel:specifyingList:0:subList");
		tester.assertListView("panel:specifyingList:0:subList", new ArrayList<SNClass>(lvlOneCategories));
		Iterator<SNClass> iterator = lvlOneCategories.iterator();
		tester.assertLabel("panel:specifyingList:0:subList:0:subLink:subLabel", new ResourceLabelModel(iterator.next()).getObject());
	}

	// ------------------------------------------------------

	private Set<SNClass> getSuperCategories(){
		Set<SNClass> set = new LinkedHashSet<SNClass>();

		set.add(getSNClassFor("Application Server"));
		set.add(getSNClassFor("DataStore"));

		return set;
	}

	private Set<SNClass> getLvlOneCategories(){
		Set<SNClass> set = new LinkedHashSet<SNClass>();

		set.add(getSNClassFor("Glasfish"));
		set.add(getSNClassFor("JBoss"));
		set.add(getSNClassFor("Jetty"));
		set.add(getSNClassFor("Tomcat"));
		set.add(getSNClassFor("Web Logic"));

		return set;
	}

	private SNClass getSNClassFor(final String label) {
		SNResource snResource = new SNResource(QualifiedName.create(resourcePrefix + label));
		SNClass appServer = new SNClass(snResource);
		SNOPS.assure(appServer, RDFS.LABEL, new SNValue(ElementaryDataType.STRING, label));
		return appServer;
	}

}
