/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.naming.Namespace;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.behaviors.DefaultButtonBehavior;
import de.lichtflut.rb.webck.components.ComponentFactory;

/**
 * <p>
 *  Panel for selecting/editing of a new Resource.
 * </p>
 *
 * <p>
 * 	Created Sep 22, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class CreateResourcePanel extends Panel {

	private IModel<Namespace> nsModel;
	private IModel<String> nameModel;
	
	// -----------------------------------------------------

	/**
	 * Constructor.
	 * @param id
	 * @param model
	 */
	@SuppressWarnings("rawtypes")
	public CreateResourcePanel(final String id) {
		super(id);
		
		final Form form = new Form("form"); 
		form.setOutputMarkupId(true);
		form.add(new FeedbackPanel("feedback"));
		
		nsModel = new Model<Namespace>();
		nameModel = new Model<String>();
		
		ComponentFactory.addDropDownChoice("nsChoice", form, nsModel, getAvailableNamespaces())
			.setRequired(true);
		ComponentFactory.addTextField("name", nameModel, form)
			.setRequired(true);
		
		form.add(new AjaxFallbackButton("save", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onCreate(new QualifiedName(nsModel.getObject(), nameModel.getObject()), target);
				resetModel();
			}
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
				resetModel();
			}
		}.add(new DefaultButtonBehavior()));
		
		add(form);
	}
	
	// -----------------------------------------------------
	
	public abstract void onCreate(final QualifiedName qn, final AjaxRequestTarget target);
	
	public abstract ServiceProvider getServiceProvider();
	
	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected void onDetach() {
		super.onDetach();
		nsModel.detach();
		nameModel.detach();
	}
	
	protected void resetModel() {
		nsModel.setObject(null);
		nameModel.setObject(null);
	}
	
	protected List<Namespace> getAvailableNamespaces() {
		return getServiceProvider().getDomainOrganizer().getNamespaces();
	}

}
