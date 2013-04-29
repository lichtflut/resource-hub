/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.components.typesystem;

import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.webck.behaviors.DefaultButtonBehavior;
import de.lichtflut.rb.webck.components.common.ComponentFactory;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.naming.Namespace;
import org.arastreju.sge.naming.QualifiedName;

import java.util.List;

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

	@SpringBean
	private DomainOrganizer domainOrganizer;
	
	private final IModel<Namespace> nsModel;
	private final IModel<String> nameModel;
	
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
				onCreate(new QualifiedName(nsModel.getObject().getUri(), nameModel.getObject()), target);
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
		return domainOrganizer.getNamespaces();
	}

}
