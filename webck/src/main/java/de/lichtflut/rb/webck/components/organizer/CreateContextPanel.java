/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.organizer;

import static de.lichtflut.rb.webck.components.ComponentFactory.addTextField;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SimpleFormComponentLabel;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import de.lichtflut.rb.core.api.DomainOrganizer;
import de.lichtflut.rb.core.organizer.ContextDeclaration;
import de.lichtflut.rb.webck.behaviors.DefaultButtonBehavior;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;

/**
 * <p>
 *  Panel for creation of a new Namespace.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class CreateContextPanel extends Panel {
	
	private final IModel<ContextDeclaration> model;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	public CreateContextPanel(String id) {
		this(id, Model.of(new ContextDeclaration()));
	}
	
	/**
	 * @param id
	 * @param model
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CreateContextPanel(final String id, final IModel<ContextDeclaration> model) {
		super(id);
		this.model = model;
		
		final Form form = new Form("form"); 
		form.setOutputMarkupId(true);
		
		addTextField("qualifiedName", new PropertyModel(model, "qualifiedName"), form)
			.setRequired(true);
		
		final TextArea<String> desc = new TextArea<String>("description", new PropertyModel(model,"description"));
		final ComponentFeedbackPanel feedback = new ComponentFeedbackPanel("descriptionFeedback", desc);
		feedback.setOutputMarkupPlaceholderTag(true);
		form.add(feedback);
		desc.setLabel(new ResourceModel("description"));
		form.add(new SimpleFormComponentLabel("descriptionLabel", desc));
		form.add(desc);
		
		form.add(new RBStandardButton("create") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				getOrganizer().registerContext(model.getObject());
				send(getPage(), Broadcast.BUBBLE, new ModelChangeEvent(ModelChangeEvent.NAMESPACE));
				onSuccess(target);
				resetModel();
			}
			
		}.add(new DefaultButtonBehavior()));
		
		form.add(new RBCancelButton("cancel") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onCancel(target);
				resetModel();
			}
			
		});
		
		add(form);
		
	}

	// ----------------------------------------------------
	
	public void onCancel(AjaxRequestTarget target){}
	
	public void onSuccess(AjaxRequestTarget target){}

	public abstract DomainOrganizer getOrganizer();
	
	// ----------------------------------------------------
	

	/** 
	* {@inheritDoc}
	*/
	@Override
	protected void detachModel() {
		super.detachModel();
		model.detach();
	}
	
	private void resetModel() {
		model.setObject(new ContextDeclaration());
	}
	
}
