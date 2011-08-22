/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.mockPages;

import de.lichtflut.rb.core.mock.MockNewRBEntityFactory;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.RBSuperPage;
import de.lichtflut.rb.web.ck.components.CKComponent;
import de.lichtflut.rb.web.ck.components.GenericResourceTableView;

/**
 * [TODO Insert description here.
 *
 * Created: Aug 18, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class EmployeeView extends RBSuperPage {

	/**
	 * Constructor.
	 */
	public EmployeeView() {
		super("Mock-Employees-View");

		add(new GenericResourceTableView("mockEmployeeView", MockNewRBEntityFactory.getListOfNewRBEntities()){

		@Override
		public CKComponent setViewMode(final ViewMode mode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void initComponent(final CKValueWrapperModel model) {
			// TODO Auto-generated method stub

		}

		@Override
		public RBServiceProvider getServiceProvider() {
			// TODO Auto-generated method stub
			return null;
		}
	});
	}

}
