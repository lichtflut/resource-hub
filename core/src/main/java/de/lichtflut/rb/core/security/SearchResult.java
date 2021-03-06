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
package de.lichtflut.rb.core.security;

import java.util.List;

/**
 * <p>
 *  Search result for auth objects (users, domains, etc.).
 * </p>
 *
 * <p>
 * 	Created Jun 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface SearchResult<T> extends Iterable<T> {
	
	/**
	 * Close the query result.
	 * Should always be called to free resources.
	 */
	void close();
	
	/**
	 * Returns the size of the query result entries or -1 if the size is unknown.
	 * @return The size.
	 */
	int size();
	
	/**
	 * Converts the query result to a list.
	 * <p>Warning: This might be very expensive and memory consuming. Only use this method, if you really know
	 * 	the list result is not too large!.
	 * </p>
	 * @return The list (or maybe an OutOfMemoryException)
	 */
	List<T> toList();
	
	/**
	 * Converts the query result to a list.
	 * @param The maximum amount of items to retrieve from index hits.
	 * @return The list.
	 */
	List<T> toList(int max);
	
	/**
	 * Converts the query result to a list.
	 * @param The maximum amount of items to retrieve from index hits.
	 * @return The list.
	 */
	List<T> toList(int offset, int max);

	/**
	 * Check if the result is empty.
	 * @return true if the result is empty.
	 */
	boolean isEmpty();
	
	/**
	 * Get the only result or null. If there is more than one result an {@link IllegalStateException} is thrown.
	 * @return The single item or null.
	 */
	T getSingleItem();

}
