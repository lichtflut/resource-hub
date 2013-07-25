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
package de.lichtflut.rb.webck.components.fields;

import de.lichtflut.rb.core.entity.RBEntity;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.JQueryResourceReference;
import org.arastreju.sge.model.ResourceID;

import java.io.Serializable;
import java.util.Collections;

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
	
	public static final ResourceReference REF =
            new JavaScriptResourceReference(DataPickerField.class, "lfrb-datapicker.js") {
                @Override
                public Iterable<? extends HeaderItem> getDependencies() {
                    return Collections.<HeaderItem>singleton(
                            JavaScriptHeaderItem.forReference(JQueryResourceReference.get()));
                }
            };

    private final IModel<String> source = new Model<String>();

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
		add(hidden);
		
		final TextField<String> display = new TextField<String>("display", displayModel);
		display.setType(String.class);
		display.setOutputMarkupId(true);
        display.add(new AttributeModifier("lfrb-source", source));
		add(display);
		
		add(new AttributeModifier("title", hiddenModel));
		add(new PickerSuggestLink("suggest", display));
	}
	
	// -----------------------------------------------------
	
	public String getDisplayValue() {
		return getDisplayComponent().getDefaultModelObjectAsString();
	}
	
	public TextField getDisplayComponent() {
		return (TextField) get("display");
	}
	
	public TextField<ResourceID> getValueComponent() {
		return (TextField<ResourceID>) get("acValue");
	}
	
	public PickerSuggestLink getSuggestLink() {
		return (PickerSuggestLink) get("suggest");
	}
	
    public DataPickerField setSource(String source) {
        this.source.setObject(source);
        return this;
    }

	// ----------------------------------------------------
	
	@Override
	public FormComponent<T> setType(Class<?> type) {
		final FormComponent<T> comp = getValueField();
		if (comp != null) {
			comp.setType(type);
		}
		return super.setType(type);
	}
	
	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(REF));
        response.render(OnDomReadyHeaderItem.forScript("LFRB.Datapicker.initAllDatapickers()"));
	}

	// -----------------------------------------------------
	
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
