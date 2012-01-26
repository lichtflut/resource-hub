/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.websample.infomanagement;

import de.lichtflut.rb.webck.components.infomanagement.InformationIOPanel;
import de.lichtflut.rb.websample.base.RBBasePage;

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
	
	/**
	 * Constructor.
	 */
	public InformationManagementPage() {
		super("Information Management");
		
		add(new InformationIOPanel("infoIO"));
	}
	
}
