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
package de.lichtflut.rb.core.content;

import java.io.InputStream;

/**
 * <p>
 * This interface defines how to communicate with a Repository.
 * </p>
 * Created: Jul 12, 2012
 *
 * @author Ravi Knox
 */
public interface ContentRepository {

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
