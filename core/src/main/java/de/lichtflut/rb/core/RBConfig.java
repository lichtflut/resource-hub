/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core;

import org.arastreju.sge.ArastrejuProfile;

/**
 * <p>
 *  [DESCRIPTION]
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
	 * @return The Arastreju configuration properties.
	 */
	public ArastrejuProfile getArastrejuConfiguration() {
		final ArastrejuProfile profile = ArastrejuProfile.read();
		final String storeDir = System.getProperty(ARAS_STORE_DIRECTORY);
		if (storeDir != null) {
			profile.setProperty(ARAS_STORE_DIRECTORY, storeDir);
		}
		return profile;
	}

}
