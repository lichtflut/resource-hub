/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.editor;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.defaultButtonIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.BrowsingContextModel.isInCreateReferenceMode;
import static de.lichtflut.rb.webck.models.ConditionalModel.hasSchema;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.browsing.ReferenceReceiveAction;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;

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
@SuppressWarnings("rawtypes")
public abstract class BrowsingButtonBar extends TypedPanel<RBEntity> {

	private final IModel<ResourceID> typeModel;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	public BrowsingButtonBar(final String id, final IModel<RBEntity> model, final IModel<ResourceID> typeModel) {
		super(id, model);
		this.typeModel = typeModel;
		
		add(createSaveButton(model));
		add(createCancelButton(model));
		add(createClassifyButton(model));
		
		add(visibleIf(isInCreateReferenceMode()));
	}
	
	// ----------------------------------------------------
	
	public void onSaveAndBack() {
		final RBEntity createdEntity = getModelObject();
		getEntityManager().store(createdEntity);
		for(ReferenceReceiveAction action : RBWebSession.get().getHistory().getCurrentStep().getActions()) {
			action.execute(getServiceProvider(), createdEntity);
		}
		RBWebSession.get().getHistory().back();
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
	}
	
	public void onClassify() {
		getEntityManager().changeType(getModelObject(), typeModel.getObject());
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
	}
	
	public void onCancelAndBack() {
		RBWebSession.get().getHistory().back();
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
	}
	
	// -- BUTTONS -----------------------------------------
	
	protected AjaxButton createSaveButton(final IModel<RBEntity> model) {
		final AjaxButton save = new RBStandardButton("save") {
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				onSaveAndBack();
			}
		};
		save.add(visibleIf(hasSchema(model)));
		save.add(defaultButtonIf(hasSchema(model)));
		return save;
	}
	
	protected AjaxButton createClassifyButton(final IModel<RBEntity> model) {
		final AjaxButton classify = new RBStandardButton("classify") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				onClassify();
			}
		};
		classify.add(visibleIf(not(hasSchema(model))));
		classify.add(defaultButtonIf(not(hasSchema(model))));
		return classify;
	}
	
	protected AjaxButton createCancelButton(final IModel<RBEntity> model) {
		final AjaxButton cancel = new RBCancelButton("cancel") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				onCancelAndBack();
			}
		};
		return cancel;
	}
	
	// ----------------------------------------------------
	
	protected EntityManager getEntityManager() {
		return getServiceProvider().getEntityManager();
	}
	
	protected abstract ServiceProvider getServiceProvider();
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected void onDetach() {
		super.onDetach();
		typeModel.detach();
	}
	
}
