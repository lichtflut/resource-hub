/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.editor;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.defaultButtonIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.application.RBWebSession;
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
	
	protected AjaxFallbackButton createSaveButton(final IModel<RBEntity> model, final Form form) {
		final AjaxFallbackButton save = new AjaxFallbackButton("save", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				getEntityManager().store(model.getObject());
				RBWebSession.get().getHistory().finishEditing();
				send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		};
		save.add(visibleIf(not(viewMode)));
		save.add(defaultButtonIf(not(viewMode)));
		return save;
	}
	
	protected AjaxFallbackButton createCancelButton(final IModel<RBEntity> model, final Form form) {
		final AjaxFallbackButton cancel = new AjaxFallbackButton("cancel", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				RBWebSession.get().getHistory().back();
				send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		};
		cancel.add(visibleIf(not(viewMode)));
		return cancel;
	}
	
	protected AjaxFallbackButton createEditButton(final IModel<RBEntity> model, final Form form) {
		final AjaxFallbackButton edit = new AjaxFallbackButton("edit", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				RBWebSession.get().getHistory().beginEditing();
				target.add(form);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		};
		edit.add(visibleIf(not(not(viewMode))));
		return edit;
	}
	
}
