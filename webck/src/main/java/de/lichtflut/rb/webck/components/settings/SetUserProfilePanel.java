/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.settings;

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
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.webck.browsing.BrowsingHistory;
import de.lichtflut.rb.webck.browsing.JumpTarget;
import de.lichtflut.rb.webck.browsing.ReferenceReceiveAction;
import de.lichtflut.rb.webck.browsing.ResourceAttributeApplyAction;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.common.RBWebSession;
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


	@SpringBean
	private DomainOrganizer domainOrganizer;

	private final SimpleResourceID defaultProfileModel = new SimpleResourceID("No Profile set");
	private final IModel<DisplayMode> mode = new Model<DisplayMode>(DisplayMode.VIEW);
	private IModel<Boolean> hasProfile;
	private IModel<ResourceNode> userModel;
	private AbstractLoadableModel<ResourceID> profileModel;
	private IModel<ResourceID> entityPickerModel;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param user The user.
	 */
	public SetUserProfilePanel(final String id, final IModel<RBUser> user) {
		super(id, user);
		initUserModel(user);
		initProfileModel();
		initHasProfileModel();

		final Form form = new Form("form");
		populateForm(form);

		add(form);
		setOutputMarkupId(true);
	}

	// ------------------------------------------------------

	/**
	 * Calls the {@link WebPage} to edit a resource.
	 * @param handle
	 */
	protected abstract void jumpToResourceEditorPage(EntityHandle handle);

	/**
	 * Action to perform when resourcelink is clicked.
	 * @param node
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
	 */
	private void initProfileModel() {
		this.profileModel = new AbstractLoadableDetachableModel<ResourceID>() {
			@Override
			public ResourceID load() {
				ResourceID person =  domainOrganizer.getUsersPerson();
				if (person != null) {
					return person;
				} else {
					return defaultProfileModel;
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
		userModel = new AbstractLoadableModel<ResourceNode>() {
			@Override
			public ResourceNode load() {
				ResourceID usersPerson = domainOrganizer.getUsersPerson();
				if(usersPerson == null){
					return null;
				}
				return usersPerson.asResource();
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
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				final EntityHandle handle = EntityHandle.forType(RB.PERSON);
				final ReferenceReceiveAction action = new ResourceAttributeApplyAction(
						userModel.getObject(), RBSystem.IS_RESPRESENTED_BY);
				getHistory().clear(new JumpTarget(getOffsetPage()));
				getHistory().createReference(handle, action);
				jumpToResourceEditorPage(handle);
			}

			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
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
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				if(profileModel.getObject() != defaultProfileModel){
					domainOrganizer.setUsersPerson(null);
					mode.setObject(DisplayMode.VIEW);
					profileModel.reset();
					RBAjaxTarget.add(form);
				}
			}
			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				RBAjaxTarget.add(SetUserProfilePanel.this);
			}
		};
		link.add(visibleIf(areEqual(mode, DisplayMode.EDIT)));
		return link;
	}

	/**
	 * Create edit-button.
	 */
	private AjaxButton createEditButton(final String id, final Form form) {
		AjaxButton edit = new RBStandardButton(id) {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form){
				mode.setObject(DisplayMode.EDIT);
				RBAjaxTarget.add(form);
			}
		};
		edit.add(visibleIf(areEqual(mode, DisplayMode.VIEW)));
		return edit;
	}

	/**
	 * Create save-button.
	 */
	protected AjaxButton createSaveButton(final String id, final Form<?> form) {
		final AjaxButton save = new RBDefaultButton(id) {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form){
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
