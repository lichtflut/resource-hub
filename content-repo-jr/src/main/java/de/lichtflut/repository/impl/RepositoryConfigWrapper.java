/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.repository.impl;

import java.io.File;

import org.apache.jackrabbit.core.config.RepositoryConfig;

/**
 * <p>
 * This is a simple wrapper object for a {@link RepositoryConfig}uration.
 * </p>
 * Created: Aug 3, 2012
 *
 * @author Ravi Knox
 */
public class RepositoryConfigWrapper {

	private final String path;
	private final File config;

	// ---------------- Constructor -------------------------

	/**
	 * @param home - path pointing to the content storage location
	 * @param config - xml file representing the necessary configuration
	 */
	public RepositoryConfigWrapper(final String home, final File config) {
		this.path = home;
		this.config = config;
	}

	// ------------------------------------------------------

	/**
	 * @return the config
	 */
	public File getConfig() {
		return config;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

}
