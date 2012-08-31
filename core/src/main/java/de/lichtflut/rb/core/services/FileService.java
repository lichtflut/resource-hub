/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import java.io.File;
import java.io.InputStream;

import de.lichtflut.repository.ContentDescriptor;

/**
 * <p>
 * This Service provides access to
 * </p>
 * Created: Aug 3, 2012
 * 
 * @author Ravi Knox
 */
public interface FileService {

	/**
	 * This method returns an {@link File} for a given path.
	 * 
	 * @param path - where the file is located
	 * @return an File
	 */
	InputStream getData(String path);

	/**
	 * Stores a {@link File} under a given path. If file already exists, it will be overwritten.
	 * 
	 * @param descriptor
	 */
	void storeFile(ContentDescriptor descriptor);

	/**
	 * @param path - where to look for a file
	 * @return true if file exists, false if not.
	 */
	boolean exists(String path);

}
