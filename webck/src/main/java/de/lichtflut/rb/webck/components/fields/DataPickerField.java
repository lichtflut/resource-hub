/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import de.lichtflut.rb.core.entity.RBEntity;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.arastreju.sge.model.ResourceID;
import org.odlabs.wiquery.core.javascript.JsScopeContext;
import org.odlabs.wiquery.ui.autocomplete.Autocomplete;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteJavaScriptResourceReference;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;
import org.odlabs.wiquery.ui.core.JsScopeUiEvent;

import java.io.Serializable;

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
	
	public static final ResourceReference REF = new JavaScriptResourceReference(DataPickerField.class, "lfrb-datapicker.js");
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public DataPickerField(final String id, final IModel<T> model) {
		this(id, model, toDisplayModel(model));
	}
	
	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model for the internal value.
	 * @param displayModel The model for the display value.
	 */
	public DataPickerField(final String id, final IModel<T> model, final IModel<String> displayModel) {
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
		display.setSearchEvent(new JsScopeUiEvent() {
			protected void execute(final JsScopeContext ctx) {
				ctx.append("LFRB.Datapicker.onSearch('#" +  displayMarkupID + "');");
			}
		});
		display.setSelectEvent(new JsScopeUiEvent() {
			protected void execute(final JsScopeContext ctx) {
				ctx.append("LFRB.Datapicker.accept('#" +  hiddenMarkupId + "', '#" +  displayMarkupID + "', ui.item);"); 
			}
		});
		display.setChangeEvent(new JsScopeUiEvent() {
			protected void execute(final JsScopeContext ctx) {
				ctx.append("LFRB.Datapicker.onChange('#" +  displayMarkupID + "');");
			}
		});
		display.setOpenEvent(new JsScopeUiEvent() {
			protected void execute(final JsScopeContext ctx) {
				ctx.append("LFRB.Datapicker.onOpen('#" +  displayMarkupID + "');");
			}
		});
		display.setCloseEvent(new JsScopeUiEvent() {
			protected void execute(final JsScopeContext ctx) {
				ctx.append("LFRB.Datapicker.onClose('#" +  displayMarkupID + "');");
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
	
	@SuppressWarnings("unchecked")
	public TextField<ResourceID> getValueComponent() {
		return (TextField<ResourceID>) get("acValue");
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
		response.renderJavaScriptReference(REF);
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
