/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.websample.entities;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.browsing.BrowsingHistory;
import de.lichtflut.rb.webck.components.ResourceBrowsingPanel;
import de.lichtflut.rb.webck.components.editor.VisualizationMode;

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
	
	private Component initBrowser(final EntityHandle handle, final boolean readonly) {
		final BrowsingHistory history = RBWebSession.get().getHistory();
		history.view(handle);
		if (!readonly) {
			history.edit(handle);
		}
		final Browser browser = new Browser("rb");
		add(browser);
		return browser;
	}
	
	// -----------------------------------------------------

	class Browser extends ResourceBrowsingPanel {
		
		public Browser(final String id) {
			super(id);
		}

		@Override
		public CharSequence getUrlToResource(ResourceID id, VisualizationMode mode) {
			final PageParameters params = new PageParameters();
			params.add(EntityDetailPage.PARAM_RESOURCE_ID, id.getQualifiedName().toURI());
			return RequestCycle.get().urlFor(EntityDetailPage.class, params);
		}

	}

}
