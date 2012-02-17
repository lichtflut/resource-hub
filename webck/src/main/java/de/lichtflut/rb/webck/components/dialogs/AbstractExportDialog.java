/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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