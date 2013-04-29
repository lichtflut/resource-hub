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
package de.lichtflut.rb.webck.components.dialogs;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.request.resource.ResourceStreamResource;

/**
 * <p>
 *  Abstract base for export dialogs.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class AbstractExportDialog extends AbstractRBDialog implements IResourceListener {

	/**
	 * @param id
	 */
	public AbstractExportDialog(String id) {
		super(id);
	}

	protected CharSequence getDownloadUrl() {
		return urlFor(IResourceListener.INTERFACE, new PageParameters());
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void onResourceRequested() {
		final RequestCycle cycle = RequestCycle.get();
		final Attributes a = new Attributes(cycle.getRequest(), cycle.getResponse(), null);
		final ResourceStreamResource resource = prepareResource();
		resource.respond(a);
	}

	// ----------------------------------------------------
	
	/**
	 * To be implemented by concrete classes.
	 */
	protected abstract ResourceStreamResource prepareResource();

}