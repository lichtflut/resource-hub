/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.mockPages;

import org.apache.wicket.markup.html.panel.FeedbackPanel;

import de.lichtflut.rb.core.mock.MockNewRBEntityFactory;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.RBSuperPage;
import de.lichtflut.rb.web.ck.components.CKComponent;
import de.lichtflut.rb.web.ck.components.ResourceDetailPanel;
import de.lichtflut.rb.web.components.IFeedbackContainerProvider;

/**
 * <p>
 * This webpage displays an example Resource in an Editor.
 * </p>
 * Created: Aug 16, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class EmployeePage extends RBSuperPage implements IFeedbackContainerProvider {

	/**
	 * Contructor.
	 */
	public EmployeePage() {
		super("Mockpage-Employee");
		add(new FeedbackPanel("feedback").setOutputMarkupPlaceholderTag(true));
		add(new ResourceDetailPanel("mockEmployee", MockNewRBEntityFactory.createComplexNewRBEntity()){

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

	@Override
	public FeedbackPanel getFeedbackContainer() {
		return (FeedbackPanel) get("feedback");
	}


}
