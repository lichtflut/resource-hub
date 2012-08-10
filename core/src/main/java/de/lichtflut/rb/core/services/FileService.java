/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import java.io.File;


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
	 * @param path - where the file is located
	 * @return an File
	 */
	File getData(String path);

	/**
	 * Stores a {@link File} under a given path.
	 * If file already exists, it will be overwritten.
	 * @param path - like <code>'instance/domain/entity/property/filename'</code>
	 * @param file
	 */
	void storeFile(String path, File file);

	/**
	 * @param path - where to look for a file
	 * @return true if file exists, false if not.
	 */
	boolean exists(String path);

}
