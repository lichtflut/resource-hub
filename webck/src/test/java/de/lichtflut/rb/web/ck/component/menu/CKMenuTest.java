package de.lichtflut.rb.web.ck.component.menu;

import junit.framework.TestCase;

import org.apache.wicket.Page;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

import de.lichtflut.rb.web.application.AbstractResourceBrowserApplication;
import de.lichtflut.rb.web.ck.components.CKMenu;
import de.lichtflut.rb.web.ck.components.ResourceRegisterPanel;
/**
 *
 * [TODO Insert description here.
 *
 * Created: Jun 22, 2011
 *
 * @author Ravi Knox
 */
public class CKMenuTest extends TestCase{

	@SuppressWarnings("unused")
	private WicketTester tester;

	@Override
	public void setUp(){
		tester = new WicketTester(new AbstractResourceBrowserApplication(){
			protected void init(){
				getMarkupSettings().setStripWicketTags(true);
			}

			@Override
			public Class<? extends Page> getHomePage() {
				return TestMenuComponentPage.class;
			}
		});
	}

	/**
	 * Test CKMenuComponent.
	 */
	@Test
	public void testMenu(){
		tester.startPage(TestMenuComponentPage.class);

		//Test if CKMenu component is rendered
		tester.assertComponent("menu", CKMenu.class);

		// Test if ListItem is rendered
		//TODO: IMPROVE!!!
		tester.assertContains("<li>");

	}
}
