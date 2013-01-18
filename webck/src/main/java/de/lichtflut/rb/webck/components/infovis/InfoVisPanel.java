/*
 * Copyright 2013 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis;

import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import de.lichtflut.rb.webck.components.infovis.common.CurrentNodeInfoPanel;
import de.lichtflut.rb.webck.components.infovis.js.InfoVisJavaScriptResources;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import de.lichtflut.rb.webck.config.DefaultInfoVisServicePathBuilder;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
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
public abstract class InfoVisPanel extends TypedPanel<ResourceNode> {

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
        response.render(JavaScriptHeaderItem.forReference(InfoVisJavaScriptResources.INFOVIS_JS));
        response.render(JavaScriptHeaderItem.forScript(
                "LFRB.InfoVis.contextPath='" + RequestCycle.get().getRequest().getContextPath()
                        + "';", "LFRB.InfoVis.contextPath"));

        String serviceURI = new DefaultInfoVisServicePathBuilder()
                .getTree(context.getDomain(), getModelObject().toURI());
        response.render(JavaScriptHeaderItem.forScript(
                "LFRB.InfoVis.serviceURI='" + serviceURI + "';", "LFRB.InfoVis.serviceURI"));
	}

}
