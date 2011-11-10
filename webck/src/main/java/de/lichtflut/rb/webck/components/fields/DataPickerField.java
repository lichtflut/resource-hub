/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.model.IModel;
import org.odlabs.wiquery.core.javascript.JsScopeContext;
import org.odlabs.wiquery.ui.autocomplete.Autocomplete;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteJavaScriptResourceReference;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;
import org.odlabs.wiquery.ui.core.JsScopeUiEvent;

/**
 * <p>
 *  Component for picking of data.
 * </p>
 *
 * <p>
 * 	Created May 30, 2011
 * </p>
 *
 * @author Oliver Tigges
 * @param <T> /
 */
@SuppressWarnings("serial")
public class DataPickerField<T extends Serializable> extends FormComponentPanel<T> {

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 * @param source The {@link AutocompleteSource}
	 */
	public DataPickerField(final String id, final IModel<T> model, final AutocompleteSource source) {
		this(id, model, toDisplayModel(model), source);
	}
	
	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model for the internal value.
	 * @param displayModel The model for the display value.
	 * @param source The {@link AutocompleteSource}
	 */
	public DataPickerField(final String id, final IModel<T> model, final IModel<String> displayModel, final AutocompleteSource source) {
		super(id, model);
		
		final IModel<T> hiddenModel = toHiddenModel(model);
		final HiddenField<T> hidden = new HiddenField<T>("acValue", hiddenModel);
		hidden.setOutputMarkupId(true);
		final String hiddenMarkupId = hidden.getMarkupId();
		add(hidden);
		
		final Autocomplete<String> display = new Autocomplete<String>("display", displayModel);
		display.setOutputMarkupId(true);
		final String displayMarkupID = display.getMarkupId();
		display.setSource(source);
		display.setSelectEvent(new JsScopeUiEvent() {
			protected void execute(final JsScopeContext ctx) {
				ctx.append("if (ui.item) {");
				ctx.append("  $('#" +  hiddenMarkupId + "').attr('value', ui.item.id);");
				ctx.append("  $('#" +  displayMarkupID + "').attr('value', ui.item.label);");
				ctx.append("} else { alert ('nothing selected')};");
				ctx.append("return false;");
			}
		});
		add(display);
		
		add(new AttributeModifier("title", hiddenModel));
	}
	
	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public FormComponent<T> setType(Class<?> type) {
		final FormComponent<T> comp = getValueField();
		if (comp != null) {
			comp.setType(type);
		}
		return super.setType(type);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavaScriptReference(AutocompleteJavaScriptResourceReference.get());
	}

	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	
	@Override
	protected void convertInput() {
		final FormComponent<T> comp = getValueField();
		setConvertedInput(comp.getConvertedInput());
	}
	
	@SuppressWarnings("unchecked")
	protected FormComponent<T> getValueField() {
		return (FormComponent<T>) get("acValue");
	}

	// -----------------------------------------------------
	
	/**
	 * @param model
	 * @return
	 */
	private static IModel<String> toDisplayModel(final IModel<?> model) {
		return new IModel<String>() {

			public void detach() {
			}

			public String getObject() {
				if (model.getObject() != null) {
					return model.getObject().toString();	
				} else {
					return "";
				}
			}

			public void setObject(String object) {
			}
		};
	}
	
	/**
	 * @param model
	 * @return
	 */
	private static <T> IModel<T> toHiddenModel(final IModel<T> model) {
		return new IModel<T>() {
			
			private T object;

			public void detach() {
			}

			public T getObject() {
				if (object != null) {
					return object;
				} else {
					return model.getObject();
				}
			}

			public void setObject(T object) {
				this.object = object;
			}
		};
	}

}
