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

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.content.ContentItem;
import de.lichtflut.rb.core.services.ContentService;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.content.PostingGroupPanel;
import de.lichtflut.rb.webck.components.widgets.PredefinedWidget;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

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

	public BlogWidget(final String id, final WidgetSpec spec, final ConditionalModel<Boolean> perspectiveInConfigMode) {
		super(id, spec, perspectiveInConfigMode);

		final IModel<List<ContentItem>> postingsModel = getPostingsModel(spec);

		add(new PostingGroupPanel("postingGroup", postingsModel, new Model<ResourceID>(spec.getID())));
	}

	private IModel<List<ContentItem>> getPostingsModel(final WidgetSpec widget) {
		return new DerivedDetachableModel<List<ContentItem>, WidgetSpec>(widget) {
			@Override
			protected List<ContentItem> derive(final WidgetSpec widget) {
				return contentService.getAttachedItems(widget.getID());
			}
		};
	}

}
