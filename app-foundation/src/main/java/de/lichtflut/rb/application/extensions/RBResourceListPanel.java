/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.extensions;

import java.util.List;

import de.lichtflut.rb.application.common.CommonParams;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import de.lichtflut.rb.webck.components.listview.ReferenceLink;
import de.lichtflut.rb.webck.components.listview.ResourceListPanel;

/**
 * <p>
 *  Glasnost extension.
 * </p>
 *
 * <p>
 * 	Created Dec 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public final class RBResourceListPanel extends ResourceListPanel {

	@SpringBean
	private EntityManager entityManager;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param id
	 * @param model
	 * @param config
	 */
	public RBResourceListPanel(String id, IModel<List<ResourceNode>> model, IModel<ColumnConfiguration> config) {
		super(id, model, config);
	}
	
	// ----------------------------------------------------

	@Override
	protected Component createViewAction(final String componentId, final ResourceNode entity) {
		return new ReferenceLink(componentId, RBApplication.get().getEntityDetailPage(), entity, new ResourceModel("action.view"))
			.setLinkCssClass("action-view")
			.setLinkTitle(new ResourceModel("action.view"));
	}

	@Override
	protected Component createEditAction(final String componentId, final ResourceNode entity) {
		final PageParameters params = new PageParameters();
		params.set(CommonParams.PARAM_RESOURCE_ID, entity.getQualifiedName().toURI());
		params.set(DisplayMode.PARAMETER, DisplayMode.EDIT);
		return new ReferenceLink(componentId, RBApplication.get().getEntityDetailPage(), params, new ResourceModel("action.edit"))
			.setLinkCssClass("action-edit")
			.setLinkTitle(new ResourceModel("action.edit"));
	}

	@Override
	protected void onDelete(final ResourceNode entity, final AjaxRequestTarget target) {
		entityManager.delete(entity);
		target.add(this);
	}
	
}