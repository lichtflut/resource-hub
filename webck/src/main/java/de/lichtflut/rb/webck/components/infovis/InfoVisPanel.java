/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.util.resource.IResourceStream;

import de.lichtflut.rb.webck.components.common.TypedPanel;

/**
 * <p>
 *  Basic information visualization panel.
 * </p>
 *
 * <p>
 * 	Created Mar 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class InfoVisPanel<T> extends TypedPanel<T> implements IResourceListener {

	/**
	 * Constructor.
	 * @param id
	 * @param model
	 */
	public InfoVisPanel(String id, IModel<T> model) {
		super(id, model);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavaScriptReference(getScriptUrl());
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onResourceRequested() {
		final RequestCycle cycle = RequestCycle.get();
		final Attributes a = new Attributes(cycle.getRequest(), cycle.getResponse(), null);
		new ResourceStreamResource(getJsonResource()).respond(a);
	}
	
	// ----------------------------------------------------
	
	protected abstract IResourceStream getJsonResource();
	
	// ----------------------------------------------------
	
	/**
	 * @return
	 */
	protected String getScriptUrl() {
		final PageParameters params = new PageParameters();
		params.add("uid", System.nanoTime());
		return urlFor(IResourceListener.INTERFACE, params).toString();
	}

}
