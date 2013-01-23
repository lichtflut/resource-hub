/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.constraints;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.webck.RBWebTest;
import de.lichtflut.rb.webck.data.schema.ConstraintsFactory;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableModel;
import de.lichtflut.rb.webck.models.basic.LoadableModel;

/**
 * <p>
 * Testcase for {@link ConstraintsBrowserPanel}.
 * </p>
 * Created: Oct 29, 2012
 *
 * @author Ravi Knox
 */
public class ConstraintsBrowserPanelTest extends RBWebTest{

	private LoadableModel<List<Constraint>> model;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setupTest() {
		model = new AbstractLoadableModel<List<Constraint>>() {

			@Override
			public List<Constraint> load() {
				List<Constraint> constraints = new ArrayList<Constraint>();
				constraints.add(ConstraintsFactory.buildPublicEmailConstraint());
				constraints.add(ConstraintsFactory.buildPublicURLConstraint());
				return constraints;
			}

		};
	}

	// ------------------------------------------------------

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.typesystem.constraints.ConstraintsBrowserPanel#ConstraintsBrowserPanel(java.lang.String, de.lichtflut.rb.webck.models.basic.LoadableModel)}.
	 */
	@Test
	public void testConstraintsBrowserPanel() {
		ConstraintsBrowserPanel panel = new ConstraintsBrowserPanel("panel",model) {
			@Override
			public void onCreateConstraint(final AjaxRequestTarget target) {

			}
			@Override
			public void onConstraintSelected(final Constraint constraint, final AjaxRequestTarget target) {

			};
		};

		tester.startComponentInPage(panel);
		tester.assertNoErrorMessage();
		tester.assertListView("panel:listview", model.getObject());
		for (Constraint constraint: model.getObject()) {
			tester.assertContains(constraint.getName());
		}
	}
}
