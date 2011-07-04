package de.lichtflut.rb.web.ck.components;
/**
 * This enum describes the Type of CKLinks and how to perform incoming actions like "onClick".
 *
 * Created: Jun 21, 2011
 *
 * @author Ravi Knox
 */
public enum CKLinkType {

	/**
	 * This link's action should refer to a {@link Class} of {@link Page}.
	 */
	WEB_PAGE_CLASS,
	/**
	 * This link's action should refer to a instance of {@link Page}.
	 */
	WEB_PAGE_INSTANCE,
	/**
	 * This link's action should refer to a {@link Class} of {@link Page}.
	 */
	BOOKMARKABLE_WEB_PAGE_CLASS,
	/**
	 * This link's action should refer to an external resource.
	 */
	EXTERNAL_LINK,
	/**
	 * This link's action should perform a pre-defined {@link CKBehavior}.
	 */
	CUSTOM_BEHAVIOR,
	/**
	 * There is no special type defined. The link itself should decide how to handle an incoming action.
	 */
	NONE;
}
