/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.repository.impl;

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
	private final String config;

	// ---------------- Constructor -------------------------

	/**
	 * @param home - id pointing to the content storage location
	 * @param config - xml file representing the necessary configuration
	 */
	public RepositoryConfigWrapper(final String home, final String config) {
		this.path = home;
		this.config = config;
	}

	// ------------------------------------------------------

	/**
	 * @return the config
	 */
	public String getConfigPath() {
		return config;
	}

	/**
	 * @return the id
	 */
	public String getPath() {
		return path;
	}

}
