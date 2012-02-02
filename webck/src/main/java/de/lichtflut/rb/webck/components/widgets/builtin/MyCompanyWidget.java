/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.builtin;

import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.editor.EntityPanel;
import de.lichtflut.rb.webck.components.widgets.PredefinedWidget;
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
	
	@SpringBean
	private ServiceProvider provider;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param spec The specification.
	 */
	public MyCompanyWidget(String id, WidgetSpec spec) {
		super(id, new ResourceModel("widget.title.my-company"));
		
		add(new EntityPanel("entity", new CurrentOrganizationModel() {
			@Override
			public ServiceProvider getServiceProvider() {
				return provider;
			}
		}));
		
	}
	
}
