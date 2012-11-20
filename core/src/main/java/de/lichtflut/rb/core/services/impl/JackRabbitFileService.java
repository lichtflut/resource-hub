/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.config.FileServiceConfiguration;
import de.lichtflut.rb.core.config.RBConfig;
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
public class JackRabbitFileService implements FileService {

	public static final String FILE_SERVICE_JACK_RABBIT_CONFIG_XML = "file-service.jack-rabbit.config-xml";

	private final static Logger LOGGER = LoggerFactory.getLogger(JackRabbitFileService.class);

	protected RepositoryDelegator delegator;

	private final RBConfig rbConfig;

	private final FileServiceConfiguration fsConfiguration;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor for file service factory.
	 * @param rbConfig The RB configuration.
	 * @param fsConfiguration The file service configuration.
	 */
	public JackRabbitFileService(final RBConfig rbConfig, final FileServiceConfiguration fsConfiguration) {
		this.rbConfig = rbConfig;
		this.fsConfiguration = fsConfiguration;
		initRepository();
	}

	// ------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContentDescriptor getData(final String id) {
		LOGGER.info("Retrieving file for id: {}", id);
		return delegator.getData(id);
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
	public boolean exists(final String id) {
		return delegator.exists(id);
	}

	/**
	 * Returns only the part after the last "/" of a id.
	 * @param path
	 * @return
	 */
	public static String getSimpleName(final String path){
		if(null == path || path.isEmpty()){
			return "";
		}
		String[] strings = path.split("/");
		if(null == strings || strings.length == 0){
			return path;
		}
		return strings[strings.length-1];
	}

	// ------------------------------------------------------

	protected void initRepository() {
		final String jcrConfigFile = fsConfiguration.getProperty(FILE_SERVICE_JACK_RABBIT_CONFIG_XML);
		delegator = new RepositoryDelegatorImpl("admin","adminId") {
			@Override
			protected RepositoryConfigWrapper getConfig() {
				String home = getHomeDirectory();
				return new RepositoryConfigWrapper(home, jcrConfigFile);
			}
		};
	}

	// ------------------------------------------------------

	private String getHomeDirectory() {
		String home = rbConfig.getWorkDirectory();
		if(null == home || home.isEmpty()){
			home = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + UUID.randomUUID().toString();
		}
		home = home + System.getProperty("file.separator") + "content-repository";
		LOGGER.info("Using repository location: {}", home);
		return home;
	}

}
