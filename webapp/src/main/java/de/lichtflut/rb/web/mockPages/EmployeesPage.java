/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.mockPages;

import de.lichtflut.rb.core.mock.MockNewRBEntityFactory;
import de.lichtflut.rb.core.spi.INewRBServiceProvider;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.RBSuperPage;
import de.lichtflut.rb.web.ck.behavior.CKBehavior;
import de.lichtflut.rb.web.ck.components.CKComponent;
import de.lichtflut.rb.web.ck.components.ResourceTableView;

/**
 * [TODO Insert description here.
 *
 * Created: Aug 18, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class EmployeesPage extends RBSuperPage {

	/**
	 * Constructor.
	 */
	public EmployeesPage() {
		super("Mock-Employees-View");
		ResourceTableView view =
			new ResourceTableView("mockEmployeeView", MockNewRBEntityFactory.getListOfNewRBEntities()){

			@Override
			public RBServiceProvider getServiceProvider() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public CKComponent setViewMode(final ViewMode mode) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public INewRBServiceProvider getNewServiceProvider() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		view.addBehavior(ResourceTableView.SHOW_DETAILS, new CKBehavior() {

			@Override
			public Object execute(final Object... objects) {
				// TODO Auto-generated method stub
				return null;
			}
		});
//		view.addBehavior(ResourceTableView.UPDATE_ROW_ITEM, new CKBehavior() {
//
//			@Override
//			public Object execute(final Object... objects) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		});
		view.addBehavior(ResourceTableView.DELETE_ROW_ITEM, new CKBehavior() {

			@Override
			public Object execute(final Object... objects) {
				return null;
			}
		});
		add(view);
	}

}
