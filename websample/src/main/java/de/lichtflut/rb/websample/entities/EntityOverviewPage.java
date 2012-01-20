/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.websample.entities;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import de.lichtflut.rb.webck.components.listview.ReferenceLink;
import de.lichtflut.rb.webck.components.listview.ResourceListPanel;
import de.lichtflut.rb.webck.models.resources.ResourceListModel;

/**
 * <p>
 * 	SamplePage for an overview of entities.
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EntityOverviewPage(final PageParameters params) {
		super("Entity Overview");
		
		final ResourceID type = getResourceID(params);
		final ResourceListModel model = new ResourceListModel(type) {
			@Override
			protected ServiceProvider getServiceProvider() {
				return EntityOverviewPage.this.getServiceProvider();
			}
		};
		
		final ResourceSchema schema = getServiceProvider().getSchemaManager().findSchemaForType(type);
		final ColumnConfiguration config = ColumnConfiguration.defaultConfig();
		config.addColumnsFromSchema(schema);
		
		final ResourceListPanel list = new ResourceListPanel("listView", model, config) {
			
			@Override
			protected Component createViewAction(final String componentId, final ResourceNode entity) {
				return new ReferenceLink(componentId, EntityDetailPage.class, entity, new ResourceModel("action.view"));
			}
			
			@Override
			protected Component createEditAction(final String componentId, final ResourceNode entity) {
				final PageParameters params = new PageParameters();
				params.set(EntityDetailPage.PARAM_RESOURCE_ID, entity.getQualifiedName().toURI());
				params.set(EntityDetailPage.PARAM_MODE, EntityDetailPage.MODE_EDIT);
				return new ReferenceLink(componentId, EntityDetailPage.class, params, new ResourceModel("action.edit"));
			}
			
			@Override
			protected void onDelete(final ResourceNode entity, final AjaxRequestTarget target) {
				getServiceProvider().getEntityManager().delete(entity);
				model.reset();
				target.add(this);
			}
			
		};
		add(list);
		
		final PageParameters linkParams = new PageParameters();
		linkParams.add(EntityDetailPage.PARAM_RESOURCE_TYPE, type.getQualifiedName().toURI());
		linkParams.set(EntityDetailPage.PARAM_MODE, EntityDetailPage.MODE_EDIT);
		final Link createLink = new BookmarkablePageLink("createLink", EntityDetailPage.class, linkParams);
		add(createLink);
	}
	
	// ----------------------------------------------------
	
	private ResourceID getResourceID(final PageParameters params) {
		final StringValue uri = params.get("type");
		if (uri.isEmpty()) {
			return new SimpleResourceID();
		} else {
			return new SimpleResourceID(uri.toString());
		}
	}

}
