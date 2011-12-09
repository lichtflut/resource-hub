/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.entities;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.components.IFeedbackContainerProvider;
import de.lichtflut.rb.webck.components.ResourceBrowsingPanel;

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
 * @author Oliver Tigges
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
		
		final boolean readonly = mode.isEmpty() || !MODE_EDIT.equals(mode.toString());

		if (!idParam.isEmpty()) {
			final ResourceID id = new SimpleResourceID(idParam.toString());
			final EntityHandle handle = EntityHandle.forID(id);
			initBrowser(handle, readonly);
		} else if (!typeParam.isEmpty()) {
			final ResourceID type = new SimpleResourceID(typeParam.toString());
			final EntityHandle handle = EntityHandle.forType(type);
			initBrowser(handle, readonly);
		} else {
			add(new WebMarkupContainer("rb").setVisible(false));
		}
	}

	// ----------------------------------------------------
	
	@Override
	public FeedbackPanel getFeedbackContainer() {
		return (FeedbackPanel) get("feedback");
	}
	
	// ----------------------------------------------------
	
	protected Component initBrowser(final EntityHandle handle, final boolean readonly) {
		final Browser browser = new Browser("rb", handle, !readonly);
		add(browser);
		return browser;
	}
	
	// -----------------------------------------------------

	class Browser extends ResourceBrowsingPanel {
		
		public Browser(final String id, EntityHandle handle, boolean editable) {
			super(id, handle, editable);
		}

		@Override
		public CharSequence getUrlToResource(EntityHandle handle) {
			final PageParameters params = new PageParameters();
			if (handle.hasId()) {
				params.add(EntityDetailPage.PARAM_RESOURCE_ID, handle.getId().getQualifiedName().toURI());
			} else {
				params.add(EntityDetailPage.PARAM_RESOURCE_TYPE, handle.getType().getQualifiedName().toURI());
			}
			return RequestCycle.get().urlFor(EntityDetailPage.class, params);
		}

		@Override
		public ServiceProvider getServiceProvider() {
			return EntityDetailPage.this.getServiceProvider();
		}
	}

}
