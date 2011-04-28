/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi.impl;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.core.spi.ResourceSchemaManagement;

/**
 * Reference implementation of {@link RBServiceProvider}
 * TODO: The rootContext of ArastrejuGate is used, this should be changed
 *  
 * 
 * Created: Apr 28, 2011
 *
 * @author Nils Bleisch
 */
public class DefaultRBServiceProvider implements RBServiceProvider {

	private ArastrejuGate gate = null;
	private ResourceSchemaManagement schemaManagement = null;
	
	public DefaultRBServiceProvider(){
		init();
	}
	
	/**
	 * initalizing the services
	 */
	private void init(){
		gate = Arastreju.getInstance().rootContext();
		schemaManagement = new ResourceSchemaManagementImpl(gate);
	}
	
	
	public ResourceSchemaManagement getResourceSchemaManagement() {
		return schemaManagement;
	}

	public ArastrejuGate openArastejuGateInstance() {
		return gate;
	}

}
