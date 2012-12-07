/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import static org.mockito.Mockito.when;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.Test;

import de.lichtflut.rb.AbstractRBWebTest;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.mock.RBEntityFactory;

/**
 * <p>
 * Testclass for {@link QuickInfoPanel}
 * </p>
 * Created: Sep 24, 2012
 *
 * @author Ravi Knox
 */
public class QuickInfoPanelTest extends AbstractRBWebTest {

	/**
	 * Test method for {@link de.lichtflut.rb.webck.components.entity.QuickInfoPanel#QuickInfoPanel(java.lang.String, IModel)}.
	 */
	@Test
	public void testQuickInfoPanel() {
		RBEntity entity = RBEntityFactory.createPersonEntity();
		QuickInfoPanel panel = new QuickInfoPanel("panel", new Model<RBEntity>(entity));

		tester.startComponentInPage(panel);
		tester.assertNoErrorMessage();
		tester.assertContains(entity.getLabel());
		tester.assertListView("panel:quickInfo", entity.getQuickInfo());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setupTest() {
		QualifiedName qname = new QualifiedName("quickInfoPanelTest");
		RBDomain domain = new RBDomain(qname);
		when(serviceContext.getDomain()).thenReturn(qname.toURI());
		when(authModule.getDomainManager()).thenReturn(domainManager);
		when(domainManager.findDomain(qname.toURI())).thenReturn(domain);
	}

}
