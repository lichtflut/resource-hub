/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.editor;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.defaultButtonIf;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.models.BrowsingContextModel;

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
public abstract class BrowsingButtonBar extends Panel {

	/**
	 * @param id
	 */
	public BrowsingButtonBar(final String id, final IModel<RBEntity> model, final Form form) {
		super(id);
		
		add(createSaveButton(model, form));
		add(createCancelButton(model, form));
		
		add(visibleIf(BrowsingContextModel.isInSubReferencingMode()));
	}
	
	// ----------------------------------------------------
	
	public abstract void onSave();
	
	public abstract void onCancel();
	
	// -- BUTTONS -----------------------------------------
	
	protected AjaxFallbackButton createSaveButton(final IModel<RBEntity> model, final Form form) {
		final AjaxFallbackButton save = new AjaxFallbackButton("save", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onSave();
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		};
		save.add(defaultButtonIf(BrowsingContextModel.isInSubReferencingMode()));
		return save;
	}
	
	protected AjaxFallbackButton createCancelButton(final IModel<RBEntity> model, final Form form) {
		final AjaxFallbackButton cancel = new AjaxFallbackButton("cancel", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onCancel();
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		};
		cancel.setDefaultFormProcessing(false);
		return cancel;
	}
	
}