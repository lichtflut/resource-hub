/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

			}
		};

		tester.startComponentInPage(panel);
		tester.assertNoErrorMessage();
		tester.assertListView("panel:listview", model.getObject());
		for (Constraint constraint: model.getObject()) {
			tester.assertContains(constraint.getName());
		}
	}
}
