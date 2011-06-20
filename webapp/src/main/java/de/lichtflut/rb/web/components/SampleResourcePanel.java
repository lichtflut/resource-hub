/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.web.SampleResourcePage;
import de.lichtflut.rb.web.components.fields.SNTimeSpecField;
import de.lichtflut.rb.web.components.fields.SNTextField;
import de.lichtflut.rb.web.models.ResourceNodeModel;

/**
 * <p>
 *  [DESCRIPTION].
 * </p>
 *
 * <p>
 * 	Created May 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class SampleResourcePanel extends Panel {

	/**
	 * Constructor.
	 * @param id The Component ID.
	 * @param model A model containing a Resource Node.
	 */
	@SuppressWarnings("rawtypes")
	public SampleResourcePanel(final String id, final IModel<ResourceNode> model) {
		super(id, new ResourceNodeModel<ResourceNode>(model));
		final Form form = new Form("form");

		form.add(new FeedbackPanel("feedback"));

		form.add(new SNTextField("forename", Aras.HAS_FORENAME));
		form.add(new SNTextField("surname", Aras.HAS_SURNAME));
		form.add(new SNTimeSpecField("birthdate", SampleResourcePage.HAS_BIRTHDATE));

		form.add(new Button("save") {
			public void onSubmit() {
				info("Associations: " + model.getObject().getAssociations());
			};
		});

		add(form);
	}

}
