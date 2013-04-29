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
package de.lichtflut.rb.webck.components.widgets.builtin;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

import de.lichtflut.rb.core.content.ContentItem;
import de.lichtflut.rb.core.content.SNContentItem;
import de.lichtflut.rb.core.services.ContentService;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.rteditor.RichTextEditor;
import de.lichtflut.rb.webck.components.widgets.PredefinedWidget;
import de.lichtflut.rb.webck.models.ConditionalModel;

/**
 * <p>
 *  This a build-in widget for display of content.
 * </p>
 *
 * <p>
 *  Created 07.09.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class ContentDisplayWidget extends PredefinedWidget {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContentDisplayWidget.class);

	private final IModel<DisplayMode> mode = new Model<DisplayMode>(DisplayMode.VIEW);

	@SpringBean
	private ContentService contentService;

    @SpringBean
    private ViewSpecificationService viewSpecService;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 *
	 * @param id The component ID.
	 * @param spec The specification.
	 * @param perspectiveInConfigMode Conditional model for checking if this widget is in config mode.
	 */
	public ContentDisplayWidget(final String id, final WidgetSpec spec, final ConditionalModel<Boolean> perspectiveInConfigMode) {
		super(id, spec, perspectiveInConfigMode);

		setOutputMarkupId(true);

		final IModel<ContentItem> contentModel = Model.of(getContentItem());
        final IModel<String> contentDisplayModel = getContentDisplayModel(contentModel);

		final WebMarkupContainer display = new WebMarkupContainer("display");
		display.add(new Label("content", contentDisplayModel).setEscapeModelStrings(false));
		display.add(new AjaxLink<Void>("edit") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				mode.setObject(DisplayMode.EDIT);
				target.add(ContentDisplayWidget.this);
			}
		});
		display.add(visibleIf(areEqual(mode, DisplayMode.VIEW)));
		add(display);

		final Form<?> form = new Form<Void>("form");
		form.add(new FeedbackPanel("feedback"));

		final RichTextEditor editor = new RichTextEditor("editor", contentModel) {
			@Override
			public void onSave() {
				storeContent(spec, contentModel.getObject());
				mode.setObject(DisplayMode.VIEW);
				RBAjaxTarget.add(ContentDisplayWidget.this);
			}
			@Override
			public void onCancel() {
				mode.setObject(DisplayMode.VIEW);
				RBAjaxTarget.add(ContentDisplayWidget.this);
			}
		};
		editor.add(visibleIf(areEqual(mode, DisplayMode.EDIT)));
		form.add(editor);

		add(form);

	}

	// ----------------------------------------------------

    @Override
    protected Component createTitle(String componentID) {
        return super.createTitle(componentID).add(visibleIf(areEqual(mode, DisplayMode.VIEW)));
    }

    @Override
    protected IModel<String> getTitleModel() {
        return new DerivedDetachableModel<String, ContentItem>(getContentItem()) {
            @Override
            protected String derive(ContentItem item) {
                return item.getTitle();
            }
        };
    }

    // ----------------------------------------------------

    private void storeContent(final WidgetSpec spec, final ContentItem item) {
        contentService.store(item);
        if (item.getID().equals(spec.getContentID())) {
            LOGGER.debug("Updating content of widget {} : {}", spec, item);
        } else {
            LOGGER.debug("Creating content of widget {} : {}", spec, item);
            spec.setContentID(item.getID());
            viewSpecService.store(spec);
        }
    }

    private ContentItem getContentItem() {
        ContentItem existing = contentService.findById(getWidgetSpec().getContentID());
        if (existing != null) {
            return existing;
        } else {
            return new SNContentItem();
        }
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
