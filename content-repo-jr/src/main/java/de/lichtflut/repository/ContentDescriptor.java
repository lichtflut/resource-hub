/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.repository;

import java.io.InputStream;

/**
 * <p>
 * This class contains some meta-information about an {@link Object}.
 * The provided information will be used for storing data.
 * </p>
 * Created: Jul 20, 2012
 *
 * @author Ravi Knox
 */
public interface ContentDescriptor {

	/**
	 * @return the path under which an object shall be referenced.
	 */
	public String getPath();

	/**
	 * @return the name for an Object.
	 */
	public String getName();

	/**
	 * @return the Mimetype foran Object.
	 */
	public String getMimeType();

	/**
	 * Returns the contained data as an {@link InputStream}
	 */
	public InputStream getData();

}
