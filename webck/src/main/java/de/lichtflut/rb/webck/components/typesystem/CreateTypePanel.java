/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import java.util.ArrayList;
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
import org.arastreju.sge.naming.SimpleNamespace;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.webck.components.ComponentFactory;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Sep 22, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class CreateTypePanel extends Panel {

	private IModel<Namespace> nsModel;
	private IModel<String> nameModel;

	/**
	 * Constructor.
	 * @param id
	 * @param model
	 */
	@SuppressWarnings("rawtypes")
	public CreateTypePanel(final String id) {
		super(id);
		
		final Form form = new Form("form"); 
		form.setOutputMarkupId(true);
		form.add(new FeedbackPanel("feedback"));
		
		final List<Namespace> nsList = new ArrayList<Namespace>();
		nsList.add(new SimpleNamespace(RB.NAMESPACE_URI));
		
		nsModel = new Model<Namespace>();
		nameModel = new Model<String>();
		
		ComponentFactory.addDropDownChoice("nsChoice", form, nsModel, nsList)
			.setRequired(true);
		ComponentFactory.addTextField("name", nameModel, form)
			.setRequired(true);
		
		form.add(new AjaxFallbackButton("save", form) {
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onCreate(new QualifiedName(nsModel.getObject(), nameModel.getObject()), target);
				nsModel.setObject(null);
				nameModel.setObject(null);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		});
		
		add(form);
	}
	
	// -----------------------------------------------------
	
	public abstract void onCreate(final QualifiedName qn, final AjaxRequestTarget target);

}
