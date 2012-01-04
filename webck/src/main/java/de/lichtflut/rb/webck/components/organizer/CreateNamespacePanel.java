/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.organizer;

import static de.lichtflut.rb.webck.components.ComponentFactory.addTextField;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;
import org.apache.wicket.validation.validator.UrlValidator;

import de.lichtflut.rb.core.organizer.NamespaceDeclaration;
import de.lichtflut.rb.core.services.DomainOrganizer;
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
public abstract class CreateNamespacePanel extends Panel {
	
	private final IModel<NamespaceDeclaration> model;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	public CreateNamespacePanel(String id) {
		this(id, Model.of(new NamespaceDeclaration()));
	}
	
	/**
	 * @param id
	 * @param model
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CreateNamespacePanel(final String id, final IModel<NamespaceDeclaration> model) {
		super(id);
		this.model = model;
		
		final Form form = new Form("form"); 
		form.setOutputMarkupId(true);
		
		addTextField("uri", new PropertyModel(model, "uri"), form)
			.add(new UrlValidator())
			.add(new AbstractValidator<String>(){
				@Override
				protected void onValidate(IValidatable<String> validatable) {
					final String value = validatable.getValue();
					if (!value.endsWith("/") && !value.endsWith("#")) {
						error(validatable, "error.no-suffix");
					}
				}
			})
			.setRequired(true);
		
		addTextField("prefix", new PropertyModel(model, "prefix"), form)
			.setRequired(true);
		
		form.add(new RBStandardButton("create") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				getOrganizer().registerNamespace(model.getObject());
				send(getPage(), Broadcast.BUBBLE, new ModelChangeEvent(ModelChangeEvent.NAMESPACE));
				onSuccess(target, model.getObject());
				resetModel();
			}
			
		}.add(new DefaultButtonBehavior()));
		
		form.add(new RBCancelButton("cancel") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				onCancel(target);
				resetModel();
			}
			
		});
		
		add(form);
		
	}

	// ----------------------------------------------------
	
	public void onCancel(AjaxRequestTarget target){}
	
	public void onSuccess(AjaxRequestTarget target, NamespaceDeclaration decl){}
	
	// ----------------------------------------------------

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
		model.setObject(new NamespaceDeclaration());
	}
	
}
