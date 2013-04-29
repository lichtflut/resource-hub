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
package de.lichtflut.rb.core.services.impl;

import java.util.UUID;

import de.lichtflut.rb.core.content.ContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.config.FileServiceConfiguration;
import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.rb.core.content.ContentDescriptor;
import de.lichtflut.rb.core.content.filestore.FileStoreRepository;

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

	private final ContentRepository delegate;

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
