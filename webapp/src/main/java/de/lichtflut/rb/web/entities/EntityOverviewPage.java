/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.entities;

import org.apache.commons.lang3.Validate;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.components.ResourceTableView;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import de.lichtflut.rb.webck.components.listview.EntityListPanel;
import de.lichtflut.rb.webck.components.listview.ReferenceLink;
import de.lichtflut.rb.webck.models.RBEntityListModel;

/**
 * <p>
 * 	SamplePage for {@link ResourceTableView}.
 * </p>
 *
 * <p>
 * 	Created: Aug 18, 2011
 * </p>
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class EntityOverviewPage extends EntitySamplesBasePage {

	/**
	 * Constructor.
	 * @param params -
	 */
	public EntityOverviewPage(final PageParameters params) {
		super("Entity Overview");
		final StringValue uri = params.get("type");
		Validate.notBlank(uri.toString(), "No type specified.");
		initTable(new SimpleResourceID(uri.toString()));
	}
	
	// -----------------------------------------------------
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void initTable(final ResourceID type) {
		final RBEntityListModel model = new RBEntityListModel(type) {
			protected ServiceProvider getServiceProvider() {
				return EntityOverviewPage.this.getServiceProvider();
			}
		};
		
		final EntityListPanel list = new EntityListPanel("listView", model, ColumnConfiguration.defaultConfig()) {
			
			@Override
			protected Component createViewAction(final String componentId, final RBEntity entity) {
				return new ReferenceLink(componentId, EntityDetailPage.class, entity.getID(), new ResourceModel("action.view"));
			}
			
			@Override
			protected Component createEditAction(final String componentId, final RBEntity entity) {
				return new ReferenceLink(componentId, EntityDetailPage.class, entity.getID(), new ResourceModel("action.edit"));
			}
			
			@Override
			protected void onDelete(final RBEntity entity, final AjaxRequestTarget target) {
				getServiceProvider().getEntityManager().delete(entity);
				model.reset();
				target.add(this);
			}
			
		};
		add(list);
		
		final PageParameters params = new PageParameters();
		params.add("type", type.getQualifiedName().toURI());
		final Link createLink = new BookmarkablePageLink("createLink", EntityDetailPage.class, params);
		add(createLink);
	}

}
