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
package de.lichtflut.rb.core.services;

import de.lichtflut.rb.core.content.ContentItem;
import org.arastreju.sge.model.ResourceID;

import java.util.List;

/**
 * <p>
 *  Service for management of content items.
 *  <br />
 *  A content item is semantic node with meta information and some sort of rich text content.
 * </p>
 *
 * <p>
 *  Created 21.09.12
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ContentService {

    /**
     * Find a content item by it's ID.
     * @param id The unique content ID.
     * @return The content item or null if not found.
     */
    ContentItem findById(String id);

    /**
     * Store a content item.
     * @param item The item to store.
     */
    void store(ContentItem item);

    /**
     * Remove the content item with given ID.
     * @param id The ID of the item to be removed.
     */
    void remove(String id);

    // -- NOTES -------------------------------------------

    /**
     * Get all content items attached to owning resource.
     * @param owner The resource, where the items have been attached.
     * @return The content items.
     */
    List<ContentItem> getAttachedItems(ResourceID owner);

    /**
     * Attach the content item to the given target.
     * @param contentItem The content item.
     * @param target The target.
     */
    void attachToResource(ContentItem contentItem, ResourceID target);
}
