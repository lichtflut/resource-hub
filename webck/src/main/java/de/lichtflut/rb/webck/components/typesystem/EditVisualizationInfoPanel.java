/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import de.lichtflut.rb.core.schema.model.VisualizationInfo;

/**
 * <p>
 * Panel for editing {@link VisualizationInfo}.
 * </p>
 * Created: Sep 17, 2012
 *
 * @author Ravi Knox
 */
public class EditVisualizationInfoPanel extends Panel {

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param model
	 */
	public EditVisualizationInfoPanel(final String id, final IModel<PropertyRow> model) {
		super(id, model);
		nullCheck(model);
		Form<?> form = new Form<Void>("form");
		addFields(form, model);
		add(form);
	}

	// ------------------------------------------------------

	private void addFields(final Form<?> form, final IModel<PropertyRow> model) {
		form.add(new CheckBox("embedded", new PropertyModel<Boolean>(model, "embedded")));
		form.add(new CheckBox("floating", new PropertyModel<Boolean>(model, "floating")));
		TextArea<String> textArea = new TextArea<String>("style", new PropertyModel<String>(model, "style"));
		form.add(textArea);
	}

	private void nullCheck(final IModel<PropertyRow> model) {
		if(null == model.getObject().getVisualizationInfo()){
			VisualizationInfo info = VisualizationInfo.DEFAULT;
			model.getObject().setVisualizationInfo(info);
		}

	}

}
