/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.defaultButtonIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.and;
import static de.lichtflut.rb.webck.models.ConditionalModel.hasSchema;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.BrowsingContextModel;
import de.lichtflut.rb.webck.models.ConditionalModel;

/**
 * <p>
 *  The standard button bar to be used when not in "create reference" mode.
 * </p>
 *
 * <p>
 * 	Created Dec 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public class LocalButtonBar extends Panel {

	private final ConditionalModel viewMode = BrowsingContextModel.isInViewMode();

	@SpringBean
	private EntityManager entityManager;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model of the current entity.
	 */
	public LocalButtonBar(final String id, final IModel<RBEntity> model) {
		super(id);

		add(createSaveButton(model));
		add(createCancelButton(model));
		add(createEditButton(model));

		add(visibleIf(and(hasSchema(model), not(BrowsingContextModel.isInCreateReferenceMode()))));

	}

	// -- BUTTONS -----------------------------------------

	protected Component createSaveButton(final IModel<RBEntity> model) {
		final RBStandardButton save = new RBStandardButton("save") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				throw new NotYetImplementedException("Handle ValidationException first");
				// TODO IMPLEMENT VALIDATION EXCEPTION
				//				entityManager.store(model.getObject());
				//				RBWebSession.get().getHistory().finishEditing();
				//				send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
			}
		};
		save.add(visibleIf(not(viewMode)));
		save.add(defaultButtonIf(not(viewMode)));
		return save;
	}

	protected Component createCancelButton(final IModel<RBEntity> model) {
		final RBCancelButton cancel = new RBCancelButton("cancel") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				RBWebSession.get().getHistory().back();
				send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
			}
		};
		cancel.add(visibleIf(not(viewMode)));
		return cancel;
	}

	protected Component createEditButton(final IModel<RBEntity> model) {
		final RBStandardButton edit = new RBStandardButton("edit") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				RBWebSession.get().getHistory().edit(EntityHandle.forID(model.getObject().getID()));
				send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
			}
		};
		edit.add(visibleIf(not(not(viewMode))));
		return edit;
	}

}
