/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.constraints;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;

import de.lichtflut.rb.AbstractRBWebTest;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.impl.ConstraintImpl;
import de.lichtflut.rb.mock.schema.ConstraintsFactory;

/**
 * <p>
 * Testclass for {@link PublicConstraintsDetailPanel}.
 * </p>
 * Created: Oct 29, 2012
 *
 * @author Ravi Knox
 */
public class PublicConstraintsDetailPanelTest extends AbstractRBWebTest {

	IModel<Constraint> model;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setupTest() {
		model = new Model<Constraint>(ConstraintsFactory.buildPublicEmailConstraint());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.typesystem.constraints.PublicConstraintsDetailPanel#PublicConstraintsDetailPanel(java.lang.String, org.apache.wicket.model.IModel)}.
	 */
	@Test
	public void testPublicConstraintsDetailPanel() {
		PublicConstraintsDetailPanel panel= new PublicConstraintsDetailPanel("panel", model);

		tester.startComponentInPage(panel);

		assertRenderedPanel(PublicConstraintsDetailPanel.class, "panel");

		tester.assertNoErrorMessage();
		tester.assertContains(model.getObject().getName());
		tester.assertContains(model.getObject().getLiteralConstraint());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.typesystem.constraints.PublicConstraintsDetailPanel#save(org.apache.wicket.model.IModel)}.
	 */
	@Test
	public void testSave() {
		String changedName = "changed";
		String changedPattern = "*";
		// Make sure indexes match with selected values!
		int[] indexes = {0,2};

		ConstraintImpl updatedConstraint = new ConstraintImpl(model.getObject().getQualifiedName());
		updatedConstraint.setApplicableDatatypes(Arrays.asList(Datatype.BOOLEAN, Datatype.DECIMAL));
		updatedConstraint.setLiteralConstraint(changedPattern);
		updatedConstraint.setName(changedName);
		updatedConstraint.setPublic(true);

		PublicConstraintsDetailPanel panel= new PublicConstraintsDetailPanel("panel", model);
		tester.startComponentInPage(panel);

		FormTester form = tester.newFormTester("panel:form");

		tester.executeAjaxEvent("panel:form:name:label", "onclick");
		tester.assertVisible("panel:form:name:editor");
		tester.assertInvisible("panel:form:name:label");

		form.setValue("name:editor", changedName);
		form.selectMultiple("datatypes", indexes, true);

		// Submit so that values get updated by ajax
		form.submit();
		form = tester.newFormTester("panel:form");

		tester.executeAjaxEvent("panel:form:pattern:label", "onclick");
		tester.assertVisible("panel:form:pattern:editor");
		tester.assertInvisible("panel:form:pattern:label");
		form.setValue("pattern:editor", changedPattern);

		tester.executeAjaxEvent("panel:form:buttonBar:save", "onClick");

		verify(schemaManager, times(1)).store(updatedConstraint);
	}

}
