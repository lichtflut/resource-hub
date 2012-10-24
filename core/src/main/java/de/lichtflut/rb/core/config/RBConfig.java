/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.config;

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
public class RBConfig {

	/**
	 * Arastreju store directory.
	 */
	public static final String DOMAIN_WORK_DIRECTORY = "de.lichtflut.rb.workdir";

	public static final String VIRTUAL_DOMAINS = "de.lichtflut.rb.virtual-domains";

    // ----------------------------------------------------

    private final static Logger LOGGER = LoggerFactory.getLogger(RBConfig.class);

	// ----------------------------------------------------

	/**
	 * Name of the application.
	 */
	private final String appName;

	/**
	 * Profile.
	 */
	private ArastrejuProfile profile;

	// -----------------------------------------------------

	/**
	 * Creates a new configuration object for an RB application with the given name.
     * There is an Arastreju configuration file with the name {appName}.profile expected
     * to be in the classpath.
	 * @param appName The application name.
	 */
	public RBConfig(final String appName) {
		this.appName = appName;
	}

	// -----------------------------------------------------

	/**
	 * @return The Arastreju configuration properties.
	 */
	public ArastrejuProfile getArastrejuProfile(){
		if (profile == null) {
			initProfile();
		}
		return profile;
	}

	/**
	 * @return the work directory for this profile
	 */
	public String getWorkDirectory() {
		// 1st: check profile specific work directory
		if (appName != null) {
			final String workDir = System.getProperty(DOMAIN_WORK_DIRECTORY + "." + appName);
			if (workDir != null) {
				return workDir;
			}
		}
		// 2nd: check global work directory
		return System.getProperty(DOMAIN_WORK_DIRECTORY);
	}

    // ----------------------------------------------------

	@Override
	public String toString() {
		return appName;
	}

	// -----------------------------------------------------

	/**
	 * Initializes a profile.
	 */
	private void initProfile(){
		if (appName == null) {
			LOGGER.info("Initialising Arastreju default profile");
			profile = ArastrejuProfile.read();
		} else {
			LOGGER.info("Initialising Arastreju profile with name " + appName);
			profile = ArastrejuProfile.read(appName);
		}
		checkWorkDir();
		checkVirtualDomains();
	}

	private void checkWorkDir() {
		final String workDir = getWorkDirectory();
		if (workDir != null) {
			LOGGER.info("Using work directory {} for profile {}.", workDir, appName);
			profile.setProperty(ArastrejuProfile.ARAS_STORE_DIRECTORY, workDir);
		} else {
			LOGGER.info("Using default directory for profile {}.", appName);
		}
	}

	private void checkVirtualDomains() {
		String vd = System.getProperty(VIRTUAL_DOMAINS, "off");
		if ("default".equals(vd)) {
			LOGGER.info("Enabling virtual domains for profile {}. ", appName);
			profile.setProperty(ArastrejuProfile.ENABLE_VIRTUAL_DOMAINS, "yes");
		}
	}

}
