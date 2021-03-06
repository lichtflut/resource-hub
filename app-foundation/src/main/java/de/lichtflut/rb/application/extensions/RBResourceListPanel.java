/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.application.extensions;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.application.common.CommonParams;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import de.lichtflut.rb.webck.components.listview.ReferenceLink;
import de.lichtflut.rb.webck.components.listview.ResourceListPanel;

/**
 * <p>
 *  RB Application extension.
 * </p>
 *
 * <p>
 * 	Created Dec 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBResourceListPanel extends ResourceListPanel {

	@SpringBean
	private EntityManager entityManager;

	@SpringBean
	private TypeManager typeManager;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id
	 * @param model
	 * @param config
	 */
	public RBResourceListPanel(final String id, final IModel<List<ResourceNode>> model, final IModel<ColumnConfiguration> config) {
		super(id, model, config);
	}

	// ----------------------------------------------------

	@Override
	protected Component createViewAction(final String componentId, final ResourceNode entity) {
		ResourceID type = typeManager.getTypeOfResource(entity);
		return new ReferenceLink(componentId, RBApplication.get().getEntityDetailPage(type), entity, new ResourceModel("action.view"))
		.setLinkCssClass("action-view")
		.setLinkTitle(new ResourceModel("action.view"));
	}

	@Override
	protected Component createEditAction(final String componentId, final ResourceNode entity) {
		ResourceID type = typeManager.getTypeOfResource(entity);
		final PageParameters params = new PageParameters();
		params.set(CommonParams.PARAM_RESOURCE_ID, entity.getQualifiedName().toURI());
		params.set(DisplayMode.PARAMETER, DisplayMode.EDIT);
		return new ReferenceLink(componentId, RBApplication.get().getEntityDetailPage(type), params, new ResourceModel("action.edit"))
		.setLinkCssClass("action-edit")
		.setLinkTitle(new ResourceModel("action.edit"));
	}

	@Override
	protected void onDelete(final ResourceNode entity, final AjaxRequestTarget target) {
		entityManager.delete(entity);
		target.add(this);
	}

}