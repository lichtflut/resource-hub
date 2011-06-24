/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import de.lichtflut.rb.core.spi.RBServiceProvider;

/**
 * Wrapper class for CKMenu components.
 * This class builds a hierarchical menu from given parameters.
 *
 * Created: Jun 20, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class CKMenu extends CKComponent {

	private final List<CKMenuItem> menuItemList;

//	private ResourceReference SHORTCUTS_CSS = new CompressedResourceReference(MultiLevelCssMenu.class,"css/MultiLevelCssMenu.css");
//	private ResourceReference SHORTCUTS_JAVASCRIPT = new CompressedResourceReference(MultiLevelCssMenu.class,"js/jqueryMin.js");
//	private ResourceReference SHORTCUTS_JAVASCRIPT2 = new
//			CompressedResourceReference(MultiLevelCssMenu.class,"js/MultiLevelCssMenu.js");

	/**
	 * Constructor.
	 * @param id - wicket:id of component
	 * @param menuItemList - List of {@link CKMenuItem}
	 */
	public CKMenu(final String id, final List<CKMenuItem> menuItemList) {
		super(id);
		setRenderBodyOnly(true);
		this.menuItemList = menuItemList;
		buildComponent();
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
	public CKComponent setViewMode(final ViewMode mode) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Checks type of the link and acts accordingly.
	 * TODO: CKECK IF IT IS ACTUALLY NEEDED
	 * @param menuItem - {@link CKMenuItem} to be processed.
	 */
	private void processResponse(final CKMenuItem menuItem){
		switch(menuItem.getLinkType()){
			case EXTERNAL_LINK:
				//TODO: forward to external link
				break;
			case WEB_PAGE_CLASS:
				setResponsePage(menuItem.getResponsePageClass());
				break;
			case WEB_PAGE_INSTANCE:
				setResponsePage(menuItem.getResponsePage());
				break;
			case NONE:
				//TODO throw exception
				break;
		default:
			break;
		}
	}

	/**
	 *
	 * [TODO Insert description here.
	 *
	 * Created: Jun 21, 2011
	 *
	 * @author Ravi Knox
	 */
	class NestedMenu extends CKComponent {

		/**
		 * Constructor.
		 * @param id - wicket:id of the component
		 * @param menuItemList - List of {@link CKMenuItem} to be displayed
		 */

		public NestedMenu(final String id,final List<CKMenuItem> menuItemList) {
			super(id);
			if(menuItemList==null || menuItemList.size()==0) {
				return;
			}
			buildComponent();
		}

		/**
		 * Builds a nested menu.
		 * @param id - wicket:id of component
		 * @param menuItemList - List of {@link CKMenuItem} to be displayed
		 * @return {@link ListView}
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private ListView buildNestedMenu(final String id,final List<CKMenuItem> menuItemList) {
			return new ListView(id, menuItemList) {
				@Override
				public void populateItem(final ListItem item) {
					final CKMenuItem menuItem = ((CKMenuItem) item.getModelObject());

						final Link link = new Link("menuLink") {
							@Override
							public void onClick() {
								if (menuItem!=null){
									processResponse(menuItem);
								}
							}
						};

						final Label linkText = new Label("menuLinkText", menuItem.getMenuText());
						linkText.setRenderBodyOnly(true);
						link.add(linkText);
						item.add(link);

					final List<CKMenuItem> submenuItemList = menuItem.getSubMenuItemList();
					//INFO If submenu exists, output it to html. If not, add empty markup container and hide it.
					if(submenuItemList != null && submenuItemList.size()>0) {
						final CKMenu subLevelMenu = new CKMenu("submenuListContainer",submenuItemList);
						subLevelMenu.setRenderBodyOnly(true);
						item.add(subLevelMenu);
					}else {
						final WebMarkupContainer submenuMarkupContainer =
									new WebMarkupContainer("submenuListContainer");
						submenuMarkupContainer.setRenderBodyOnly(true);
						item.add(submenuMarkupContainer);
					}
				}
			};
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
		public CKComponent setViewMode(final ViewMode mode) {
			// TODO Auto-generated method stub
			return null;
		}

		@SuppressWarnings("rawtypes")
		@Override
		protected void initComponent(final CKValueWrapperModel model) {
			final ListView menu = buildNestedMenu("menuList", menuItemList);
			menu.setReuseItems(true);
			add(menu);
		}
	}

	@Override
	protected void initComponent(final CKValueWrapperModel model){
		final NestedMenu multiLevelMenu = new NestedMenu("menu",menuItemList);
		multiLevelMenu.setRenderBodyOnly(true);
		this.add(multiLevelMenu);
	}
}

