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
package de.lichtflut.rb.webck.components.content;

import de.lichtflut.rb.core.content.ContentItem;
import de.lichtflut.rb.core.content.SNContentItem;
import de.lichtflut.rb.core.services.ContentService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isEmpty;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNotNull;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNull;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(PostingGroupPanel.class);

    private final IModel<ContentItem> editModel = new Model<ContentItem>();

    private final IModel<ResourceID> groupID;

    @SpringBean
    private ContentService contentService;

    @SpringBean
    private ServiceContext context;

    // ----------------------------------------------------

    public PostingGroupPanel(String id, IModel<List<ContentItem>> model, IModel<ResourceID> groupID) {
        super(id, model);
        this.groupID = groupID;

        LOGGER.debug("Initializing a new posting group with owner {}.", groupID);

        setOutputMarkupId(true);

        final PostingEditorPanel editor = new PostingEditorPanel("postingEditor", editModel) {
            @Override
            public void onSave(ContentItem item) {
                store(item);
                stopEditing();
            }
            @Override
            public void onCancel(ContentItem item) {
                stopEditing();
            }
        };
        editor.add(visibleIf(isNotNull(editModel)));
        add(editor);

        final ListView<ContentItem> view = new ListView<ContentItem>("group", model) {
            @Override
            protected void populateItem(ListItem<ContentItem> listItem) {
                listItem.add(new PostingPanel("posting", listItem.getModel()) {
                    @Override
                    public void onEdit(ContentItem item) {
                        startEditing(item);
                    }
                });
            }
        };
        view.add(visibleIf(isNull(editModel)));
        add(view);

        AjaxLink<Void> addLink = new AjaxLink<Void>("addPosting") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                createPosting();
            }
        };
        addLink.add(visibleIf(isNull(editModel)));
        add(addLink);

        add(new Label("noContentInfo", new ResourceModel("label.no-postings"))
                .add(visibleIf(isEmpty(model))));
    }

    // ----------------------------------------------------

    private void createPosting() {
        SNContentItem item = new SNContentItem();
        item.setCreator(getCurrentUserName());
        item.setCreateDate(new Date());
        item.setModificationDate(new Date());
        editModel.setObject(item);
        RBAjaxTarget.add(this);
    }

    private void startEditing(ContentItem item) {
        editModel.setObject(item);
        RBAjaxTarget.add(this);
    }

    private void stopEditing() {
        editModel.setObject(null);
        RBAjaxTarget.add(this);
    }

    private void store(ContentItem item) {
        LOGGER.debug("Attaching item {} to group {}.", item, groupID.getObject());
        item.setModificationDate(new Date());
        contentService.store(item);
        contentService.attachToResource(item, groupID.getObject());
    }

    private String getCurrentUserName() {
        if (context.isAuthenticated()) {
            return context.getUser().getName();
        } else {
            return null;
        }
    }

    // ----------------------------------------------------


    @Override
    protected void onDetach() {
        super.onDetach();
        this.editModel.detach();
        this.groupID.detach();
    }
}
