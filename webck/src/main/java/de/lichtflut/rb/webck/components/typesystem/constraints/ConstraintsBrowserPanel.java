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
package de.lichtflut.rb.webck.components.typesystem.constraints;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.LoadableModel;

/**
 * <p>
 *  Panel for Browsing of {@link Constraint}s.
 * </p>
 *
 * <p>
 *	This panel lists all Constraints privided by a {@link LoadableModel}.
 * </p>
 * 
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class ConstraintsBrowserPanel extends Panel {

	private final LoadableModel<List<Constraint>> model;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public ConstraintsBrowserPanel(final String id, final LoadableModel<List<Constraint>> model) {
		super(id);
		this.model = model;

		setOutputMarkupId(true);

		add(new ListView<Constraint>("listview", this.model) {
			@Override
			protected void populateItem(final ListItem<Constraint> item) {
				final Constraint constraint = item.getModelObject();
				final Link link = new AjaxFallbackLink("link") {
					@Override
					public void onClick(final AjaxRequestTarget target) {
						onConstraintSelected(constraint, target);
					}
				};
				item.add(link);
				link.add(new Label("constraint", constraint.getName()));
				link.add(new AttributeAppender("title", constraint.getQualifiedName()));
			}
		});

		add(new AjaxFallbackLink("createConstraint") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				onCreateConstraint(target);
			}
		});
	}

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEvent(final IEvent<?> event) {
		final ModelChangeEvent<?> mce = ModelChangeEvent.from(event);
		if (mce.isAbout(ModelChangeEvent.PUBLIC_CONSTRAINT)) {
			model.reset();
			RBAjaxTarget.add(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onDetach() {
		super.onDetach();
		model.detach();
	}

	// -- CALLBACKS ---------------------------------------

	public abstract void onCreateConstraint(AjaxRequestTarget target);

	public abstract void onConstraintSelected(Constraint constraint, AjaxRequestTarget target);

}
