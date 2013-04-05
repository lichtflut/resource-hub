package de.lichtflut.rb.core.services.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.config.FileServiceConfiguration;
import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.RepositoryDelegator;
import de.lichtflut.repository.filestore.FileStoreRepository;

/**
 * <p>
 *  Simple implementation of FileService storing files in local fs.
 * </p>
 *
 * <p>
 *  Created 05.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class SimpleFileService implements FileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleFileService.class);

	@SuppressWarnings("unused")
	private final RBConfig rbConfig;

	@SuppressWarnings("unused")
	private final FileServiceConfiguration fsConfiguration;

	private final RepositoryDelegator delegate;

	// ----------------------------------------------------

	public SimpleFileService(final RBConfig rbConfig, final FileServiceConfiguration fsConfiguration) {
		this.rbConfig = rbConfig;
		this.fsConfiguration = fsConfiguration;
		this.delegate = new FileStoreRepository(getRepositoryDirectory(rbConfig));
	}

	// ----------------------------------------------------

	@Override
	public ContentDescriptor getData(final String id) {
		return delegate.getData(id);
	}

	@Override
	public void storeFile(final ContentDescriptor descriptor) {
		delegate.storeFile(descriptor);
	}

	@Override
	public boolean exists(final String id) {
		return delegate.exists(id);
	}

	// ----------------------------------------------------

	private String getRepositoryDirectory(final RBConfig rbConfig) {
		String directory = rbConfig.getWorkDirectory();
		if(null == directory || directory.isEmpty()){
			directory = System.getProperty("java.io.tmpdir")
					+ System.getProperty("file.separator")
					+ UUID.randomUUID().toString();
		}
		directory = directory + System.getProperty("file.separator") + "fs-content-repository";
		LOGGER.info("Using repository location: {}", directory);
		return directory;
	}

}
