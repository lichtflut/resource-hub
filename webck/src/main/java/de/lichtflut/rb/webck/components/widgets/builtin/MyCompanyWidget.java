/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.builtin;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.*;
import de.lichtflut.rb.webck.components.entity.EntityPanel;
import de.lichtflut.rb.webck.components.entity.EntityInfoPanel;
import de.lichtflut.rb.webck.components.widgets.PredefinedWidget;
import static de.lichtflut.rb.webck.models.ConditionalModel.*;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.CurrentOrganizationModel;

/**
 * <p>
 *  Widget showing the organization associated with the current user.
 * </p>
 *
 * <p>
 * 	Created Feb 2, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class MyCompanyWidget extends PredefinedWidget {
	
	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param spec The specification.
	 */
	public MyCompanyWidget(String id, WidgetSpec spec, ConditionalModel<Boolean> perspectiveInConfigMode) {
		super(id, Model.of(spec), perspectiveInConfigMode);
		
		final CurrentOrganizationModel model = new CurrentOrganizationModel();
		
		add(new EntityInfoPanel("info", model));
		
		add(new EntityPanel("entity", model));
		
		add(new Label("noEntityHint", new ResourceModel("message.company-not-found"))
				.add(visibleIf(isNull(model))));
		
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected IModel<String> getTitleModel() {
		return new ResourceModel("title");
	}
}
