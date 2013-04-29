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
package de.lichtflut.rb.application.graphvis;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.application.pages.AbstractBasePage;
import de.lichtflut.rb.webck.models.CurrentUserModel;
import de.lichtflut.rb.webck.models.domains.CurrentDomainModel;

/**
 * <p>
 *  Base page for information visualization.
 * </p>
 *
 * <p>
 * 	Created Mar 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class AbstractInfoVisPage extends AbstractBasePage {

	public static final String PARAM_RID = "rid";

	// ----------------------------------------------------

	/**
	 * @param parameters
	 */
	public AbstractInfoVisPage(final PageParameters parameters) {
		super(parameters);

		add(new Label("username", CurrentUserModel.displayNameModel()));
		add(new Label("domain", CurrentDomainModel.displayNameModel()));

		final StringValue ridParam = parameters.get(PARAM_RID);
		if (ridParam.isEmpty()) {
			throw new WicketRuntimeException("No valid resource ID.");
		}

		add(createInfoVisPanel("infovis", new SimpleResourceID(ridParam.toString())));

	}

	// ----------------------------------------------------

	/**
	 * Create the visualization pane.
	 * @param componentID The component ID to use.
	 * @param resource The ID of the resource to visualize.
	 * @return The component.
	 */
	protected abstract Component createInfoVisPanel(String componentID, ResourceID resource);

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void addLanguageLinks() {
		// suppress
	}

}
