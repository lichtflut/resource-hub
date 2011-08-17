/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.mockObjects;

import de.lichtflut.rb.core.mock.MockNewRBEntityFactory;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.RBSuperPage;
import de.lichtflut.rb.web.ck.components.CKComponent;
import de.lichtflut.rb.web.ck.components.GenericResourceDetailPanel;

/**
 * <p>
 * This webpage displays an example Resource in an Editor.
 * </p>
 * Created: Aug 16, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class Employee extends RBSuperPage {

	/**
	 * Contructor.
	 */
	public Employee() {
		super("Mockpage-Employee");
		add(new GenericResourceDetailPanel("mockEmployee", MockNewRBEntityFactory.createNewRBEntity()) {

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
