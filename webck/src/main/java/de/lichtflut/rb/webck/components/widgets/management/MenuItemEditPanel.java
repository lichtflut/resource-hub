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
package de.lichtflut.rb.webck.components.widgets.management;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

import de.lichtflut.rb.core.services.MenuService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.MenuItem;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.viewspecs.PerspectiveListModel;

/**
 * <p>
 *  Panel for editing of a menu item.
 * </p>
 *
 * <p>
 * 	Created Feb 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public class MenuItemEditPanel extends TypedPanel<MenuItem> {
	
    @SpringBean
	private MenuService menuService;

	private final IModel<DisplayMode> mode;

	// ----------------------------------------------------

	/**
     * Constructor.
	 * @param id The component ID.
	 */
	public MenuItemEditPanel(final String id, final IModel<MenuItem> model, final IModel<DisplayMode> mode) {
		super(id, model);
		this.mode = mode;
		
		final Form form = new Form("form");
		form.setOutputMarkupId(true);
		form.add(new FeedbackPanel("feedback"));
		
		form.add(new TextField<String>("name", new PropertyModel<String>(model, "name")));
		
		form.add(new DropDownChoice<Perspective>("perspective", 
				new PropertyModel<Perspective>(model, "perspective"),
				new PerspectiveListModel(), 
				new PerspectiveRenderer()));
		
		form.add(createSaveButton(model));
		form.add(createCancelButton(model));
		form.add(createCreateButton(model));
		
		add(form);
	}
	
	// ----------------------------------------------------
	
	protected void onCancel(AjaxRequestTarget target) { }
	
	protected void onSuccess(AjaxRequestTarget target) { }

	// -- BUTTONS -----------------------------------------
	
	protected Button createSaveButton(final IModel<MenuItem> model) {
		final AjaxButton save = new RBDefaultButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
                menuService.store(model.getObject());
				send(getPage(), Broadcast.BREADTH, new ModelChangeEvent(ModelChangeEvent.VIEW_SPEC, ModelChangeEvent.MENU));
				RBAjaxTarget.add(form);
				onSuccess(target);
			}
		};
		save.add(visibleIf(areEqual(mode, DisplayMode.EDIT)));
		return save;
	}
	
	protected Button createCreateButton(final IModel<MenuItem> model) {
		final Button edit = new RBDefaultButton("create") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				menuService.addUsersMenuItem(model.getObject());
				send(getPage(), Broadcast.BREADTH, new ModelChangeEvent(ModelChangeEvent.VIEW_SPEC));
				RBAjaxTarget.add(form);
				onSuccess(target);
			}
		};
		edit.add(visibleIf(areEqual(mode, DisplayMode.CREATE)));
		return edit;
	}
	
	protected Button createCancelButton(final IModel<MenuItem> model) {
		final AjaxButton cancel = new RBCancelButton("cancel") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				onCancel(target);
				RBAjaxTarget.add(form);
			}
		};
		return cancel;
	}
	
	// ----------------------------------------------------
	
	private class PerspectiveRenderer implements IChoiceRenderer<Perspective> {
		/** 
		 * {@inheritDoc}
		 */
		@Override
		public Object getDisplayValue(Perspective perspective) {
			return perspective.getName();
		}

		/** 
		 * {@inheritDoc}
		 */
		@Override
		public String getIdValue(Perspective perspective, int index) {
			return perspective.toURI();
		}
		
	}
	
}
