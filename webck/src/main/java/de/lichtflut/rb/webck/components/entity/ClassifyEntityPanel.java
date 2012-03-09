/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.hasSchema;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.fields.ClassPickerField;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

/**
 * <p>
 *  Panel for classification of an {@link RBEntity}.
 *  
 *  This panel must be added beneath a form!
 * </p>
 *
 * <p>
 * 	Created Dec 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ClassifyEntityPanel extends TypedPanel<RBEntity> {
	
	@SpringBean
	private ServiceProvider provider;

	private final IModel<ResourceID> targetModel = new Model<ResourceID>();
	
	private final IModel<ResourceID> superClassModel;
	
	// ----------------------------------------------------
	
	/**
	 * @param id - wicket:id
	 * @param entityModel - the entity to be classified.
	 */
	public ClassifyEntityPanel(final String id, final  IModel<RBEntity> entityModel) {
		super(id, entityModel);
		
		this.superClassModel = new DerivedModel<ResourceID, RBEntity>(entityModel) {
			@Override
			protected ResourceID derive(RBEntity original) {
				return original.getType();
			}
		};
		
		final ClassPickerField picker = new ClassPickerField("typePicker", targetModel, superClassModel);
		add(picker);
		
		add(visibleIf(not(hasSchema(entityModel))));
		
		add(createSaveButton(entityModel));
		
		add(createCancelButton(entityModel));
	}
	
	// ----------------------------------------------------
	
	public void onClassify() {
		provider.getEntityManager().changeType(getModelObject(), targetModel.getObject());
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
	}
	
	// ----------------------------------------------------

	protected Component createSaveButton(final IModel<RBEntity> model) {
		final RBStandardButton save = new RBDefaultButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				onClassify();
			}
		};
		return save;
	}
	
	protected Component createCancelButton(final IModel<RBEntity> model) {
		final RBCancelButton cancel = new RBCancelButton("cancel") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				RBWebSession.get().getHistory().back();
				send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
			}
		};
		return cancel;
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	protected void onConfigure() {
		super.onConfigure();
		targetModel.setObject(null);
	}

}
