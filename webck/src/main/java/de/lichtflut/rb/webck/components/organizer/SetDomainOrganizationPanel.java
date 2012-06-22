/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.organizer;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.and;
import static de.lichtflut.rb.webck.models.ConditionalModel.isFalse;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNull;
import static de.lichtflut.rb.webck.models.ConditionalModel.isTrue;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;

/**
 * <p>
 *  Panel for setting the organisation of the domain.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SetDomainOrganizationPanel extends TypedPanel<ResourceID> {
	
	@SpringBean
	private DomainOrganizer domainOrganizer;

	private final IModel<Boolean> readonly;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 * @param model
	 */
	@SuppressWarnings("rawtypes")
	public SetDomainOrganizationPanel(String id, IModel<ResourceID> model) {
		super(id, model);
		this.readonly = Model.of(true);
		
		setOutputMarkupId(true);
		
		final Form form = new Form("form");
		form.add(new FeedbackPanel("feedback"));
		form.setOutputMarkupId(true);
		
		final Label noOrgInfo = new Label("noOrgInfo", new ResourceModel("info.no-organisation-set"));
		noOrgInfo.add(visibleIf(and(isTrue(readonly), isNull(model))));
		form.add(noOrgInfo);
		
		final Label roLabel = new Label("readonly", new ResourceLabelModel(model));
		roLabel.add(visibleIf(and(isTrue(readonly), not(isNull(model)))));
		form.add(roLabel);
		
		final EntityPickerField picker = new EntityPickerField("entityPicker", model, RB.ORGANIZATION);
		picker.add(visibleIf(isFalse(readonly)));
		form.add(picker);
		
		form.add(createChangeButton());
		form.add(createSetButton());
		form.add(createSaveButton());
		form.add(createCancelButton());
		
		add(form);
	}
	
	// ----------------------------------------------------
	
	protected Component createSetButton() {
		return new RBStandardButton("set") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				readonly.setObject(false);
				target.add(SetDomainOrganizationPanel.this);
			}
		}.add(visibleIf(and(isTrue(readonly), isNull(getOrganizationModel()))));
	}
	
	protected Component createChangeButton() {
		return new RBStandardButton("change") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				readonly.setObject(false);
				target.add(SetDomainOrganizationPanel.this);
			}
		}.add(visibleIf(and(isTrue(readonly), not(isNull(getOrganizationModel() )))));
	}

	protected Component createSaveButton() {
		return new RBDefaultButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				domainOrganizer.setDomainOrganization(getOrganizationModel() .getObject());
				readonly.setObject(true);
				target.add(SetDomainOrganizationPanel.this);
			}
		}.add(visibleIf(isFalse(readonly)));
	}
	
	protected Component createCancelButton() {
		return new RBStandardButton("cancel") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				readonly.setObject(true);
				target.add(SetDomainOrganizationPanel.this);
			}
		}.add(visibleIf(isFalse(readonly)));
	}
	
	// ----------------------------------------------------
	
	private IModel<ResourceID> getOrganizationModel() {
		return getModel();
	}
	
}
