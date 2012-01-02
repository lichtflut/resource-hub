/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.organizer;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.persistence.ResourceResolver;
import org.arastreju.sge.security.User;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBEntityReference;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;

/**
 * <p>
 * This panel creates a relationship between a registered user and a resource representing a person.
 * </p><p>
 * Created: Dec 27, 2011
 * </p>
 * @author Ravi Knox
 */
@SuppressWarnings("rawtypes")
public abstract class SetUserProfilePanel extends Panel {

	private IModel<User> model;
	private IModel<RBEntityReference> resourcePickerModel = new Model<RBEntityReference>();
	private WebMarkupContainer container;
	private final IModel<Mode> mode = new Model<Mode>(Mode.VIEW);

	public enum Mode {
		EDIT, VIEW;
	}
	
	// ------------------------------------------------------------

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param model
	 */
	public SetUserProfilePanel(String id, final IModel<User> model) {
		super(id, model);
		this.model = model;
		final Form form = new Form("form");
		updateResourcePickerModel();
		
		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		container.add(createResourceLink());
		container.add(createEditButton("edit", form));
		container.add(createSaveButton("save", form));
		
		form.add(container);
		
		add(form);
	}

	/**
	 * @param model
	 */
	private void updateResourcePickerModel() {
		SemanticNode object = SNOPS.singleObject(model.getObject().getAssociatedResource(), RB.IS_RESPRESENTED_BY);
		if (object != null && object.isResourceNode()) {
			resourcePickerModel.setObject(new RBEntityReference(object.asResource()));
		}
	}

	// ------------------------------------------------------------

	/**
	 * Action to perform when 'Save'-button is clicked.
	 * @param reference 
	 */
	protected abstract void createRelationship(ResourceNode user, ResourceNode profile);

	/**
	 * Action to perform when resourcelink is clicked.
	 * @param user
	 */
	protected abstract void onResourceLinkClicked(RBEntity user);

	/**
	 * Create resource-{@link Link}
	 * @return {@link Link}
	 */
	private Component createResourceLink() {
		IModel<String> label = Model.of("No Profile set");
		ResourceNode resource = null;
		RBEntityReference ref = resourcePickerModel.getObject();
		final RBEntity userEntity = new RBEntityImpl(resource);
		if (resourcePickerModel.getObject() != null){
			resource = ref.asResource();
			label.setObject(new RBEntityImpl(resource).getLabel() + " LABEL");
		}
		Fragment f = new Fragment("user", "referenceLink", this);
		f.setOutputMarkupId(true);
		Link link = new Link("link") {
			@Override
			public void onClick() {
				onResourceLinkClicked(userEntity);
			}
		};
		link.add(new Label("label", label));
		f.add(link);
		return f;
	}
	

	/**
	 * Create {@link EntityPickerField}.
	 * @param user
	 * @return
	 */
	private Fragment createEntityPicker(Model<RBEntityReference> user) {
		Fragment f = new Fragment("user", "resourcePicker", SetUserProfilePanel.this);
		f.add(new EntityPickerField("picker", resourcePickerModel, RB.PERSON));
		f.setOutputMarkupId(true);
		return f;
	}
	
	/**
	 * Create edit-button.
	 * @param form 
	 * @param string
	 * @return
	 */
	private AjaxFallbackButton createEditButton(String id, Form form) {
		AjaxFallbackButton edit = new AjaxFallbackButton(id, form) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Model<RBEntityReference> user = Model.of(new RBEntityReference(model.getObject().getAssociatedResource()));
				mode.setObject(Mode.EDIT);
				updateResourcePickerModel();
				container.remove("user").add(createEntityPicker(user));
				
				target.add(form);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				onSubmit(target, form);
			}
		};
		edit.add(visibleIf(areEqual(mode, Mode.VIEW)));
		return edit;
	}


	/**
	 * Create save-button.
	 * @param string 
	 * @param form
	 * @return
	 */
	protected AjaxFallbackButton createSaveButton(String id, final Form<?> form) {
		final AjaxFallbackButton save = new AjaxFallbackButton(id, form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				ResourceNode user = getResourceResolver().resolve(model.getObject().getAssociatedResource());
				createRelationship(user, resourcePickerModel.getObject().asResource());
				container.remove("user").add(createResourceLink());
				mode.setObject(Mode.VIEW);
				target.add(form);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		};
		save.add(visibleIf(areEqual(mode, Mode.EDIT)));
		return save;
	}

	protected abstract ResourceResolver getResourceResolver();
}
