/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.organizer;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.persistence.ResourceResolver;
import org.arastreju.sge.security.User;
import org.arastreju.sge.security.impl.UserImpl;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.browsing.BrowsingHistory;
import de.lichtflut.rb.webck.browsing.JumpTarget;
import de.lichtflut.rb.webck.browsing.ReferenceReceiveAction;
import de.lichtflut.rb.webck.browsing.ResourceAttributeApplyAction;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
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

	private final DerivedModel<ResourceID, User> profileModel;
	private final IModel<Mode> mode = new Model<Mode>(Mode.VIEW);
	private IModel<User> user;
	private IModel<Boolean> hasProfile;
	private IModel<ResourceID> profile;

	private enum Mode {
		EDIT, VIEW;
	}
	
	// ------------------------------------------------------

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param model
	 */
	public SetUserProfilePanel(String id, final IModel<User> user) {
		super(id, user);
		this.user = user;
		this.hasProfile = Model.of(true);
		this.profileModel = refreshProfileModel();
		
		final Form form = new Form("form");
		populateForm(form);
		
		add(form);
	}

	// ------------------------------------------------------

	/**
	 * Populates the {@link Form}
	 * @param form
	 */
	private void populateForm(final Form form) {
		form.add(createResourceLink());
		form.add(createEntityPicker());
		form.add(createCreateLink());
		form.add(createDeleteLink());
		form.add(createEditButton("edit", form));
		form.add(createSaveButton("save", form));
	}

	/**
	 * @return an {@link AjaxSubmitLink} that removes the current Profile association.
	 */
	private AjaxSubmitLink createDeleteLink() {
		final AjaxSubmitLink link = new AjaxSubmitLink("deleteLink") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(profileModel.getObject() != null){
					SNOPS.remove(user.getObject().getAssociatedResource(), RB.IS_RESPRESENTED_BY, profileModel.getObject());
					refreshModels();
					mode.setObject(Mode.VIEW);
					target.add(form);
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(SetUserProfilePanel.this);
			}
		};
		link.add(visibleIf(areEqual(mode, Mode.EDIT)));
		return link;
	}

	/**
	 * @return a {@link AjaxSubmitLink} that allows the user to create a new Person Resource.
	 */
	private AjaxSubmitLink createCreateLink() {
		final AjaxSubmitLink link = new AjaxSubmitLink("createLink") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				final EntityHandle handle = EntityHandle.forType(RB.PERSON);
				final ReferenceReceiveAction action = new ResourceAttributeApplyAction(
					user.getObject().getAssociatedResource(), RB.IS_RESPRESENTED_BY);
				getHistory().clear(new JumpTarget(getUserProfilePage()));
				getHistory().createReference(handle, action);
				jumpToResourceEditorPage(handle);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(SetUserProfilePanel.this);
			}
		};
		link.add(visibleIf(areEqual(mode, Mode.EDIT)));
		return link;
	}

	/**
	 * Calls the {@link WebPage} to edit a resource.
	 * @param handle
	 */
	protected abstract void jumpToResourceEditorPage(EntityHandle handle);

	/**
	 * @return the {@link WebPage} that displays a users' profile
	 */
	protected abstract Class<? extends Page> getUserProfilePage();

	/**
	 * Action to perform when resourcelink is clicked.
	 * @param resourceNode
	 */
	protected abstract void onResourceLinkClicked(ResourceNode node);
	
	protected abstract SchemaManager getSchemaManager();
	
	protected abstract ResourceResolver getResourceResolver();

	/**
	 * @param ref - {@link ResourceID}
	 * @return a {@link IModel} that represents the resource label.
	 */
	private IModel<String> getResourceLabel() {
		IModel<String> label;
		if (profileModel.getObject() != null){
			label = new ResourceLabelModel(profileModel);
		}else{
			label = Model.of(profileModel);
			hasProfile.setObject(false);
		}
		return label;
	}

	/**
	 * @param label - {@link IModel}
	 * @return a {@link Link}
	 */
	private Link createResourceLink() {
		Link link = new Link("link") {
			@Override
			public void onClick() {
				onResourceLinkClicked(profileModel.getObject().asResource());
			}
		};
		link.add(new Label("label", getResourceLabel()));
		if(!hasProfile.getObject()){
			link.setEnabled(false);
		}
		link.add(visibleIf(areEqual(mode, Mode.VIEW)));
		return link;
	}
	
	/**
	 * Create {@link EntityPickerField}.
	 * @return
	 */
	private EntityPickerField createEntityPicker() {
		if(hasProfile.getObject()){
			profile = Model.of(profileModel.getObject());
		}else{
			profile = new Model<ResourceID>();
		}
		EntityPickerField picker = new EntityPickerField("picker", profile, RB.PERSON);
		picker.add(visibleIf(areEqual(mode, Mode.EDIT)));
		return picker;
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
				mode.setObject(Mode.EDIT);
				RBAjaxTarget.add(form);
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
	 * @param string - wicket:id
	 * @param form
	 * @return
	 */
	protected AjaxFallbackButton createSaveButton(String id, final Form<?> form) {
		final AjaxFallbackButton save = new AjaxFallbackButton(id, form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				ResourceNode userNode = getResourceResolver().resolve(user.getObject().getAssociatedResource());
				createRelationship(userNode, profile.getObject());
				refreshModels();
				mode.setObject(Mode.VIEW);
				RBAjaxTarget.add(form);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				RBAjaxTarget.add(form);
			}
		};
		save.add(visibleIf(areEqual(mode, Mode.EDIT)));
		return save;
	}
	
	/**
	 * @param userNode
	 */
	private void refreshModels() {
		refreshUser();
		refreshProfileModel();
	}
	
	/**
	 * @param userNode
	 */
	private void refreshUser() {
		ResourceNode userNode = getResourceResolver().resolve(user.getObject().getAssociatedResource());
		user.setObject(new UserImpl(userNode));
	}
	
	/**
	 * @return a {@link DerivedModel} for the userprofile
	 */
	private DerivedModel<ResourceID, User> refreshProfileModel() {
		return new DerivedModel<ResourceID, User>(user) {
			@Override
			protected ResourceID derive(User original) {
				final SemanticNode node = SNOPS.singleObject(original.getAssociatedResource(), RB.IS_RESPRESENTED_BY);
				if (node != null) {
					hasProfile.setObject(true);
					return node.asResource();
				} else {
					hasProfile.setObject(false);
					return new SimpleResourceID("No Profile set");
				}
			}
		};
	}
	
	/**
	 * Action to perform when 'Save'-button is clicked.
	 * @param reference 
	 */
	protected void createRelationship(ResourceNode userNode, ResourceID resourceID){
		if(resourceID != null){
			SNOPS.assure(userNode, RB.IS_RESPRESENTED_BY, resourceID, RB.PRIVATE_CONTEXT);
		}
	}

	private BrowsingHistory getHistory(){
		return RBWebSession.get().getHistory();
	}
}
