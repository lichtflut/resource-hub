/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.mockPages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.spi.IRBServiceProvider;
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
		this(null);
	}

	/**
	 * Constructor.
	 * @param params -
	 */
	public EmployeesPage(final PageParameters params) {
		super("Mock-View");
		StringValue uri = params.get("type");
		ResourceID type = new SimpleResourceID(uri.toString());
		ResourceTableView view =
			new ResourceTableView("mockEmployeeView", getRBServiceProvider().getRBEntityManagement().findAllByType(type)){
			@Override
			public IRBServiceProvider getServiceProvider() {
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
