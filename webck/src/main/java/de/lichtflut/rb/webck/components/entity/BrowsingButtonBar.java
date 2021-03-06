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
package de.lichtflut.rb.webck.components.entity;

import de.lichtflut.rb.core.eh.ValidationException;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.webck.browsing.ReferenceReceiveAction;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.Conversation;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.BrowsingContextModel.isInCreateReferenceMode;
import static de.lichtflut.rb.webck.models.ConditionalModel.and;
import static de.lichtflut.rb.webck.models.ConditionalModel.hasSchema;

/**
 * <p>
 *  Button bar to be used in 'create reference' mode.
 * </p>
 *
 * <p>
 * 	Created Dec 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class BrowsingButtonBar extends TypedPanel<RBEntity> {

	@SpringBean
	private EntityManager entityManager;

	@SpringBean
	private Conversation conversation;

	// ----------------------------------------------------

	/**
	 * @param id The compoenent ID.
	 */
	public BrowsingButtonBar(final String id, final IModel<RBEntity> model) {
		super(id, model);

		add(createSaveButton(model));
		add(createCancelButton(model));

		add(visibleIf(and(hasSchema(model), isInCreateReferenceMode())));
	}

	// ----------------------------------------------------

	public void onSaveAndBack() throws ValidationException {
		final RBEntity createdEntity = getModelObject();
		entityManager.store(createdEntity);
		for(ReferenceReceiveAction<?> action : RBWebSession.get().getHistory().getCurrentStep().getActions()) {
			action.execute(conversation, createdEntity);
		}
		RBWebSession.get().getHistory().back();
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
	}

	public void onCancelAndBack() {
		entityManager.delete(getModelObject().getID());
		RBWebSession.get().getHistory().back();
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
	}

	// -- BUTTONS -----------------------------------------

	protected AjaxButton createSaveButton(final IModel<RBEntity> model) {
		final AjaxButton save = new RBDefaultButton("save") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form)  {
				try {
					onSaveAndBack();
				} catch (ValidationException e) {
					setDefaultFormProcessing(false);
					error(getString("error.validation"));
					RBAjaxTarget.add(getPage());
				}

			}
		};
		return save;
	}

	protected AjaxButton createCancelButton(final IModel<RBEntity> model) {
		final AjaxButton cancel = new RBCancelButton("cancel") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				onCancelAndBack();
			}
		};
		return cancel;
	}

}
