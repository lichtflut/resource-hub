package de.lichtflut.rb.webck.components.widgets.builtin;

import de.lichtflut.rb.core.content.ContentItem;
import de.lichtflut.rb.core.services.ContentService;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.content.PostingGroupPanel;
import de.lichtflut.rb.webck.components.widgets.PredefinedWidget;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isEmpty;

/**
 * <p>
 *  Widget representing a blog.
 * </p>
 *
 * <p>
 *  Created 04.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class BlogWidget extends PredefinedWidget {

    @SpringBean
    private ContentService contentService;

    // ----------------------------------------------------

    public BlogWidget(String id, WidgetSpec spec, ConditionalModel<Boolean> perspectiveInConfigMode) {
        super(id, spec, perspectiveInConfigMode);

        final IModel<List<ContentItem>> postingsModel = getPostingsModel(spec);

        add(new PostingGroupPanel("postingGroup", postingsModel));

        add(new Label("noContentInfo", new ResourceModel("label.no-postings"))
                .add(visibleIf(isEmpty(postingsModel))));

    }

    private IModel<List<ContentItem>> getPostingsModel(WidgetSpec widget) {
        return new DerivedDetachableModel<List<ContentItem>, WidgetSpec>(widget) {
            @Override
            protected List<ContentItem> derive(WidgetSpec widget) {
                return contentService.getAttachedItems(widget.getID());
            }
        };
    }

}
