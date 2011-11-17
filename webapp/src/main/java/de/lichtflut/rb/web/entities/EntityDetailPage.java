/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.entities;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.mock.MockNewRBEntityFactory;
import de.lichtflut.rb.webck.components.IFeedbackContainerProvider;
import de.lichtflut.rb.webck.components.editor.EntityPanel;
import de.lichtflut.rb.webck.models.RBEntityModel;

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
		if (!idParam.isEmpty()) {
			initView(modelFor(new SimpleResourceID(idParam.toString()), null));
		} else if (!typeParam.isEmpty()) {
			initView(modelFor(null, new SimpleResourceID(typeParam.toString())));
		}
	}

	/**
	 * Contructor.
	 * @param entity - instance of {@link RBEntity}
	 */
	public EntityDetailPage(final RBEntity entity) {
		super("Entity Details (Mock Mode)");
		initView(Model.of(entity));
	}
	
	@Override
	public FeedbackPanel getFeedbackContainer() {
		return (FeedbackPanel) get("feedback");
	}
	
	// -----------------------------------------------------
	
	protected void initView(final IModel<RBEntity> model) {
		add(new FeedbackPanel("feedback").setOutputMarkupPlaceholderTag(true));
		add(new EntityPanel("entity", model) {
			@Override
			public EntityManager getEntityManager() {
				return getServiceProvider().getEntityManager();
			}
		});
	}
	
	// -----------------------------------------------------

	private IModel<RBEntity> modelFor(final ResourceID id, final ResourceID type) {
		return new RBEntityModel(id, type) {
			@Override
			protected ServiceProvider getServiceProvider() {
				return EntityDetailPage.this.getServiceProvider();
			}
		};
	}

}
