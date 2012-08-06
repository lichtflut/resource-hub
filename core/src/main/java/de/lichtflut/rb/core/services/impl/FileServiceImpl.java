/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.UUID;

import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.RepositoryDelegator;
import de.lichtflut.repository.impl.ContentDescriptorBuilder;
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

	protected RepositoryDelegator delegator;

	private final Properties properties;

	// ---------------- Constructor -------------------------

	public FileServiceImpl(final String config){
		properties = getPropertiesFile(config);
		initRepository();
	}

	// ------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File getData(final String path) {
		return getFileForStream(delegator.getData(path));
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void storeFile(final String path, final File file) {
		String mime = file.getPath().substring(file.getPath().lastIndexOf('.'));
		String separator = System.getProperty("file.separator");
		String name = file.getPath();
		if(file.getPath().contains(separator)){
			name = name.substring(file.getPath().lastIndexOf(System.getProperty("file.separator")));
		}
		name.substring(0, name.lastIndexOf('.'));
		ContentDescriptor descriptor = new ContentDescriptorBuilder().data(getInputStreamFromFile(file)).mimeType(mime).name(name).build();
		delegator.storeFile(descriptor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists(final String path) {
		return delegator.exists(path);
	}

	// ------------------------------------------------------

	protected void initRepository() {
		delegator = new RepositoryDelegatorImpl(getProperty("username", "anonymous"), getProperty("password", "anonymous")) {

			@Override
			protected RepositoryConfigWrapper getConfig() {
				String home = getProperty("storage-location", "target");
				if(home == null || home.isEmpty()){
					home = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + UUID.randomUUID().toString();
				}
				File config = new File(getProperty("config-file", ""));
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

	private File getFileForStream(final ContentDescriptor descriptor) {
		InputStream inputStream = descriptor.getData();
		File retrieved = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + UUID.randomUUID().toString() + "." + descriptor.getMimeType());
		// write the inputStream to a FileOutputStream
		OutputStream out;
		try {
			out = new FileOutputStream(retrieved);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			inputStream.close();
			out.flush();
			out.close();
			return retrieved;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private FileInputStream getInputStreamFromFile(final File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Properties getPropertiesFile(final String config) {
		final Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(config));
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

}
