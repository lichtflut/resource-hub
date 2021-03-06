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
package de.lichtflut.rb.webck.components.typesystem;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isEmpty;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.fields.ClassPickerField;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

/**
 * <p>
 *  Panel displaying the type hierarchy of an rdfs:Class.
 * </p>
 *
 * <p>
 * 	Created Mar 7, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class TypeHierarchyPanel extends TypedPanel<ResourceID> {

	private static final Logger LOGGER = LoggerFactory.getLogger(TypeHierarchyPanel.class);

	@SpringBean
	private TypeManager typeManager;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model containing the ID of the type.
	 */
	@SuppressWarnings("rawtypes")
	public TypeHierarchyPanel(final String id, final IModel<ResourceID> model) {
		super(id, model);

		setOutputMarkupId(true);

		add(new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(TypeHierarchyPanel.this)));

		final SuperClassModel superClassModel = new SuperClassModel(model);

		final ListView<SNClass> view = new ListView<SNClass>("superClasses", superClassModel) {
			@Override
			protected void populateItem(final ListItem<SNClass> item) {
				item.add(new Label("class", item.getModel()));
				item.add(new AjaxLink("remove") {
					@Override
					public void onClick(final AjaxRequestTarget target) {
						removeSuperClass(item.getModelObject());
						superClassModel.reset();
						TypeHierarchyPanel.this.info(getString("confirmation.deleted-successful"));
						target.add(TypeHierarchyPanel.this);
					}
				});
			}
		};
		view.setReuseItems(true);
		add(view);

		final Label hint = new Label("noSuperClassesHint", new ResourceModel("label.no-super-classes"));
		hint.add(visibleIf(isEmpty(superClassModel)));
		add(hint);

		final Form<?> form = new Form<Void>("form");

		final IModel<ResourceID> newSuperClass = new Model<ResourceID>();

		form.add(new ClassPickerField("classPicker", newSuperClass));
		form.add(new RBDefaultButton("addClass") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				if(newSuperClass == null || newSuperClass.getObject() == null){
					error(getString("error.no-data"));
					RBAjaxTarget.add(TypeHierarchyPanel.this);
				} else{
					addSuperClass(newSuperClass.getObject());
					newSuperClass.setObject(null);
					info(getString("confirmation.saved-successful"));
					target.add(TypeHierarchyPanel.this);
				}
			}
		});
		add(form);
	}

	// ----------------------------------------------------

	protected void addSuperClass(final ResourceID superClass) {
		final ResourceID baseClass = getModelObject();
		LOGGER.info("adding super class to : " + baseClass);
		typeManager.addSuperClass(baseClass, superClass);
	}

	protected void removeSuperClass(final ResourceID superClass) {
		final ResourceID baseClass = getModelObject();
		LOGGER.info("removing super class to : " + baseClass);
		typeManager.removeSuperClass(baseClass, superClass);
	}

	// ----------------------------------------------------

	private class SuperClassModel extends DerivedDetachableModel<List<SNClass>, ResourceID> {

		public SuperClassModel(final IModel<ResourceID> base) {
			super(base);
		}

		@Override
		protected List<SNClass> derive(final ResourceID base) {
			return new ArrayList<SNClass>(typeManager.getSuperClasses(base));
		}

	}

}
