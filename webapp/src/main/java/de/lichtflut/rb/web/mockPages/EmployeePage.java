/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.mockPages;

import org.apache.wicket.markup.html.panel.FeedbackPanel;

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.spi.INewRBServiceProvider;
import de.lichtflut.rb.mock.MockNewRBEntityFactory;
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
		add(new ResourceDetailPanel("mockEmployee", MockNewRBEntityFactory.createPerson()){
			@Override
			public CKComponent setViewMode(final ViewMode mode) {
				return null;
			}
			@Override
			public INewRBServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}
		});
	}

	/**
	 * Contructor.
	 * @param entity - instance of {@link IRBEntity}
	 */
	public EmployeePage(final IRBEntity entity) {
		super("Mockpage-Employee");
		add(new FeedbackPanel("feedback").setOutputMarkupPlaceholderTag(true));
		add(new ResourceDetailPanel("mockEmployee", entity){
			@Override
			public CKComponent setViewMode(final ViewMode mode) {
				return null;
			}
			@Override
			public INewRBServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}
		});
	}

	@Override
	public FeedbackPanel getFeedbackContainer() {
		return (FeedbackPanel) get("feedback");
	}


}
