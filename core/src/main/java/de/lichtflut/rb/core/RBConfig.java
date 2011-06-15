/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core;

import org.arastreju.sge.ArastrejuProfile;

/**
 * <p>
 *  TODO: [DESCRIPTION].
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
			profile = ArastrejuProfile.read();
		} else {
			profile = ArastrejuProfile.read(profileName);
		}
		final String storeDir = System.getProperty(ARAS_STORE_DIRECTORY);
		if (storeDir != null) {
			profile.setProperty(ARAS_STORE_DIRECTORY, storeDir);
		}
	}

}
