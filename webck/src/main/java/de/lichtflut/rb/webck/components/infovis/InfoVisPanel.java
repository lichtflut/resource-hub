/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis;

import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import de.lichtflut.rb.webck.components.infovis.common.CurrentNodeInfoPanel;
import de.lichtflut.rb.webck.components.infovis.js.InfoVisJavaScriptResources;
import de.lichtflut.rb.webck.config.DefaultInfoVisServicePathBuilder;
import de.lichtflut.rb.webck.config.DefaultQueryServicePathBuilder;
import org.apache.wicket.IResourceListener;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.IResourceStream;
import org.arastreju.sge.model.nodes.ResourceNode;

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
public abstract class InfoVisPanel extends TypedPanel<ResourceNode> implements IResourceListener {

    @SpringBean
    private ServiceContext context;

    // ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model Model containing the initially selected node.
	 * @param mode The visualization mode.
	 */
	public InfoVisPanel(String id, IModel<ResourceNode> model, VisualizationMode mode) {
		super(id, model);
		
		add(new CurrentNodeInfoPanel("info", model, mode));
	}
	
	// ----------------------------------------------------
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavaScriptReference(InfoVisJavaScriptResources.INFOVIS_JS);
		response.renderJavaScriptReference(getScriptUrl());

		response.renderJavaScript("LFRB.InfoVis.contextPath='"
                + RequestCycle.get().getRequest().getContextPath() + "';", "LFRB.InfoVis.contextPath");

        String serviceURI = new DefaultInfoVisServicePathBuilder().getTree(context.getDomain(), getModelObject().toURI());
        response.renderJavaScript("LFRB.InfoVis.serviceURI='" + serviceURI + "';", "LFRB.InfoVis.serviceURI");
	}

	@Override
	public void onResourceRequested() {
		final RequestCycle cycle = RequestCycle.get();
		final Attributes a = new Attributes(cycle.getRequest(), cycle.getResponse(), null);
		new ResourceStreamResource(getJsonResource()).respond(a);
	}
	
	// ----------------------------------------------------
	
	protected abstract IResourceStream getJsonResource();
	
	// ----------------------------------------------------
	
	protected String getScriptUrl() {
		final PageParameters params = new PageParameters();
		params.add("uid", System.nanoTime());
		return urlFor(IResourceListener.INTERFACE, params).toString();
	}

}
