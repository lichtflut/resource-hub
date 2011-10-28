/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.entities;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.mock.MockNewRBEntityFactory;
import de.lichtflut.rb.webck.components.CKComponent;
import de.lichtflut.rb.webck.components.IFeedbackContainerProvider;
import de.lichtflut.rb.webck.components.ResourceDetailPanel;

/**
 * <p>
 * This webpage displays an example Resource in an Editor.
 * </p>
 * Created: Aug 16, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class EntityDetailPage extends EntitySamplesBasePage implements IFeedbackContainerProvider {

	public static final String PARAM_RESOURCE_ID = "rid";
	public static final String PARAM_RESOURCE_Type = "type";
	
	// -----------------------------------------------------
	
	/**
	 * Contructor.
	 */
	public EntityDetailPage() {
		this(MockNewRBEntityFactory.createPerson());
	}
	
	/**
	 * Constructor.
	 * @param params
	 */
	public EntityDetailPage(final PageParameters params) {
		super("Entity Details", params);
		final StringValue idParam = params.get(PARAM_RESOURCE_ID);
		final StringValue typeParam = params.get(PARAM_RESOURCE_Type);
		final EntityManager em = getServiceProvider().getEntityManager();
		if (!idParam.isEmpty()) {
			final RBEntity entity = em.find(new SimpleResourceID(idParam.toString()));
			initView(entity);
		} else if (!typeParam.isEmpty()) {
			final RBEntity entity = em.create(new SimpleResourceID(typeParam.toString()));
			initView(entity);
		}
	}

	/**
	 * Contructor.
	 * @param entity - instance of {@link RBEntity}
	 */
	public EntityDetailPage(final RBEntity entity) {
		super("Entity Details (Mock Mode)");
		initView(entity);
	}
	
	// -----------------------------------------------------
	
	protected void initView(RBEntity entity) {
		add(new FeedbackPanel("feedback").setOutputMarkupPlaceholderTag(true));
		add(new ResourceDetailPanel("mockEmployee", entity){
			@Override
			public CKComponent setViewMode(final ViewMode mode) {
				this.setEnabled(false);
				return null;
			}
			@Override
			public ServiceProvider getServiceProvider() {
				return EntityDetailPage.this.getServiceProvider();
			}
		});
	}
	
	// -----------------------------------------------------

	@Override
	public FeedbackPanel getFeedbackContainer() {
		return (FeedbackPanel) get("feedback");
	}


}
