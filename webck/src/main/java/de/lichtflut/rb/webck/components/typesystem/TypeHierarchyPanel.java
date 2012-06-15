/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isEmpty;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.services.ServiceProvider;
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
	
	private static final Logger logger = LoggerFactory.getLogger(TypeHierarchyPanel.class);
	
	@SpringBean
	private ServiceProvider provider;
	
	@SpringBean
	private ModelingConversation conversation;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model containing the ID of the type.
	 */
	@SuppressWarnings("rawtypes")
	public TypeHierarchyPanel(String id, IModel<ResourceID> model) {
		super(id, model);
		
		setOutputMarkupId(true);
		
		final SuperClassModel superClassModel = new SuperClassModel(model);
		
		final ListView<SNClass> view = new ListView<SNClass>("superClasses", superClassModel) {
			@Override
			protected void populateItem(final ListItem<SNClass> item) {
				item.add(new Label("class", item.getModel()));
				item.add(new AjaxLink("remove") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						removeSuperClass(item.getModelObject());
						superClassModel.reset();
						target.add(TypeHierarchyPanel.this);
					}
				});
			}
		};
		add(view);
		
		final Label hint = new Label("noSuperClassesHint", new ResourceModel("label.no-super-classes"));
		hint.add(visibleIf(isEmpty(superClassModel)));
		add(hint);
		
		final Form<?> form = new Form<Void>("form");
		form.add(new FeedbackPanel("feedback"));
		
		final IModel<ResourceID> newSuperClass = new Model<ResourceID>();
		
		form.add(new ClassPickerField("classPicker", newSuperClass));
		form.add(new RBDefaultButton("addClass") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				addSuperClass(newSuperClass.getObject());
				newSuperClass.setObject(null);
				target.add(TypeHierarchyPanel.this);
			}
		});
		add(form);
	}
	
	// ----------------------------------------------------
	
	protected void addSuperClass(ResourceID superClass) {
		final ResourceID baseClass = getModelObject();
		logger.info("adding super class to : " + baseClass);
		provider.getTypeManager().addSuperClass(baseClass, superClass);
	}

	protected void removeSuperClass(ResourceID superClass) {
		final ResourceID baseClass = getModelObject();
		logger.info("removing super class to : " + baseClass);
		provider.getTypeManager().removeSuperClass(baseClass, superClass);
	}

	
	// ----------------------------------------------------
	
	private class SuperClassModel extends DerivedDetachableModel<List<SNClass>, ResourceID> {
		
		public SuperClassModel(IModel<ResourceID> base) {
			super(base);
		}

		/** 
		 * {@inheritDoc}
		 */
		@Override
		protected List<SNClass> derive(ResourceID base) {
			final ResourceNode typeNode = conversation.resolve(base);
			final List<SNClass> result = new ArrayList<SNClass>();
			result.addAll(typeNode.asClass().getDirectSuperClasses());
			return result;
		}
		
	}

}
