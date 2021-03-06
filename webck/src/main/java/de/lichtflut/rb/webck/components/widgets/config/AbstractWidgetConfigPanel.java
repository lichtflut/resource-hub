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
package de.lichtflut.rb.webck.components.widgets.config;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.ViewSpecTraverser;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.dialogs.InformationExportDialog;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.components.widgets.ConfigurableWidget;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.model.SemanticGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  Abstract base configuration panel.
 * </p>
 *
 * <p>
 * 	Created Jan 26, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public class AbstractWidgetConfigPanel extends TypedPanel<WidgetSpec> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWidgetConfigPanel.class);
	
	@SpringBean
	protected Conversation conversation;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public AbstractWidgetConfigPanel(final String id, final IModel<WidgetSpec>  model) {
		super(id, model);
		
		final IModel<SemanticGraph> exportModel = new DerivedModel<SemanticGraph, WidgetSpec>(model) {
			@Override
			protected SemanticGraph derive(WidgetSpec spec) {
				return new ViewSpecTraverser().toGraph(spec);
			}
		};
		
		final Link exportLink = new AjaxFallbackLink("exportLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new InformationExportDialog(hoster.getDialogID(), exportModel));
			}
		};
		add(exportLink);
		
		final Form form = new Form("form");
		form.add(new FeedbackPanel("feedback"));
		
		form.add(new TextField<String>("title", new PropertyModel<String>(model, "title")));
		
		form.add(new RBDefaultButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				save(model.getObject());
				findParent(ConfigurableWidget.class).switchToDisplay();
			}
		});

		form.add(new RBCancelButton("cancel") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				cancel(model.getObject());
				findParent(ConfigurableWidget.class).switchToDisplay();
			}
		});
		
		add(form);
	}
	
	// ----------------------------------------------------
	
	protected final void save(WidgetSpec spec) {
		LOGGER.info("Storing widget {}.", spec);
		onSave(spec);
		conversation.attach(spec);
	}
	
	protected void cancel(WidgetSpec spec) {
		conversation.reset(spec);
	}
	
	protected void onSave(WidgetSpec spec) {
	}
	
	// ----------------------------------------------------

	protected Form<?> getForm() {
		return (Form<?>) get("form");
	}

}