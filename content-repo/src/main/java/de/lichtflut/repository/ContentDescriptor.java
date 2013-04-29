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
package de.lichtflut.repository;

import java.io.InputStream;
import java.io.Serializable;

/**
 * <p>
 * This class contains some meta-information about an content-Object.
 * The provided information will be used for storing data.
 * </p>
 * Created: Jul 20, 2012
 *
 * @author Ravi Knox
 */
public interface ContentDescriptor extends Serializable {

	/**
	 * @return the id under which an object shall be referenced.
	 */
	public String getID();

	/**
	 * @return the name for an Object.
	 */
	public String getName();

	/**
	 * @return the Mimetype foran Object.
	 */
	public Filetype getMimeType();

	/**
	 * Returns the contained data as an {@link InputStream}
	 */
	public InputStream getData();

}
