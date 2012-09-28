package de.lichtflut.rb.core.services;

import de.lichtflut.rb.core.content.ContentItem;

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

    ContentItem findById(String id);

    void store(ContentItem item);

}
