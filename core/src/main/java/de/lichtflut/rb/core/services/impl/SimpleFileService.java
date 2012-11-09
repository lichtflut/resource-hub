package de.lichtflut.rb.core.services.impl;

import de.lichtflut.rb.core.config.FileServiceConfiguration;
import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.RepositoryDelegator;
import de.lichtflut.repository.filestore.FileStoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

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

    private RBConfig rbConfig;

    private FileServiceConfiguration fsConfiguration;

    private RepositoryDelegator delegate;

    // ----------------------------------------------------

    public SimpleFileService(RBConfig rbConfig, FileServiceConfiguration fsConfiguration) {
        this.rbConfig = rbConfig;
        this.fsConfiguration = fsConfiguration;
        this.delegate = new FileStoreRepository(getRepositoryDirectory(rbConfig));
    }

    // ----------------------------------------------------

    @Override
    public ContentDescriptor getData(String id) {
        return delegate.getData(id);
    }

    @Override
    public void storeFile(ContentDescriptor descriptor) {
        delegate.storeFile(descriptor);
    }

    @Override
    public boolean exists(String id) {
        return delegate.exists(id);
    }

    // ----------------------------------------------------

    private String getRepositoryDirectory(RBConfig rbConfig) {
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
