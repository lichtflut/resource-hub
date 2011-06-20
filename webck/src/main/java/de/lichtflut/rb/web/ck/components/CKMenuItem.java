package de.lichtflut.rb.web.ck.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;

import de.lichtflut.rb.web.ck.components.CKDestinationType;
import de.lichtflut.rb.core.spi.RBServiceProvider;

@SuppressWarnings("serial")
public class CKMenuItem extends CKComponent {

	private int destinationType;
	private String menuText;
	private Class<? extends WebPage> responsePageClass;
	private WebPage responsePage;
	private String externalLink;
	
	private List<CKMenuItem> subMenuItemList = new ArrayList<CKMenuItem>();
	private boolean seperator = false;
	private boolean submenuTitle = false;
	
	public CKMenuItem(String id) {
		super(id);
//		setSubmenuTitleTitle(true);
//		setMenuText(submenuTitle);
//		setDestinationType(DestinationType.NONE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RBServiceProvider getServiceProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CKComponent setViewMode(ViewMode mode) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	public CKMenuItem(boolean seperator){
//		setSeperator(true);
//		setDestinationType(CKDestinationType.NONE);
//	}

	public <T extends WebPage>CKMenuItem(String menuText, T destinationPage) {
//		setMenuText(menuText);
		super(menuText);
		setResponsePage(destinationPage);
		setSubMenuItemList(new ArrayList<CKMenuItem>());
		setDestinationType(CKDestinationType.WEB_PAGE_INSTANCE);
	}
	public CKMenuItem(String menuText, Class<? extends WebPage> destinationPageClass) {
//		setMenuText(menuText);
		super(menuText);
		setResponsePageClass(destinationPageClass);
		setSubMenuItemList(new ArrayList<CKMenuItem>());
		setDestinationType(CKDestinationType.WEB_PAGE_CLASS);
	}
	
	public CKMenuItem(String menuText, Class<? extends WebPage> destinationWebPage,List<CKMenuItem> subMenuItemList) throws InstantiationException, IllegalAccessException {
		this(menuText,destinationWebPage.newInstance(),subMenuItemList);
		setDestinationType(CKDestinationType.WEB_PAGE_CLASS);
	}
	public <T extends WebPage>CKMenuItem(String menuText, T destinationPage,List<CKMenuItem> subMenuItemList) {
//		setMenuText(menuText);
		super(menuText);
		setResponsePage(destinationPage);
		setSubMenuItemList(subMenuItemList);
		setDestinationType(CKDestinationType.WEB_PAGE_INSTANCE);
	}
	
	
//	public static CKMenuItem getMenuSeperator(){
//		return new CKMenuItem(true);
//	}
	
	public String getMenuText() {
		return menuText;
	}
	public void setMenuText(String text) {
		this.menuText = text;
	}
	public WebPage getResponsePage() {
		return responsePage;
	}
	public <T extends WebPage> void setResponsePage(T destinationPage) {
		this.responsePage = destinationPage;
	}
	public void addSubmenu(CKMenuItem subMenuItem){
		getSubMenuItemList().add(subMenuItem);
	}
	public List<CKMenuItem> getSubMenuItemList() {
		return subMenuItemList;
	}
	public void setSubMenuItemList(List<CKMenuItem> subMenuItemList) {
		this.subMenuItemList = subMenuItemList;
	}
	public boolean isSeperator() {
		return seperator;
	}
	public void setSeperator(boolean seperator) {
		this.seperator = seperator;
	}
	public boolean isSubmenuTitle() {
		return submenuTitle;
	}
	public void setSubmenuTitleTitle(boolean title) {
		this.submenuTitle = title;
	}
	public int getDestinationType() {
		return destinationType;
	}
	public void setDestinationType(int destinationType) {
		this.destinationType = destinationType;
	}
	public Class<? extends WebPage> getResponsePageClass() {
		return responsePageClass;
	}
	public void setResponsePageClass(
			Class<? extends WebPage> destinationPageClass) {
		this.responsePageClass = destinationPageClass;
	}
	public String getExternalLink() {
		return externalLink;
	}
	public void setExternalLink(String externalLink) {
		this.externalLink = externalLink;
	}

}
