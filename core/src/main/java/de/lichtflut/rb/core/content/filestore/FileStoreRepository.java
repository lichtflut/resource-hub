/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.content.filestore;

import de.lichtflut.rb.core.content.ContentDescriptor;
import de.lichtflut.rb.core.content.ContentDescriptorBuilder;
import de.lichtflut.rb.core.content.ContentRepository;
import de.lichtflut.rb.core.content.Filetype;
import de.lichtflut.rb.core.security.RBCrypt;

import java.io.*;
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
public class FileStoreRepository implements ContentRepository {

	private static final String DATA = "data.bin";

	private static final String META = "meta.properties";

	private static final String META_ID = "id";

	private static final String META_NAME = "name";

	private static final String META_MIME_TYPE = "mime-type";

	private final File repositoryDir;

	// ----------------------------------------------------

	public FileStoreRepository(final String repositoryDir) {
		this(new File(repositoryDir));
	}

	public FileStoreRepository(final File repositoryDir) {
		this.repositoryDir = repositoryDir;
	}

	// ----------------------------------------------------

	@Override
	public ContentDescriptor getData(final String id) {
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
	public void storeFile(final ContentDescriptor descriptor) {
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
	public boolean exists(final String id) {
		if (null == id){
			return false;
		}
		return getContentDirectory(id).exists();
	}

	// ----------------------------------------------------

	protected Properties getMetaData(final File contentDir) throws IOException {
		Properties props = new Properties();
		props.load(new FileInputStream(new File(contentDir, META)));
		return props;
	}

	protected void writeMetaData(final File contentDir, final Properties props) throws IOException {
		String comment = "Created by RB FileRepo at " + new Date();
		props.store(new FileOutputStream(new File(contentDir, META)), comment);
	}

	protected InputStream getInputStream(final File contentDir) throws FileNotFoundException {
		return new FileInputStream(new File(contentDir, DATA));
	}

	protected void writeData(final File contentDir, final ContentDescriptor desc) throws IOException {
		FileOutputStream out = new FileOutputStream(new File(contentDir, DATA));
        byte buffer[] = new byte[2000];
        InputStream in = desc.getData();
        int read = in.read(buffer);
        while (read > -1){
            out.write(buffer, 0, read);
            read = in.read(buffer);
        }
	}

	protected File getContentDirectory(final ContentDescriptor cd) {
		return getContentDirectory(cd.getID());
	}

	protected File getContentDirectory(final String contentId) {
		String id = RBCrypt.md5Hex(contentId);
		return new File(getBaseDirectory(id), id);
	}

	protected File getBaseDirectory(final String id) {
		return new File(repositoryDir, id.substring(0, 2));
	}

	protected Filetype type(final String typeName) {
		return Filetype.valueOf(typeName);
	}

}
