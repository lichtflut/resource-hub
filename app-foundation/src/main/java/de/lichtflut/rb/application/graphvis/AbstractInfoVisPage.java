/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.graphvis;

import de.lichtflut.rb.application.pages.AbstractBasePage;
import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

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
	public AbstractInfoVisPage(PageParameters parameters) {
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
