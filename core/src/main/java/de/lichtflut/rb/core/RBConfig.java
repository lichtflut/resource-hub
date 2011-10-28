/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core;

import org.arastreju.sge.ArastrejuProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  Basic configuration class for an RB application.
 * </p>
 *
 * <p>
 * 	Created May 26, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBConfig implements RBConstants {
	
	/**
	 * Profilename.
	 */
	private final String profileName;
	/**
	 * Profile.
	 */
	private ArastrejuProfile profile;
	
	private final Logger logger = LoggerFactory.getLogger(RBConfig.class);

	// -----------------------------------------------------

	/**
	 * Default constructor.
	 */
	public RBConfig() {
		this.profileName = null;
	}

	/**
	 * Constructor.
	 * @param arastrejuProfile The profile name for Arastreju.
	 */
	public RBConfig(final String arastrejuProfile) {
		this.profileName = arastrejuProfile;
	}

	// -----------------------------------------------------


	/**
	 * @return The Arastreju configuration properties.
	 */
	public ArastrejuProfile getArastrejuConfiguration(){
		if (profile == null) {
			initProfile();
		}
		return profile;
	}

	// -----------------------------------------------------

	/**
	 * Initializes a profile.
	 */
	private void initProfile(){
		if (profileName == null) {
			logger.info("Initialising Arastreju default profile");
			profile = ArastrejuProfile.read();
		} else {
			logger.info("Initialising Arastreju profile with name " + profileName);
			profile = ArastrejuProfile.read(profileName);
		}
		final String storeDir = System.getProperty(ARAS_STORE_DIRECTORY);
		if (storeDir != null) {
			profile.setProperty(ARAS_STORE_DIRECTORY, storeDir);
		}
	}

}
