/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RBConfig;

/**
 * <p>
 *  Factory for Arastreju Master Gates.
 * </p>
 *
 * <p>
 * 	Created May 11, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class MasterGateFactory {

	private final Logger logger = LoggerFactory.getLogger(MasterGateFactory.class);
	
	private final RBConfig config;
	
	// ----------------------------------------------------

	/** Constructor.
	 * @param config The RB configuration.
	 */
	public MasterGateFactory(RBConfig config) {
		this.config = config;
		logger.info("The MasterGateFactory has been created.");
	}
	
	// ----------------------------------------------------
	
	/**
	 * Creates the master gate.
	 * @return The master gate.
	 */
	public ArastrejuGate createMasterGate() {
		logger.info("Creating the master gate.");
		final Arastreju aras = Arastreju.getInstance(config.getArastrejuConfiguration());
		return aras.rootContext();
	}
	

}
