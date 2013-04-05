/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.common;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import de.lichtflut.rb.webck.RBWebTest;

/**
 * <p>
 * Testclass for {@link PanelTitle}.
 * </p>
 * Created: Feb 8, 2013
 *
 * @author Ravi Knox
 */
public class PanelTitleTest extends RBWebTest {

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.common.PanelTitle#PanelTitle(java.lang.String, org.apache.wicket.model.IModel)}.
	 */
	@Test
	public void testPanelTitle() {
		IModel<String> model = new Model<String>("Test Title");
		PanelTitle panel = new PanelTitle("panel", model);

		tester.startComponentInPage(panel);

		assertRenderedPanel(PanelTitle.class, "panel");
		tester.assertLabel("panel:title", model.getObject());
	}

}
