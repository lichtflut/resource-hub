package de.lichtflut.repository.filestore;

import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.RepositoryDelegator;
import de.lichtflut.repository.impl.ContentDescriptorBuilder;
import sun.security.provider.MD5;

import java.io.File;
import java.security.MessageDigest;

/**
 * <p>
 *  Simple implementation of a content repository. Stores the files in local filesystem.
 * </p>
 *
 * <p>
 *  Created 04.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class FileStoreRepository implements RepositoryDelegator {

    @Override
    public ContentDescriptor getData(String id) {
        return new ContentDescriptorBuilder().build();
    }

    @Override
    public void storeFile(ContentDescriptor descriptor) {

    }

    @Override
    public boolean exists(String id) {
        return false;
    }

    // ----------------------------------------------------

    protected File toFile(ContentDescriptor cd) {
        return null;
    }


}
