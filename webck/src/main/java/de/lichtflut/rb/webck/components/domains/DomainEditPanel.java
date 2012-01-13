/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.domains;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.enableIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.Organizer;
import org.arastreju.sge.security.Domain;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.ConditionalModel;

/**
 * <p>
 *  Edit panel for {@link Domain}s.
 * </p>
 *
 * <p>
 * 	Created Jan 12, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public abstract class DomainEditPanel extends Panel{
	
	private final IModel<Domain> domainModel;
	
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
	public DomainEditPanel(final String id, final IModel<Domain> domainModel, final DisplayMode mode) {
		this(id, domainModel, Model.of(mode));
	}
	
	/**
	 * Constructor.
	 * @param id The ID.
	 * @param domainModel The model containing the domain.
	 * @param mode The initial mode.
	 */
	public DomainEditPanel(final String id, final IModel<Domain> domainModel, final IModel<DisplayMode> mode) {
		super(id);
		this.domainModel = domainModel;
		
		this.modeModel = mode;
		
		this.isViewMode = areEqual(mode, DisplayMode.VIEW);
		this.isEditMode = areEqual(mode, DisplayMode.EDIT);
		this.isCreateMode = areEqual(mode, DisplayMode.CREATE);
		
		final Form<Domain> form = new Form<Domain>("form", new CompoundPropertyModel<Domain>(domainModel));

		form.setOutputMarkupId(true);
		
		form.add(new FeedbackPanel("feedback"));
		
		final TextField<String> nameField = new TextField<String>("uniqueName");
		nameField.add(enableIf(isCreateMode));
		form.add(nameField);
		
		final TextField<String> titleField = new TextField<String>("title");
		titleField.add(enableIf(not(isViewMode)));
		form.add(titleField);
		
		final TextArea<String> descField = new TextArea<String>("description");
		descField.add(enableIf(not(isViewMode)));
		form.add(descField);
		
		final CheckBox cb = new CheckBox("masterDomain");
		cb.setEnabled(false);
		form.add(cb);
		
		form.add(createSaveButton());
		form.add(createEditButton());
		form.add(createCreateButton());
		form.add(createCancelButton());
		
		add(form);
	}
	
	// ----------------------------------------------------
	
	protected abstract ServiceProvider getServiceProvider();
	
	// ----------------------------------------------------
	
	protected void onCreate() {}
	
	// ----------------------------------------------------
	
	protected AjaxButton createSaveButton() {
		final AjaxButton save = new RBDefaultButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				organizer().updateDomain(domainModel.getObject());
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
				final Domain domain = domainModel.getObject();
				organizer().registerDomain(domain.getUniqueName(), domain.getTitle(), domain.getDescription());
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
		domainModel.detach();
		modeModel.detach();
	}
	
	// ----------------------------------------------------
	
	private void onModelChange(DisplayMode newMode) {
		if (domainModel.getObject().isMasterDomain()) {
			send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.MASTER_DOMAIN));
		} else {
			send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.DOMAINS));
		}
		modeModel.setObject(newMode);
	}
	
	private Organizer organizer() {
		return getServiceProvider().getArastejuGate().getOrganizer();
	}
}
