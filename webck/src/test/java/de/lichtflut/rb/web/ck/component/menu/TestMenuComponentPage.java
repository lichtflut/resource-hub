/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.component.menu;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.lichtflut.rb.web.ck.components.CKMenu;
import de.lichtflut.rb.web.ck.components.CKMenuItem;

/**
 * TODO Insert description here.
 *
 * Created: Jun 22, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class TestMenuComponentPage extends WebPage{

	/**
	 *
	 */
	public TestMenuComponentPage(){
		super();
	}

	/**
	 * Constructor.
	 * @param params -
	 */
	public TestMenuComponentPage(final PageParameters params){
		super(params);
		// First-Level-Menu-Items
		CKMenuItem primaryLink1 = new CKMenuItem("Link 1", new TestMenuComponentPage());
		CKMenuItem primaryLink2 = new CKMenuItem("Link 2", new TestMenuComponentPage());
		CKMenuItem primaryLink3 = new CKMenuItem("Link 3", new TestMenuComponentPage());
		CKMenuItem primaryLink4 = new CKMenuItem("Link 4", new TestMenuComponentPage());
		CKMenuItem primaryLink5 = new CKMenuItem("Link 5", new TestMenuComponentPage());

		// Second-Level-Menu-Items
		CKMenuItem subMenu1 = new CKMenuItem("Sublink 1", new TestMenuComponentPage());
		CKMenuItem subMenu2 = new CKMenuItem("Sublink 2", new TestMenuComponentPage());

		// Third-Level-Menu-Items
		CKMenuItem subSubMenu1 = new CKMenuItem("Sub-subLink 1", new TestMenuComponentPage());
		CKMenuItem subSubMenu2 = new CKMenuItem("Sub-subLink 2", new TestMenuComponentPage());
		CKMenuItem subSubMenu3 = new CKMenuItem("Sub-subLink 3", new TestMenuComponentPage());

		List<CKMenuItem> firstLevelMenu = new ArrayList<CKMenuItem>();
		List<CKMenuItem> secondLevelMenu = new ArrayList<CKMenuItem>();
		List<CKMenuItem> thirdLevelMenu = new ArrayList<CKMenuItem>();

		firstLevelMenu.add(primaryLink1);
		firstLevelMenu.add(primaryLink2);
		firstLevelMenu.add(primaryLink3);
		firstLevelMenu.add(primaryLink4);
		firstLevelMenu.add(primaryLink5);

		secondLevelMenu.add(subMenu1);
		secondLevelMenu.add(subMenu2);

		thirdLevelMenu.add(subSubMenu1);
		thirdLevelMenu.add(subSubMenu2);
		thirdLevelMenu.add(subSubMenu3);

		primaryLink2.setSubMenuItemList(secondLevelMenu);
		subMenu2.setSubMenuItemList(thirdLevelMenu);
		add(new CKMenu("test-menu", firstLevelMenu));
	}
}
