/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.mockPages;

import de.lichtflut.rb.core.mock.MockNewRBEntityFactory;
import de.lichtflut.rb.web.RBSuperPage;
import de.lichtflut.rb.web.ck.components.GenericResourcesViewPanel;

/**
 * [TODO Insert description here.
 *
 * Created: Aug 18, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class EmplyeeView extends RBSuperPage {

	/**
	 * Constructor.
	 */
	public EmplyeeView() {
		super("Mock-Employees-View");

		add(new GenericResourcesViewPanel("mockEmployee", MockNewRBEntityFactory.getListOfNewRBEntities()));
	}

}
