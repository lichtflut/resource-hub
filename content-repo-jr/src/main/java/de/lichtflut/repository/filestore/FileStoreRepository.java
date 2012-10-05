package de.lichtflut.repository.filestore;

import de.lichtflut.infra.io.Streamer;
import de.lichtflut.infra.security.Crypt;
import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.Filetype;
import de.lichtflut.repository.RepositoryDelegator;
import de.lichtflut.repository.impl.ContentDescriptorBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

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

    private static final String DATA = "data.bin";

    private static final String META = "meta.properties";

    private static final String META_ID = "id";

    private static final String META_NAME = "name";

    private static final String META_MIME_TYPE = "mime-type";

    private File repositoryDir;

    // ----------------------------------------------------

    public FileStoreRepository(String repositoryDir) {
        this(new File(repositoryDir));
    }

    public FileStoreRepository(File repositoryDir) {
        this.repositoryDir = repositoryDir;
    }

    // ----------------------------------------------------

    @Override
    public ContentDescriptor getData(String id) {
        File contentDir = getContentDirectory(id);
        if (!contentDir.exists()) {
            // TODO: add exceptions to signature?
            return null;
        }
        try {
            Properties meta = getMetaData(contentDir);
            return new ContentDescriptorBuilder()
                    .name(meta.getProperty(META_NAME))
                    .mimeType(Filetype.valueOf(meta.getProperty(META_MIME_TYPE)))
                    .id(id)
                    .data(getInputStream(contentDir))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Error while loading content item: " + id, e);
        }
    }

    @Override
    public void storeFile(ContentDescriptor descriptor) {
        File contentDir = getContentDirectory(descriptor);
        if (!contentDir.exists()) {
            contentDir.mkdirs();
        }
        Properties meta = new Properties();
        meta.setProperty(META_ID, descriptor.getID());
        meta.setProperty(META_NAME, descriptor.getName());
        meta.setProperty(META_MIME_TYPE, descriptor.getMimeType().name());
        try {
            writeMetaData(contentDir, meta);
            writeData(contentDir, descriptor);
        } catch (IOException e) {
            throw new RuntimeException("Error while writing content item: " + descriptor.getID(), e);
        }
    }

    @Override
    public boolean exists(String id) {
        return getContentDirectory(id).exists();
    }

    // ----------------------------------------------------

    protected Properties getMetaData(File contentDir) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(new File(contentDir, META)));
        return props;
    }

    protected void writeMetaData(File contentDir, Properties props) throws IOException {
        String comment = "Created by RB FileRepo at " + new Date();
        props.store(new FileOutputStream(new File(contentDir, META)), comment);
    }

    protected InputStream getInputStream(File contentDir) throws FileNotFoundException {
        return new FileInputStream(new File(contentDir, DATA));
    }

    protected void writeData(File contentDir, ContentDescriptor desc) throws IOException {
        FileOutputStream out = new FileOutputStream(new File(contentDir, DATA));
        new Streamer().stream(desc.getData(), out);
    }

    protected File getContentDirectory(ContentDescriptor cd) {
        return getContentDirectory(cd.getID());
    }

    protected File getContentDirectory(String contentId) {
        String id = Crypt.md5Hex(contentId);
        return new File(getBaseDirectory(id), id);
    }

    protected File getBaseDirectory(String id) {
        return new File(repositoryDir, id.substring(0, 2));
    }

    protected Filetype type(String typeName) {
        return Filetype.valueOf(typeName);
    }

}
