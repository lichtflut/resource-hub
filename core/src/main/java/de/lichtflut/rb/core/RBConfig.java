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
	 * Arastreju store directory.
	 */
	public static final String DOMAIN_WORK_DIRECTORY = "de.lichtflut.rb.workdir";
	
	/**
	 * Default directory.
	 */
	public static final String DEFAULT_WORK_DIRECTORY = "root";
	
	// ----------------------------------------------------
	
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
	 * @param domain The domain to be used.
	 */
	public RBConfig(final String profileName) {
		this.profileName = profileName;
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
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return profileName;
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
		final String workDir = getWorkDirecotry();
		if (workDir != null) {
			profile.setProperty(ArastrejuProfile.ARAS_STORE_DIRECTORY, workDir);
		}
	}
	
	private String getWorkDirecotry() {
		// 1st: check profile specific work directory
		if (profileName != null) {
			final String workDir = System.getProperty(DOMAIN_WORK_DIRECTORY + "." + profileName);
			if (workDir != null) {
				return workDir;
			}
		}
		// 2nd: check global work directory
		return System.getProperty(DOMAIN_WORK_DIRECTORY);
	}

}
