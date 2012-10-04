package de.lichtflut.rb.webck.components.content;

import de.lichtflut.rb.core.content.ContentItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  This panel display a posting.
 *  A posting can be one of
 *  <ul>
 *      <li>News</li>
 *      <li>Blog post</li>
 *      <li>Forum entry</li>
 *      <li>Article</li>
 *  </ul>
 *
 * </p>
 *
 * <p>
 *  Created 04.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class PostingPanel extends Panel {

    public PostingPanel(String id, IModel<ContentItem> model) {
        super(id, model);
    }

}
