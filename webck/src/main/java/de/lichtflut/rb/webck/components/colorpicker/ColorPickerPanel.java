/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.colorpicker;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.head.CssContentHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ILabelProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * <p>
 * This Panel Provides a JQuery Colorpicker.
 * </p>
 * Created: Jan 18, 2013
 *
 * @author Ravi Knox
 */
public class ColorPickerPanel extends Panel implements ILabelProvider<String> {

	private final JavaScriptResourceReference reference = new JavaScriptResourceReference(ColorPickerPanel.class, "jquery.colorpicker.js");
	private final CssResourceReference cssResourceReference = new CssResourceReference(ColorPickerPanel.class, "jquery.colorpicker.css");

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * @param id Component id
	 * @param model
	 */
	public ColorPickerPanel(final String id, final IModel<String> model) {
		super(id, model);

		addDDC("colors", model);

		setOutputMarkupId(true);
	}

	// ------------------------------------------------------

	/**
	 * Provides a List of colors.
	 * @return
	 */
	protected List<String> getColors() {
		return getDefaultColors();
	}

	// ------------------------------------------------------

	protected List<String> getDefaultColors() {
		List<String> colors = new ArrayList<String>();
		colors.add("FFBFBF");
		colors.add("FF3F3F");
		colors.add("FF0000");
		colors.add("bf0000");
		colors.add("3F0000");

		colors.add("FFFEBF");
		colors.add("FFFE3F");
		colors.add("FFFE00");
		colors.add("BFBE00");
		colors.add("3F3F00");

		colors.add("BFFFBF");
		colors.add("3FFF3F");
		colors.add("00FF00");
		colors.add("00BF00");
		colors.add("003F00");

		colors.add("BFBFFF");
		colors.add("3F3FFF");
		colors.add("0000FF");
		colors.add("0000BF");
		colors.add("00003F");

		colors.add("FFFFFF");
		colors.add("BFBFBF");
		colors.add("7F7F7F");
		colors.add("262626");
		colors.add("000000");
		return colors;
	}

	private void addDDC(final String id, final IModel<String> model) {
		DropDownChoice<String> ddc = new DropDownChoice<String>(id, model, getColors(), getRenderer());
		ddc.setOutputMarkupId(true);
		add(ddc);
	}

	private IChoiceRenderer<String> getRenderer() {
		IChoiceRenderer<String> renderer = new ChoiceRenderer<String>(){
			@Override
			public String getIdValue(final String object, final int index) {
				return object;
			}
		};
		return renderer;
	}

	// ------------------------------------------------------


	@Override
	public void renderHead(final IHeaderResponse response) {
		response.render(CssContentHeaderItem.forReference(cssResourceReference));
		response.render(JavaScriptHeaderItem.forReference(reference));
		response.render(OnLoadHeaderItem.forScript("$(document).ready(function() {" +
				"$('#" + get("colors").getMarkupId() + "').colorpicker({" +
				"size: 23, label: '', count: 5, hide: true" +
				"});" +
				"});"));
	}

	@Override
	public IModel<String> getLabel() {
		return Model.of(getId());
	}
}
