/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.RepositoryDelegator;
import de.lichtflut.repository.impl.RepositoryConfigWrapper;
import de.lichtflut.repository.impl.RepositoryDelegatorImpl;

/**
 * <p>
 * Implementation for {@link FileService}
 * </p>
 * Created: Aug 3, 2012
 * 
 * @author Ravi Knox
 */
public class FileServiceImpl implements FileService {

	private final static Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

	protected RepositoryDelegator delegator;

	private final Properties properties;
	private final RBConfig rbConfig;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * @param config - path to repository config file
	 * @param rbConfig - {@link RBConfig} for storage directory
	 */
	public FileServiceImpl(final String config, final RBConfig rbConfig) {
		this.rbConfig = rbConfig;
		properties = getPropertiesFile(config);
		if(null == delegator){
			initRepository();
		}
	}

	// ------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InputStream getData(final String path) {
		LOGGER.info("Retrieving file for path: {}", path);
		return delegator.getData(path).getData();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void storeFile(final ContentDescriptor descriptor) {
		delegator.storeFile(descriptor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists(final String path) {
		return delegator.exists(path);
	}

	/**
	 * Returns only the part after the last "/" of a path.
	 * @param path
	 * @return
	 */
	public static String getSimpleName(final String path){
		String[] strings = path.split("/");
		if(null == strings || strings.length == 0){
			return path;
		}
		return strings[strings.length-1];
	}

	// ------------------------------------------------------

	protected void initRepository() {
		delegator = new RepositoryDelegatorImpl(getProperty("username", "anonymous"), getProperty("password", "anonymous")) {

			@Override
			protected RepositoryConfigWrapper getConfig() {
				String home = getHomeDirectory();
				String config = getProperty("config-file", "");
				return new RepositoryConfigWrapper(home, config);
			}

		};
	}

	// ------------------------------------------------------

	private String getProperty(final String key, final String defaultValue) {
		if (properties != null) {
			return properties.getProperty(key, defaultValue);
		}
		return "";
	}

	private String getHomeDirectory() {
		String home = rbConfig.getWorkDirecotry();
		if(null == home || home.isEmpty()){
			home = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + UUID.randomUUID().toString();
		}
		home = home + System.getProperty("file.separator") + "content-repository";
		LOGGER.info("Using repository location: {}", home);
		return home;
	}

	private Properties getPropertiesFile(final String config) {
		final Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(config));
			LOGGER.debug("Propertyfile loaded from: {}", config);
		} catch (final FileNotFoundException e) {
			LOGGER.error("Could not find Propertiesfile from: {}", config);
			e.printStackTrace();
		} catch (final IOException e) {
			LOGGER.error("Error while loading file from: {}", config);
			e.printStackTrace();
		}
		return properties;
	}

}
