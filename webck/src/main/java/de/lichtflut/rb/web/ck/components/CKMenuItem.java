package de.lichtflut.rb.web.ck.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.WebPage;

import de.lichtflut.rb.core.spi.RBServiceProvider;


/**
 *<p>
 * Describes the CK-type MenuItem, which are used to build {@link CKMenu}.
 *</p>
 * Created: Jun 21, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class CKMenuItem extends CKComponent implements Serializable{

	private CKLink ckLink;
	private CKLinkType linkType;
	private String menuText;
	private Class<? extends WebPage> responsePageClass;
	private WebPage responsePage;
	private String externalLink;

	private List<CKMenuItem> subMenuItemList = new ArrayList<CKMenuItem>();
	private boolean seperator = false;
	private boolean submenuTitle = false;

	/**
	 * Constructor.
	 * @param submenuTitle - Linktitle
	 */
	public CKMenuItem(final String submenuTitle){
		super(submenuTitle);
		setSubmenuTitleTitle(true);
		setMenuText(submenuTitle);
		setDestinationType(CKLinkType.NONE);
		this.buildComponent();
	}

	/**
	 * Constructor.
	 * @param ckLink - {@link CKLink}
	 */
	public CKMenuItem(final CKLink ckLink){
		super(ckLink.getId());
		this.ckLink = ckLink;
	}
	/**
	 * Constructor. Link acts as a seperator.
	 * @param seperator - true if link should act as a seperator, false if not.
	 */
	public CKMenuItem(final boolean seperator){
		super("seperator");
		setSeperator(true);
		setDestinationType(CKLinkType.NONE);
	}

	/**
	 * Constructor.
	 * @param <T> -
	 * @param menuText -
	 * @param destinationPage -
	 */
	public <T extends WebPage>CKMenuItem(final String menuText,final T destinationPage) {
		super(menuText);
		setMenuText(menuText);
		setResponsePage(destinationPage);
		setSubMenuItemList(new ArrayList<CKMenuItem>());
		setDestinationType(CKLinkType.WEB_PAGE_INSTANCE);
	}

	/**
	 * Constructor.
	 * @param menuText -
	 * @param destinationPageClass -
	 */
	public CKMenuItem(final String menuText, final Class<? extends WebPage> destinationPageClass) {
		super(menuText);
		setMenuText(menuText);
		setResponsePageClass(destinationPageClass);
		setSubMenuItemList(new ArrayList<CKMenuItem>());
		setDestinationType(CKLinkType.WEB_PAGE_CLASS);
	}

	/**
	 * Constructor.
	 * @param menuText -
	 * @param destinationWebPage -
	 * @param subMenuItemList -
	 * @throws InstantiationException -
	 * @throws IllegalAccessException -
	 */
	public CKMenuItem(final String menuText, final Class<? extends WebPage> destinationWebPage
			,final List<CKMenuItem> subMenuItemList) throws InstantiationException, IllegalAccessException {
		this(menuText,destinationWebPage.newInstance(),subMenuItemList);
		setDestinationType(CKLinkType.WEB_PAGE_CLASS);
	}

	/**
	 * Constructor.
	 * @param <T> -
	 * @param menuText -
	 * @param destinationPage -
	 * @param subMenuItemList -
	 */
	public <T extends WebPage>CKMenuItem(final String menuText, final T destinationPage,final List<CKMenuItem> subMenuItemList) {
		super(menuText);
		setMenuText(menuText);
		setResponsePage(destinationPage);
		setSubMenuItemList(subMenuItemList);
		setDestinationType(CKLinkType.WEB_PAGE_INSTANCE);
	}

	/**
	 * Returns seperator.
	 * @return {@link CKMenuItem}
	 */
	public static CKMenuItem getMenuSeperator(){
		return new CKMenuItem(true);
	}

	/**
	 * Returns linktext.
	 * @return {@link String}
	 */
	public String getMenuText() {
		return menuText;
	}

	/**
	 * Sets menutext.
	 * @param text - {@link String} value for menutext
	 */
	public void setMenuText(final String text) {
		this.menuText = text;
	}

	/**
	 * Returns Responsepage.
	 * @return {@link WebPage}
	 */
	public WebPage getResponsePage() {
		return responsePage;
	}

	/**
	 * Sets ResponsePage.
	 * @param <T> -
	 * @param destinationPage -
	 */
	public <T extends WebPage> void setResponsePage(final T destinationPage) {
		this.responsePage = destinationPage;
	}

	/**
	 * Adds submenu.
	 * @param subMenuItem -
	 */
	public void addSubmenu(final CKMenuItem subMenuItem){
		getSubMenuItemList().add(subMenuItem);
	}

	/**
	 * Returns submenu.
	 * @return List of {@link CKMenuItem}
	 */
	public List<CKMenuItem> getSubMenuItemList() {
		return subMenuItemList;
	}

	/**
	 * Sets submenu-itemlist.
	 * @param subMenuItemList -
	 */
	public void setSubMenuItemList(final List<CKMenuItem> subMenuItemList) {
		this.subMenuItemList = subMenuItemList;
	}

	/**
	 * Returns wheather {@link MenuItem} is seperator.
	 * @return true if {@link MenuItem} is seperator, false if not
	 */
	public boolean isSeperator() {
		return seperator;
	}

	/**
	 * Sets if {@link MenuItem} is seperator.
	 * @param seperator - true if seperator, no if not
	 */
	public void setSeperator(final boolean seperator) {
		this.seperator = seperator;
	}

	/**
	 * Returns if {@link MenuItem} is submenu-title.
	 * @return {@link Boolean} - true if {@link MenuItem} is submenu-title, false if not
	 */
	public boolean isSubmenuTitle() {
		return submenuTitle;
	}

	/**
	 * Sets if {@link MenuItem} is submenu-title.
	 * @param title - true if {@link MenuItem} is submenu-title, false if not
	 */
	public void setSubmenuTitleTitle(final boolean title) {
		this.submenuTitle = title;
	}

	/**
	 * Returns linktype.
	 * @return {@link CKLinkType}
	 */
	public CKLinkType getLinkType() {
		return linkType;
	}

	/**
	 * Sets LinkType.
	 * @param linkType - {@link CKLinkType}
	 */
	public void setDestinationType(final CKLinkType linkType) {
		this.linkType = linkType;
	}

	/**
	 * Returns ResponsePageClass.
	 * @return {@link WebPage}
	 */
	public Class<? extends WebPage> getResponsePageClass() {
		return responsePageClass;
	}

	/**
	 * Sets ResponsePageClass.
	 * @param destinationPageClass -
	 */
	public void setResponsePageClass(
			final Class<? extends WebPage> destinationPageClass) {
		this.responsePageClass = destinationPageClass;
	}

	/**
	 * Returns externalLink as String.
	 * @return {@link String}
	 */
	public String getExternalLink() {
		return externalLink;
	}

	/**
	 * Sets externalLink.
	 * @param externalLink -
	 */
	public void setExternalLink(final String externalLink) {
		this.externalLink = externalLink;
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

	@Override
	protected void initComponent(CKValueWrapperModel model) {

		// TODO Auto-generated method stub
		
	}

}
