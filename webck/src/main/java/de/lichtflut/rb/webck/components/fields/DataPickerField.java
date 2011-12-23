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

import de.lichtflut.rb.core.entity.RBEntity;

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
		display.setType(String.class);
		display.setOutputMarkupId(true);
		final String displayMarkupID = display.getMarkupId();
		display.setSource(source);
		display.setSearchEvent(new JsScopeUiEvent() {
			protected void execute(final JsScopeContext ctx) {
				ctx.append("$('#" +  displayMarkupID + "').removeClass('status-error').addClass('status-warning');");
			}
		});
		display.setSelectEvent(new JsScopeUiEvent() {
			protected void execute(final JsScopeContext ctx) {
				ctx.append("if (ui.item) {");
				ctx.append("  $('#" +  hiddenMarkupId + "').attr('value', ui.item.id);");
				ctx.append("  $('#" +  displayMarkupID + "').attr('value', ui.item.label)" +
						".removeClass('status-error').removeClass('status-warning');");
				ctx.append("} else { alert ('internal error nothing selected')};");
				ctx.append("return false;");
			}
		});
		display.setChangeEvent(new JsScopeUiEvent() {
			protected void execute(final JsScopeContext ctx) {
				ctx.append("if ($('#" +  displayMarkupID + "').hasClass('status-warning')) { " +
					       "    $('#" +  displayMarkupID + "').addClass('status-error');" +
						   "} ");
			}
		});
		add(display);
		
		add(new AttributeModifier("title", hiddenModel));
		
		add(new PickerSuggestLink("suggest", display));
	}
	
	// -----------------------------------------------------
	
	public String getDisplayValue() {
		return getDisplayComponent().getDefaultModelObjectAsString();
	}
	
	@SuppressWarnings("rawtypes")
	public Autocomplete getDisplayComponent() {
		return (Autocomplete) get("display");
	}
	
	public PickerSuggestLink getSuggestLink() {
		return (PickerSuggestLink) get("suggest");
	}
	
	@SuppressWarnings("rawtypes")
	public DataPickerField setSource(AutocompleteSource source) {
		getDisplayComponent().setSource(source);
		return this;
	}
	
	// ----------------------------------------------------
	
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
	
	@SuppressWarnings("unchecked")
	protected FormComponent<T> getDisplayField() {
		return (FormComponent<T>) get("display");
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
	 * @param originalModel
	 * @return
	 */
	private static <T> IModel<T> toHiddenModel(final IModel<T> originalModel) {
		return new IModel<T>() {
			
			private T object;

			public void detach() {
			}

			@SuppressWarnings("unchecked")
			public T getObject() {
				final T original = originalModel.getObject();
				if (original == null) {
					// original has been reseted.
					object = null;
				}
				final T obj = object == null ? original : object;
				if (obj instanceof RBEntity) {
					return (T) ((RBEntity)obj).getID();
				} else {
					return obj;
				}
			}

			public void setObject(T object) {
				this.object = object;
			}
		};
	}

}
