/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.entities;

import org.apache.wicket.markup.html.WebMarkupContainer;
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
import de.lichtflut.rb.web.WebsampleLinkProvider;
import de.lichtflut.rb.webck.application.LinkProvider;
import de.lichtflut.rb.webck.components.IFeedbackContainerProvider;
import de.lichtflut.rb.webck.components.editor.EntityPanel;
import de.lichtflut.rb.webck.models.RBEntityModel;

/**
 * <p>
 * 	This webpage displays an example Resource in an Editor.
 * </p>
 * 
 * <p>
 * 	Created: Aug 16, 2011
 * </p>
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class EntityDetailPage extends EntitySamplesBasePage implements IFeedbackContainerProvider {

	public static final String PARAM_RESOURCE_ID = "rid";
	public static final String PARAM_RESOURCE_TYPE = "type";
	public static final String PARAM_MODE = "mode";
	
	public static final String MODE_EDIT = "edit";
	public static final String MODE_VIEW = "view";
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param params
	 */
	public EntityDetailPage(final PageParameters params) {
		super("Entity Details", params);
		
		add(new FeedbackPanel("feedback").setOutputMarkupPlaceholderTag(true));
		
		final StringValue idParam = params.get(PARAM_RESOURCE_ID);
		final StringValue typeParam = params.get(PARAM_RESOURCE_TYPE);
		final StringValue mode = params.get(PARAM_MODE);
		final IModel<Boolean> readonly = toIsReadOnlyModel(mode);
		if (!idParam.isEmpty()) {
			initView(modelFor(new SimpleResourceID(idParam.toString()), null), readonly);
		} else if (!typeParam.isEmpty()) {
			initView(modelFor(null, new SimpleResourceID(typeParam.toString())), readonly);
		} else {
			add(new WebMarkupContainer("entity").setVisible(false));
		}
	}

	// ----------------------------------------------------
	
	@Override
	public FeedbackPanel getFeedbackContainer() {
		return (FeedbackPanel) get("feedback");
	}
	
	// -----------------------------------------------------
	
	protected void initView(final IModel<RBEntity> model, final IModel<Boolean> readonly) {
		add(new EntityPanel("entity", model, readonly) {
			@Override
			public EntityManager getEntityManager() {
				return getServiceProvider().getEntityManager();
			}
			@Override
			public LinkProvider getLinkProvider() {
				return new WebsampleLinkProvider();
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
	
	private IModel<Boolean> toIsReadOnlyModel(final StringValue mode) {
		final boolean readonly = mode.isEmpty() || !MODE_EDIT.equals(mode.toString());
		return new Model<Boolean>(readonly);
	}


}
