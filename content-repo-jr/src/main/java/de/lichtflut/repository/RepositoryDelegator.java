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
	 * This method returns an {@link ContentDescriptor} for a given id.
	 * @param id - where the file is located
	 * @return an ContentDescriptor
	 */
	ContentDescriptor getData(String id);

	/**
	 * Stores a {@link InputStream} under a given id.
	 * If id already exists, data will be overwritten.
	 * @param descriptor
	 */
	void storeFile(ContentDescriptor descriptor);

	/**
	 * @param id - where to look for a file
	 * @return true if file exists, false if not.
	 */
	boolean exists(String id);
}
