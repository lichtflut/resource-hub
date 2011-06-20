/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.List;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import de.lichtflut.rb.web.ck.components.CKMenuItem;
import de.lichtflut.rb.web.ck.components.CKDestinationType;


import de.lichtflut.rb.core.spi.RBServiceProvider;

/**
 * [TODO Insert description here.]
 * 
 * Created: Jun 20, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class CKMenu extends CKComponent {
	

//	private ResourceReference SHORTCUTS_CSS = new CompressedResourceReference(MultiLevelCssMenu.class,"css/MultiLevelCssMenu.css");
//	private ResourceReference SHORTCUTS_JAVASCRIPT = new CompressedResourceReference(MultiLevelCssMenu.class,"js/jqueryMin.js");
//	private ResourceReference SHORTCUTS_JAVASCRIPT2 = new CompressedResourceReference(MultiLevelCssMenu.class,"js/MultiLevelCssMenu.js");
	

	public CKMenu(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RBServiceProvider getServiceProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CKComponent setViewMode(ViewMode mode) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public CKMenu(String id, List<CKMenuItem> menuItemList) {
		super(id);
		//TODO
		setRenderBodyOnly(true);
		CKMenu multiLevelMenu = new CKMenu("multiLevelMenu",menuItemList);
		multiLevelMenu.setRenderBodyOnly(true);
		add(multiLevelMenu);
	}
	
	
	
	@Override
	public void renderHead(IHeaderResponse response) {
//		response.renderCSSReference(SHORTCUTS_CSS);
//		response.renderJavascriptReference(SHORTCUTS_JAVASCRIPT);
//		response.renderJavascriptReference(SHORTCUTS_JAVASCRIPT2);
	}

	
	private void processResponse(CKMenuItem menuItem){
		switch(menuItem.getDestinationType()){
			case CKDestinationType.EXTERNAL_LINK:	
				//forward to external link
				break;
			case CKDestinationType.WEB_PAGE_CLASS:
				setResponsePage(menuItem.getResponsePageClass());
				break;
			case CKDestinationType.WEB_PAGE_INSTANCE:
				setResponsePage(menuItem.getResponsePage());
				break;
			case CKDestinationType.NONE:
				//TODO throw exception 
				break;
		}
	}
	
	class MultiLevelMenu extends Panel {

		public MultiLevelMenu(String id,List<CKMenuItem> menuItemList) {
			super(id);
			if(menuItemList==null || menuItemList.size()==0) {
				return;
			}
			ListView menu = buildMultiLevelMenu("menuList", menuItemList);
			menu.setReuseItems(true);
			add(menu);
		}	
		
		@SuppressWarnings("unchecked")
		private ListView buildMultiLevelMenu(String id,List<CKMenuItem> menuItemList) {
			return new ListView(id, menuItemList) {			
				public void populateItem(final ListItem item) {
					final CKMenuItem menuItem = ((CKMenuItem) item.getModelObject());
					Link link = new Link("menuLink") {
						@Override
						public void onClick() {
							if (menuItem!=null ) {
								processResponse(menuItem);
							}
						}
					};
					
					Label linkText = new Label("menuLinkText", menuItem.getMenuText());
					linkText.setRenderBodyOnly(true);
					link.add(linkText);
					item.add(link);
					
					
					List<CKMenuItem> submenuItemList = menuItem.getSubMenuItemList();
					//INFO If submenu exists, output it to html. If not, add empty markup container and hide it.
					if(submenuItemList != null && submenuItemList.size()>0) {
						MultiLevelMenu subLevelMenu = new MultiLevelMenu("submenuListContainer",submenuItemList);
						subLevelMenu.setRenderBodyOnly(true);
						item.add(subLevelMenu);
					}else {
						WebMarkupContainer submenuMarkupContainer = new WebMarkupContainer("submenuListContainer");
						submenuMarkupContainer.setRenderBodyOnly(true);
						item.add(submenuMarkupContainer);
					}
				}
			};
		}
	}
}

