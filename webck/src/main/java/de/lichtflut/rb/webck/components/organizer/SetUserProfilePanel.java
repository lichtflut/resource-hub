/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.organizer;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.enableIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;
import static de.lichtflut.rb.webck.models.ConditionalModel.isTrue;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.browsing.BrowsingHistory;
import de.lichtflut.rb.webck.browsing.JumpTarget;
import de.lichtflut.rb.webck.browsing.ReferenceReceiveAction;
import de.lichtflut.rb.webck.browsing.ResourceAttributeApplyAction;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableModel;
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

	private final IModel<DisplayMode> mode = new Model<DisplayMode>(DisplayMode.VIEW);
	private IModel<Boolean> hasProfile;
	private IModel<ResourceNode> userModel;
	private IModel<ResourceID> profileModel;
	private IModel<ResourceID> entityPickerModel;

	@SpringBean
	private DomainOrganizer domainOrganizer;
	
	@SpringBean
	private ModelingConversation conversation;
	
	// ---------------- Constructor -------------------------
	
	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param model
	 */
	public SetUserProfilePanel(String id, final IModel<RBUser> user) {
		super(id, user);
		initUserModel(user);
		initProfileModel();
		initHasProfileModel();
		
		final Form form = new Form("form");
		populateForm(form);
		
		add(form);
	}

	// ------------------------------------------------------
	
	/**
	 * Calls the {@link WebPage} to edit a resource.
	 * @param handle
	 */
	protected abstract void jumpToResourceEditorPage(EntityHandle handle);
	
	/**
	 * Action to perform when resourcelink is clicked.
	 * @param resourceNode
	 */
	protected abstract void onResourceLinkClicked(ResourceNode node);
	
	// ------------------------------------------------------
	
	/**
	 * @return the {@link WebPage} that displays a users' profile
	 */
	protected Class<? extends Page> getOffsetPage() {
		return getPage().getPageClass();
	}
	
	// ----------------------------------------------------

	/**
	 * Initialize variable profileModel.
	 * @param user
	 */
	private void initProfileModel() {
		this.profileModel = new AbstractLoadableDetachableModel<ResourceID>() {
			@Override
			public ResourceID load() {
				ResourceID person =  domainOrganizer.getUsersPerson();
				if (person != null) {
					return person;
				} else {
					return new SimpleResourceID("No Profile set");
				}
			}
		};
	}

	/**
	 * Initialize variable hasProfile
	 */
	private void initHasProfileModel() {
		this.hasProfile = new AbstractLoadableDetachableModel<Boolean>() {
			@Override
			public Boolean load() {
				return domainOrganizer.getUsersPerson() != null;
			}
		};
	}

	/**
	 * Initialize variable user.
	 * @param user
	 */
	private void initUserModel(final IModel<RBUser> user) {
		this.userModel = new AbstractLoadableModel<ResourceNode>() {
			@Override
			public ResourceNode load() {
				return conversation.findResource(user.getObject().getQualifiedName());
			}
		};
	}

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

	// ------------------------------------------------------

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
		link.add(enableIf(isTrue(hasProfile)));
		link.add(visibleIf(areEqual(mode, DisplayMode.VIEW)));
		return link;
	}

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
		}
		return label;
	}

	/**
	 * Create {@link EntityPickerField}.
	 * @return
	 */
	private EntityPickerField createEntityPicker() {
		if(hasProfile.getObject()){
			entityPickerModel = Model.of(profileModel.getObject());
		}else{
			entityPickerModel = new Model<ResourceID>();
		}
		EntityPickerField picker = new EntityPickerField("picker", entityPickerModel, RB.PERSON);
		picker.add(visibleIf(areEqual(mode, DisplayMode.EDIT)));
		return picker;
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
					userModel.getObject(), RBSystem.IS_RESPRESENTED_BY);
				getHistory().clear(new JumpTarget(getOffsetPage()));
				getHistory().createReference(handle, action);
				jumpToResourceEditorPage(handle);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(SetUserProfilePanel.this);
			}
		};
		link.add(visibleIf(areEqual(mode, DisplayMode.EDIT)));
		return link;
	}
	
	/**
	 * @return an {@link AjaxSubmitLink} that removes the current Profile association.
	 */
	private AjaxSubmitLink createDeleteLink() {
		final AjaxSubmitLink link = new AjaxSubmitLink("deleteLink") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(profileModel.getObject() != null){
					SNOPS.remove(userModel.getObject(), RBSystem.IS_RESPRESENTED_BY, profileModel.getObject());
					mode.setObject(DisplayMode.VIEW);
					RBAjaxTarget.add(form);
				}
			}
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				RBAjaxTarget.add(SetUserProfilePanel.this);
			}
		};
		link.add(visibleIf(areEqual(mode, DisplayMode.EDIT)));
		return link;
	}

	/**
	 * Create edit-button.
	 * @param form 
	 * @param string
	 * @return
	 */
	private AjaxButton createEditButton(String id, Form form) {
		AjaxButton edit = new RBStandardButton(id) {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form){
				mode.setObject(DisplayMode.EDIT);
				RBAjaxTarget.add(form);
			}
		};
		edit.add(visibleIf(areEqual(mode, DisplayMode.VIEW)));
		return edit;
	}

	/**
	 * Create save-button.
	 * @param string - wicket:id
	 * @param form
	 * @return
	 */
	protected AjaxButton createSaveButton(String id, final Form<?> form) {
		final AjaxButton save = new RBDefaultButton(id) {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form){
				domainOrganizer.setUsersPerson(entityPickerModel.getObject());
				mode.setObject(DisplayMode.VIEW);
				RBAjaxTarget.add(form);
			}
		};
		save.add(visibleIf(areEqual(mode, DisplayMode.EDIT)));
		return save;
	}
	
	private BrowsingHistory getHistory(){
		return RBWebSession.get().getHistory();
	}
	
	// ------------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected void onDetach() {
		entityPickerModel.detach();
		hasProfile.detach();
		profileModel.detach();
		super.onDetach();
	}
}
