/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.domains;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.dialogs.CreateDomainAdminDialog;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.ConditionalModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.enableIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNotNull;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

/**
 * <p>
 *  Edit panel for {@link RBDomain}s.
 * </p>
 *
 * <p>
 * 	Created Jan 12, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public class DomainEditPanel extends TypedPanel<RBDomain> {
	
	@SpringBean
	private AuthModule authModule;
	
	private final IModel<DisplayMode> modeModel;
	
	private final ConditionalModel isViewMode;
	
	private final ConditionalModel isEditMode;
	
	private final ConditionalModel isCreateMode;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param domainModel The model containing the domain.
	 * @param mode The initial mode.
	 */
	public DomainEditPanel(final String id, final IModel<RBDomain> domainModel, final DisplayMode mode) {
		this(id, domainModel, Model.of(mode));
	}
	
	/**
	 * Constructor.
	 * @param id The ID.
	 * @param domainModel The model containing the domain.
	 * @param mode The initial mode.
	 */
	public DomainEditPanel(final String id, final IModel<RBDomain> domainModel, final IModel<DisplayMode> mode) {
		super(id, domainModel);
		
		this.modeModel = mode;
		
		this.isViewMode = areEqual(mode, DisplayMode.VIEW);
		this.isEditMode = areEqual(mode, DisplayMode.EDIT);
		this.isCreateMode = areEqual(mode, DisplayMode.CREATE);
		
		setOutputMarkupId(true);
		setOutputMarkupPlaceholderTag(true);
		
		final Form<RBDomain> form = new Form<RBDomain>("form", new CompoundPropertyModel<RBDomain>(domainModel));
		form.setOutputMarkupId(true);
		
		form.add(new FeedbackPanel("feedback"));
		
		final TextField<String> nameField = new TextField<String>("name");
		nameField.add(enableIf(isCreateMode));
		form.add(nameField);
		
		final TextField<String> titleField = new TextField<String>("title");
		titleField.add(enableIf(not(isViewMode)));
		form.add(titleField);
		
		final TextArea<String> descField = new TextArea<String>("description");
		descField.add(enableIf(not(isViewMode)));
		form.add(descField);
		
		form.add(createSaveButton());
		form.add(createEditButton());
		form.add(createCreateButton());
		form.add(createCancelButton());
		
		add(form);
		
		add(new AjaxLink("createDomainAdmin") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				final DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new CreateDomainAdminDialog(hoster.getDialogID(), domainModel));
			}
		}.add(visibleIf(isViewMode)));
		
		add(visibleIf(isNotNull(domainModel)));
	}
	
	// ----------------------------------------------------
	
	protected void onCreate() {}
	
	// ----------------------------------------------------
	
	protected AjaxButton createSaveButton() {
		final AjaxButton save = new RBDefaultButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				store();
				onModelChange(DisplayMode.VIEW);
				target.add(form);
			}
		};
		save.add(visibleIf(isEditMode));
		return save;
	}
	
	protected AjaxButton createEditButton() {
		final AjaxButton save = new RBDefaultButton("edit") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				onModelChange(DisplayMode.EDIT);
				target.add(form);
			}
		};
		save.add(visibleIf(isViewMode));
		return save;
	}
	
	protected AjaxButton createCreateButton() {
		final AjaxButton create = new RBDefaultButton("create") {
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				register();
				onModelChange(DisplayMode.VIEW);
				target.add(form);
				onCreate();
			}
		};
		create.add(visibleIf(isCreateMode));
		return create;
	}
	
	protected AjaxButton createCancelButton() {
		final AjaxButton cancel = new RBCancelButton("cancel") {
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				onModelChange(DisplayMode.VIEW);
				target.add(form);
			}
		};
		cancel.add(visibleIf(not(isViewMode)));
		return cancel;
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void detachModels() {
		super.detachModels();
		modeModel.detach();
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onEvent(IEvent<?> event) {
		final ModelChangeEvent<?> mce = ModelChangeEvent.from(event);
		if (mce.isAbout(ModelChangeEvent.DOMAIN)) {
			RBAjaxTarget.add(this);
		} 
	}
	
	private void onModelChange(DisplayMode newMode) {
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.DOMAIN));
		modeModel.setObject(newMode);
	}
	
	protected void register() {
		authModule.getDomainManager().registerDomain(getModelObject());
	}
	
	protected void store() {
		authModule.getDomainManager().updateDomain(getModelObject());
	}
	
}
