/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi.impl;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ArastrejuProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.api.RBEntityManagement;
import de.lichtflut.rb.core.api.impl.ResourceSchemaManagementImpl;
import de.lichtflut.rb.core.api.impl.RBEntityManagementImpl;
import de.lichtflut.rb.core.spi.RBServiceProvider;

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
	
	private Logger logger = LoggerFactory.getLogger(DefaultRBServiceProvider.class);

	private ArastrejuGate gate = null;
	private ResourceSchemaManagement schemaManagement = null;
	private RBEntityManagement typeManagement = null;
	
	
	// --CONSTRUCTOR----------------------------------------
	
	/**
	 * Default constructor.
	 */
	public DefaultRBServiceProvider(){
		final RBConfig config = new RBConfig();
		final ArastrejuProfile profile = config.getArastrejuConfiguration();
		logger.info("Initializing Arastreju with profile: " + profile);
		gate = Arastreju.getInstance(profile).rootContext();
		schemaManagement = new ResourceSchemaManagementImpl(gate);
		typeManagement = new RBEntityManagementImpl(gate);
	}
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public ResourceSchemaManagement getResourceSchemaManagement() {
		return schemaManagement;
	}

	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public ArastrejuGate openArastejuGateInstance() {
		return gate;
	}

	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public RBEntityManagement getRBEntityManagement() {
		return this.typeManagement;
	}

}
