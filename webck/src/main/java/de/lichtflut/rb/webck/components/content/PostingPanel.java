package de.lichtflut.rb.webck.components.content;

import de.lichtflut.rb.core.content.ContentItem;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import java.text.DateFormat;

/**
 * <p>
 *  This panel display a single posting.
 * </p>
 * <p>
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
public class PostingPanel extends TypedPanel<ContentItem> {

    public PostingPanel(final String id, final IModel<ContentItem> model) {
        super(id, model);

        setOutputMarkupId(true);

        add(new Label("title", getTitleModel()));
        add(new Label("author", getAuthorModel()));
        add(new Label("created", getCreationModel()));
        add(new Label("content", getContentDisplayModel(model)).setEscapeModelStrings(false));

        add(new AjaxLink<Void>("edit") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                onEdit(model.getObject());
            }
        });
    }

    // -- CALLBACKS----------------------------------------

    public void onEdit(ContentItem item) {
    }

    // ----------------------------------------------------

    private ContentItem getContentItem() {
        return getModelObject();
    }


    protected IModel<String> getAuthorModel() {
        return new DerivedDetachableModel<String, ContentItem>(getContentItem()) {
            @Override
            protected String derive(ContentItem item) {
                return item.getCreatorName();
            }
        };
    }

    protected IModel<String> getCreationModel() {
        return new DerivedDetachableModel<String, ContentItem>(getContentItem()) {
            @Override
            protected String derive(ContentItem item) {
                return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, getLocale()).format(
                        item.getCreateDate());
            }
        };
    }

    protected IModel<String> getTitleModel() {
        return new DerivedDetachableModel<String, ContentItem>(getContentItem()) {
            @Override
            protected String derive(ContentItem item) {
                return item.getTitle();
            }
        };
    }

    private IModel<String> getContentDisplayModel(IModel<ContentItem> model) {
        return new DerivedDetachableModel<String, ContentItem>(model) {
            @Override
            protected String derive(ContentItem item) {
                return item.getContentAsString();
            }
        };
    }

}
