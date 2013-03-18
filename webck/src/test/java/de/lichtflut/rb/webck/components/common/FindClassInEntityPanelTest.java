/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.common;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.FormTester;
import org.arastreju.sge.model.ResourceID;
import org.junit.Test;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.RBWebTest;
import de.lichtflut.rb.webck.components.fields.ClassPickerField;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
import de.lichtflut.rb.webck.data.RBEntityFactory;
import de.lichtflut.rb.webck.data.RBTestConstants;


/**
 * <p>
 * Testclass of {@link FindClassInEntityPanel}.
 * </p>
 * Created: Mar 18, 2013
 *
 * @author Ravi Knox
 */
public class FindClassInEntityPanelTest extends RBWebTest{

	@Test
	public  void testFindClassInEntityPanel() throws Exception {
		simulatePathbuilder();

		Panel panel = new FindClassInEntityPanel("panel");

		tester.startComponentInPage(panel);

		assertRenderedPanel(FindClassInEntityPanel.class, "panel");
		tester.assertComponent("panel:header", PanelTitle.class);
		tester.assertComponent("panel:form:classPicker", ClassPickerField.class);
		tester.assertComponent("panel:form:entityPicker", EntityPickerField.class);
		tester.assertComponent("panel:form:search", AjaxButton.class);
		tester.assertInvisible("panel:result");
	}

	@Test
	public  void testFindClassInEntityPanelOnClick() throws Exception {
		IModel<ResourceID> wanted = new Model<ResourceID>(RB.PERSON);
		RBEntity entity = RBEntityFactory.createPersonEntity();
		entity.getField(RBTestConstants.HAS_CHILDREN).addValue(RBEntityFactory.createPersonEntity());
		IModel<ResourceID> target = new Model<ResourceID>(entity.getNode());
		simulatePathbuilder();

		when(networkService.find(target.getObject().getQualifiedName())).thenReturn(entity.getNode());

		Panel panel = new FindClassInEntityPanel("panel", wanted, target);

		tester.startComponentInPage(panel);

		assertRenderedPanel(FindClassInEntityPanel.class, "panel");

		FormTester formTester = tester.newFormTester("panel:form");

		formTester.setValue(tester.getComponentFromLastRenderedPage("panel:form:classPicker:display"), wanted.getObject().toURI());
		formTester.setValue(tester.getComponentFromLastRenderedPage("panel:form:classPicker:acValue"), wanted.getObject().toURI());

		formTester.setValue(tester.getComponentFromLastRenderedPage("panel:form:entityPicker:display"), target.getObject().toURI());
		formTester.setValue(tester.getComponentFromLastRenderedPage("panel:form:entityPicker:acValue"), target.getObject().toURI());

		tester.executeAjaxEvent("panel:form:search", "onclick");

		verify(networkService, times(1)).find(target.getObject().getQualifiedName());
	}

}
