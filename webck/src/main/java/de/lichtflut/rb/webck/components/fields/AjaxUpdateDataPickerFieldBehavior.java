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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * <p>
 *  Behavior that updates the data picker field's model when field is left.
 * </p>
 *
 * <p>
 * 	Created Apr 13, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class AjaxUpdateDataPickerFieldBehavior extends Behavior {
	
	private DataPickerField<?> picker;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public AjaxUpdateDataPickerFieldBehavior() {
	}
	
	// ----------------------------------------------------
	
	/**
	 * @param target
	 */
	public void onError(AjaxRequestTarget target) {
	}

	/**
	 * @param target
	 */
	public void onSubmit(AjaxRequestTarget target) {
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onConfigure(Component component) {
		super.onConfigure(component);
		picker = (DataPickerField<?>) component;
		
		ValueUpdateBehavior writeBackBehavior = new ValueUpdateBehavior();
		
		picker.getValueComponent().add(writeBackBehavior);
		
		String callbackUrl = writeBackBehavior.getCallbackUrl().toString();
		char separator = (callbackUrl != null && callbackUrl.indexOf('?') > -1) ? '&' : '?';
		
		String markupId = picker.getValueComponent().getMarkupId();

		final String saveCall = "{" +
				writeBackBehavior.generateCallbackScript("wicketAjaxGet('" + callbackUrl + separator +
				"save=true&'+document.getElementById('" + markupId +"').name+'='+" +
						"wicketEncode(document.getElementById('" + markupId +"').value)") + "; return false;}";

		final String cancelCall = "{" +
			writeBackBehavior.generateCallbackScript("wicketAjaxGet('" + callbackUrl + separator + "save=false'") +
			"; return false;}";
		
		picker.getDisplayComponent().add(new Behavior() {
			@Override
			public void onComponentTag(final Component comp, final ComponentTag tag) {
				super.onComponentTag(comp, tag);

				final String keypress = "var kc=wicketKeyCode(event); if (kc==27) " + cancelCall +
					" else if (kc!=13) { return true; } else " + saveCall;

				tag.put("onblur", saveCall);
				tag.put("onkeypress", "if (Wicket.Browser.isSafari()) { return; }; " + keypress);
				tag.put("onkeydown", "if (!Wicket.Browser.isSafari()) { return; }; " + keypress);
			}
		});
	}
	
	// ----------------------------------------------------
	
	private void beforeSubmit() {
		picker.convertInput();
		picker.updateModel();
	}
	
	private final class ValueUpdateBehavior extends AbstractDefaultAjaxBehavior {
		@Override
		protected void respond(AjaxRequestTarget target) {
			@SuppressWarnings("rawtypes")
			FormComponent formComp = (FormComponent) getComponent();
			
			RequestCycle requestCycle = RequestCycle.get();
			boolean save = requestCycle.getRequest()
				.getRequestParameters()
				.getParameterValue("save")
				.toBoolean(false);

			if (save) {
				formComp.processInput();
				if (formComp.isValid()) {
					beforeSubmit();
					onSubmit(target);
				} else {
					onError(target);
				}
			} else {
			}
		}

		@Override
		public CharSequence generateCallbackScript(CharSequence partialCall) {
			return super.generateCallbackScript(partialCall);
		}
	}

}