/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.editor;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.defaultButtonIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.BrowsingContextModel;
import de.lichtflut.rb.webck.models.ConditionalModel;

/**
 * <p>
 *  Simple Button Bar.
 * </p>
 *
 * <p>
 * 	Created Dec 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public abstract class LocalButtonBar extends Panel {

	private final ConditionalModel viewMode = BrowsingContextModel.isInViewMode();
	
	// ----------------------------------------------------
	
	/**
	 * @param id
	 */
	public LocalButtonBar(final String id, final IModel<RBEntity> model, final Form form) {
		super(id);
		
		add(createSaveButton(model, form));
		add(createCancelButton(model, form));
		add(createEditButton(model, form));
		
		add(visibleIf(not(BrowsingContextModel.isInSubReferencingMode())));
		
	}
	
	// ----------------------------------------------------
	
	public abstract EntityManager getEntityManager();
	
	// -- BUTTONS -----------------------------------------
	
	protected Component createSaveButton(final IModel<RBEntity> model, final Form form) {
		final RBStandardButton save = new RBStandardButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				getEntityManager().store(model.getObject());
				RBWebSession.get().getHistory().finishEditing();
				send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
			}
		};
		save.add(visibleIf(not(viewMode)));
		save.add(defaultButtonIf(not(viewMode)));
		return save;
	}
	
	protected Component createCancelButton(final IModel<RBEntity> model, final Form form) {
		final RBCancelButton cancel = new RBCancelButton("cancel") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				RBWebSession.get().getHistory().back();
				send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
			}
		};
		cancel.add(visibleIf(not(viewMode)));
		return cancel;
	}
	
	protected Component createEditButton(final IModel<RBEntity> model, final Form form) {
		final RBStandardButton edit = new RBStandardButton("edit") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				RBWebSession.get().getHistory().beginEditing();
				target.add(form);
			}
		};
		edit.add(visibleIf(not(not(viewMode))));
		return edit;
	}
	
}
