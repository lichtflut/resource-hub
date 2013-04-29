/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.services;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.config.RBConfig;

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
		final Arastreju aras = Arastreju.getInstance(config.getArastrejuProfile());
		return aras.openMasterGate();
	}
	

}
