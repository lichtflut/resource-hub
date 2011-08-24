/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.mockPages;

import org.apache.wicket.markup.html.basic.Label;

import de.lichtflut.rb.core.mock.MockNewRBEntityFactory;
import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.RBSuperPage;
import de.lichtflut.rb.web.ck.behavior.CKBehavior;
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
		GenericResourceTableView view =
			new GenericResourceTableView("mockEmployeeView", MockNewRBEntityFactory.getListOfNewRBEntities()){

			@Override
			protected void initComponent(final CKValueWrapperModel model) {
				// TODO Auto-generated method stub
			}

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
		};
		view.addBehavior(GenericResourceTableView.RESOURCE_FIELD_BEHAVIOR, new CKBehavior() {
			@Override
			public Object execute(final Object... objects) {
				IRBField field = (IRBField) objects[2];
				IRBEntity e = (IRBEntity) objects[1];
				return (new Label(objects[0].toString(), "hahah"));
			}
		});
		add(view);
	}

}
