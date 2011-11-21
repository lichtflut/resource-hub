/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.odlabs.wiquery.core.javascript.JsScope;
import org.odlabs.wiquery.core.javascript.JsScopeContext;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;

import de.lichtflut.rb.web.RBBasePage;
import de.lichtflut.rb.webck.components.fields.DataPickerField;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Oct 26, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ComponentsCatalogPage extends RBBasePage {

	/**
	 * Constructor.
	 */
	@SuppressWarnings("rawtypes")
	public ComponentsCatalogPage() {
		super("Components Catalogue");
		
		final IModel<String> acModel = new Model<String>();

		final Form form = new Form("form") {
			protected void onSubmit() {
				System.err.println("Form.onSubmit() " + acModel.getObject());	
			};
		};
		form.add(new FeedbackPanel("feedback"));
		
		form.add(new DataPickerField<String>("autocomplete", acModel, new AutocompleteSource(new JsScope("term", "response") {
			protected void execute(JsScopeContext ctx) {
				ctx.append("response([" +
						"{label: 'Jack Sparrow (Pirates of the Carribean)', value:'Capt. Jack Sparrow', id:'poc:jack_sparrow'}, " +
						"{label: 'Le Chuck (Monkey Island)', value: 'Le Chuck', id:'monkey-island:le_chuck'}" +
						"])");
			}
		})));
		
		form.add(new Label("autocompleteValue", acModel));
		
		form.add(new Button("submit") { 	
			public void onSubmit() {
				System.err.println("Form.onSubmit() " + acModel.getObject());	
			};
		});
		
		add(form);
		
	}
	
}
