/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.infomanagement;

import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.web.RBBasePage;
import de.lichtflut.rb.web.util.ServiceProviderLocator;
import de.lichtflut.rb.webck.components.infomanagement.InformationIOPanel;

/**
 * <p>
 *  Page presenting the components for management of type system.
 * </p>
 *
 * <p>
 * 	Created Sep 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class InformationManagementPage extends RBBasePage {
	
	@SpringBean
	private ServiceProvider provider;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public InformationManagementPage() {
		super("Information Management");
		
		add(new InformationIOPanel("infoIO") {
			@Override
			protected ServiceProvider getServiceProvider() {
				return InformationManagementPage.this.getServiceProvider();
			}
		});
	}
	
	// -----------------------------------------------------
	
	protected ServiceProvider getServiceProvider() {
		return provider;
	}
	
}
