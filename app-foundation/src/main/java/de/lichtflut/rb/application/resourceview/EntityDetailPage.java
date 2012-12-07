/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.resourceview;

import de.lichtflut.rb.application.base.RBBasePage;
import de.lichtflut.rb.application.common.CommonParams;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.webck.browsing.BrowsingHistory;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.ResourceBrowsingPanel;
import de.lichtflut.rb.webck.components.navigation.BreadCrumbsBar;
import de.lichtflut.rb.webck.components.notes.NotePadPanel;
import de.lichtflut.rb.webck.models.BrowsingContextModel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

/**
 * <p>
 * This page displays a Resource in an Editor.
 * </p>
 * 
 * <p>
 * Created: Aug 16, 2011
 * </p>
 * 
 * @author Ravi Knox
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class EntityDetailPage extends RBBasePage {

	/**
	 * Constructor.
	 * 
	 * @param params PageParameters might contain<ul>
	 * <li>{@link DisplayMode}</li>
	 * <li>{@link CommonParams#PARAM_RESOURCE_ID}.</li>
	 * </ul>
	 */
	public EntityDetailPage(final PageParameters params) {
		super(params);

		final EntityHandle handle = createHandle(params);
		DisplayMode displayMode = DisplayMode.fromParams(params);
		final boolean editmode = !DisplayMode.VIEW.equals(displayMode);
		if (handle != null) {
			add(createBrowser("rb"));
			initHistory(handle, editmode);
		} else {
			add(new WebMarkupContainer("rb").setVisible(false));
		}

		add(new NotePadPanel("notes", BrowsingContextModel.currentEntityModel()));
	}

	/**
	 * Constructor. Displays current entity in browsing context.
	 */
	public EntityDetailPage() {
		add(createBrowser("rb"));
		add(new NotePadPanel("notes", BrowsingContextModel.currentEntityModel()));
	}

	// ----------------------------------------------------

	@Override
	protected Component createSecondLevelNav(final String componentID) {
		return new BreadCrumbsBar(componentID, 7);
	}

    protected Component createBrowser(final String componentID) {
        return new ResourceBrowsingPanel(componentID);
    }

	// -----------------------------------------------------

	private EntityHandle createHandle(final PageParameters params) {
		final StringValue idParam = params.get(CommonParams.PARAM_RESOURCE_ID);
		final StringValue typeParam = params.get(CommonParams.PARAM_RESOURCE_TYPE);

		if (!idParam.isEmpty()) {
			final ResourceID id = new SimpleResourceID(idParam.toString());
			return EntityHandle.forID(id);
		} else if (!typeParam.isEmpty()) {
			final ResourceID type = new SimpleResourceID(typeParam.toString());
			return EntityHandle.forType(type);
		} else {
			return null;
		}
	}

	private void initHistory(final EntityHandle handle, final boolean editmode) {
		final BrowsingHistory history = RBWebSession.get().getHistory();
		if (editmode && handle.hasId()) {
			history.edit(handle);
		} else if (editmode && handle.hasType()) {
			history.create(handle);
		} else {
			history.view(handle);
		}
	}

}
