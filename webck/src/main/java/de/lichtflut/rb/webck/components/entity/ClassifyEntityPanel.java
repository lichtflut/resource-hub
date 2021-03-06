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

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.fields.ClassPickerField;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.hasSchema;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

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
	private EntityManager entityManager;
	
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
		entityManager.changeType(getModelObject(), targetModel.getObject());
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
