/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.organizer;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.persistence.ResourceResolver;
import org.arastreju.sge.security.User;
import org.arastreju.sge.security.impl.UserImpl;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.browsing.ReferenceReceiveAction;
import de.lichtflut.rb.webck.browsing.JumpTarget;
import de.lichtflut.rb.webck.browsing.ResourceAttributeApplyAction;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;

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
	private final IModel<ResourceID> profileModel;
	private IModel<Boolean> hasProfile;
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
		this.hasProfile = Model.of(true);
		final Form form = new Form("form");
		
		profileModel = new DerivedModel<ResourceID, User>(model) {
			@Override
			protected ResourceID derive(User original) {
				final SemanticNode node = SNOPS.singleObject(original.getAssociatedResource(), RB.IS_RESPRESENTED_BY);
				if (node != null) {
					return node.asResource();
				} else {
					return null;
				}
			}
		};
		
		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		container.add(createResourceLink());
		container.add(createCreateLink());
		container.add(createDeleteLink());
		container.add(createEditButton("edit", form));
		container.add(createSaveButton("save", form));
		
		form.add(container);
		
		add(form);
	}

	// ------------------------------------------------------------
	
	
	/**
	 * @return
	 */
	private AjaxSubmitLink createDeleteLink() {
		final AjaxSubmitLink link = new AjaxSubmitLink("deleteLink") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(profileModel.getObject() != null){
					SNOPS.remove(model.getObject().getAssociatedResource(), RB.IS_RESPRESENTED_BY, profileModel.getObject());
					target.add(form);
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(SetUserProfilePanel.this);
			}
		};
		if(mode.equals(Mode.VIEW)){
			link.setVisible(false);
		}
		return link;
	}

	/**
	 * @return a {@link Link}
	 */
	private AjaxSubmitLink createCreateLink() {
		final AjaxSubmitLink link = new AjaxSubmitLink("createLink") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				final EntityHandle handle = EntityHandle.forType(RB.PERSON);
				final ReferenceReceiveAction action = new ResourceAttributeApplyAction(
						model.getObject().getAssociatedResource(), RB.IS_RESPRESENTED_BY);
				RBWebSession.get().getHistory().clear(new JumpTarget(getUserProfilePage()));
				RBWebSession.get().getHistory().createReference(handle, action);
				jumpToResourceEditorPage(handle);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(SetUserProfilePanel.this);
			}
		};
		if(mode.equals(Mode.VIEW)){
			link.setVisible(false);
		}
		return link;
	}

	protected abstract void jumpToResourceEditorPage(EntityHandle handle);

	protected abstract Class<? extends Page> getUserProfilePage();

	protected abstract SchemaManager getSchemaManager();
	
	protected abstract ResourceResolver getResourceResolver();

	/**
	 * Action to perform when resourcelink is clicked.
	 * @param resourceNode
	 */
	protected abstract void onResourceLinkClicked(ResourceNode node);
	
	/**
	 * Action to perform when 'Save'-button is clicked.
	 * @param reference 
	 */
	protected void createRelationship(ResourceNode user, ResourceNode profile){
		SNOPS.assure(user, RB.IS_RESPRESENTED_BY, profile, RB.PRIVATE_CONTEXT);
	}
	

	/**
	 * Create resource-{@link Link}
	 * @return {@link Link}
	 */
	private Component createResourceLink() {
		IModel<String> label;
		final ResourceID ref = profileModel.getObject();
		if (ref != null){
			label = new ResourceLabelModel(Model.of(ref.asResource()));
		}else{
			label = Model.of("No Profile set");
		}
		Fragment f = createReferenceLink(label, ref);
		return f;
	}

	/**
	 * @param label
	 * @param ref
	 * @return a {@link Fragment} containing a {@link Link}
	 */
	private Fragment createReferenceLink(IModel<String> label, final ResourceID ref) {
		Fragment f = new Fragment("user", "referenceLink", this);
		f.setOutputMarkupId(true);
		Link link = new Link("link") {
			@Override
			public void onClick() {
				onResourceLinkClicked(ref.asResource());
			}
		};
		link.add(new Label("label", label));
		if(!hasProfile.getObject()){
			link.setEnabled(false);
		}
		f.add(link);
		return f;
	}
	


	/**
	 * Create {@link EntityPickerField}.
	 * @param user
	 * @return
	 */
	private Fragment createEntityPicker(Model<ResourceID> user) {
		Fragment f = new Fragment("user", "resourcePicker", SetUserProfilePanel.this);
		f.add(new EntityPickerField("picker", profileModel, RB.PERSON));
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
				Model<ResourceID> user = new Model<ResourceID>(model.getObject().getAssociatedResource());
				mode.setObject(Mode.EDIT);
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
				createRelationship(user, profileModel.getObject().asResource());
				user = getResourceResolver().resolve(user);
				model.setObject(new UserImpl(user));
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

}
