package de.lichtflut.rb.webck.components.content;

import de.lichtflut.rb.core.content.ContentItem;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * <p>
 *  Panel listing a group of postings, where a 'group' could describe all postings belonging to a blog or a forum.
 * </p>
 *
 * <p>
 *  Created 04.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class PostingGroupPanel extends Panel {

    public PostingGroupPanel(String id, IModel<List<ContentItem>> model) {
        super(id, model);

        ListView<ContentItem> view = new ListView<ContentItem>("group", model) {
            @Override
            protected void populateItem(ListItem<ContentItem> listItem) {
                listItem.add(new PostingPanel("posting", listItem.getModel()));
            }
        };

        add(view);

        add(new AjaxLink<Void>("addPosting") {
            @Override
            public void onClick(final AjaxRequestTarget target) {

            }
        });
    }

}
