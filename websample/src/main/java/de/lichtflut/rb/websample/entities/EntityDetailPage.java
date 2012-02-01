/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.websample.entities;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.browsing.BrowsingHistory;
import de.lichtflut.rb.webck.common.DisplayMode;
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
public class EntityDetailPage extends EntitySamplesBasePage {

	public static final String PARAM_RESOURCE_ID = "rid";
	public static final String PARAM_RESOURCE_TYPE = "type";
	
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
		
		final boolean readonly = DisplayMode.VIEW.equals(DisplayMode.fromParams(params));

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
	
	private Component initBrowser(final EntityHandle handle, final boolean readonly) {
		final BrowsingHistory history = RBWebSession.get().getHistory();
		history.view(handle);
		if (!readonly) {
			history.edit(handle);
		}
		final ResourceBrowsingPanel browser = new ResourceBrowsingPanel("rb");
		add(browser);
		return browser;
	}
	
}
