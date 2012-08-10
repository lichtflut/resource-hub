/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.repository;

import java.io.InputStream;



/**
 * <p>
 * This interface defines how to communicate with a Repository.
 * </p>
 * Created: Jul 12, 2012
 *
 * @author Ravi Knox
 */
public interface RepositoryDelegator {

	/**
	 * This method returns an {@link ContentDescriptor} for a given path.
	 * @param path - where the file is located
	 * @return an ContentDescriptor
	 */
	ContentDescriptor getData(String path);

	/**
	 * Stores a {@link InputStream} under a given path.
	 * If path already exists, data will be overwritten.
	 * @param descriptor
	 */
	void storeFile(ContentDescriptor descriptor);

	/**
	 * @param path - where to look for a file
	 * @return true if file exists, false if not.
	 */
	boolean exists(String path);
}
