/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.mockPages;

import de.lichtflut.rb.core.spi.INewRBServiceProvider;
import de.lichtflut.rb.web.RBSuperPage;
import de.lichtflut.rb.web.ck.behavior.CKBehavior;
import de.lichtflut.rb.web.ck.components.CKComponent;
import de.lichtflut.rb.web.ck.components.ResourceTableView;

/**
 * SamplePage for {@link ResourceTableView}.
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
			new ResourceTableView("mockEmployeeView", getRBServiceProvider().getRBEntityManagement().findAllByType(null)){
			@Override
			public INewRBServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}
			@Override
			public CKComponent setViewMode(final ViewMode mode) {return null;}
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
//		view.addBehavior(ResourceTableView.DELETE_ROW_ITEM, new CKBehavior() {
//
//			@Override
//			public Object execute(final Object... objects) {
//				return null;
//			}
//		});
		add(view);
	}

}
