/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.entities;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.web.ck.behavior.CKBehavior;
import de.lichtflut.rb.web.ck.components.CKComponent;
import de.lichtflut.rb.web.ck.components.ResourceTableView;

/**
 * SamplePage for {@link ResourceTableView}.
 *
 * Created: Aug 18, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class EntityOverviewPage extends EntitySamplesBasePage {

	/**
	 * Constructor.
	 */
	public EntityOverviewPage() {
		super("Default View (Mock Mode)");
		initTable(null);
	}

	/**
	 * Constructor.
	 * @param params -
	 */
	public EntityOverviewPage(final PageParameters params) {
		super("Sample Entities (Mock Mode)");
		StringValue uri = params.get("type");
		initTable(new SimpleResourceID(uri.toString()));
	}
	
	// -----------------------------------------------------
	
	protected void initTable(final ResourceID type) {
		final ResourceTableView view =
			new ResourceTableView("mockEmployeeView", getRBServiceProvider().getEntityManager().findAllByType(type)){
			@Override
			public ServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}
			@Override
			public CKComponent setViewMode(final ViewMode mode) {return null;}
			
			@Override
			protected void onShowDetails(RBEntity entity) {
				final PageParameters params = new PageParameters();
				params.add(EntityDetailPage.PARAM_RESOURCE_ID, entity.getID());
				setResponsePage(EntityDetailPage.class, params);
			}
		};
		view.addBehavior(ResourceTableView.SHOW_DETAILS, CKBehavior.VOID_BEHAVIOR);
		view.addBehavior(ResourceTableView.UPDATE_ROW_ITEM, CKBehavior.VOID_BEHAVIOR);
		view.addBehavior(ResourceTableView.DELETE_ROW_ITEM, CKBehavior.VOID_BEHAVIOR);
		add(view);
	}

}
