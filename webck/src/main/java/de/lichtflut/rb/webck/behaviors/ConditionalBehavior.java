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
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;

import de.lichtflut.rb.webck.models.ConditionalModel;

/**
 * <p>
 *  Behavior only active if condition is fulfilled.
 *  @see ConditionalModel
 * </p>
 *
 * <p>
 * 	Created Sep 29, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class ConditionalBehavior<T> extends Behavior {

	/**
	 * Apply the behavior.
	 */
	protected abstract void apply(Component component);

	// -----------------------------------------------------

	@Override
	public void onConfigure(final Component component) {
		super.onConfigure(component);
		apply(component);
	}

	// -----------------------------------------------------

	public static ConditionalBehavior<Boolean> visibleIf(final ConditionalModel<?> model) {
		return new ConditionalBehavior<Boolean>() {
			@Override
			protected void apply(final Component component) {
				component.setVisible(model.isFulfilled());
			}
		};
	}

	public static ConditionalBehavior<Boolean> requiredIf(final ConditionalModel<?> model) {
		return new ConditionalBehavior<Boolean>() {
			@Override
			protected void apply(final Component component) {
				if (component instanceof FormComponent) {
					final FormComponent<?> fc = (FormComponent<?>) component;
					fc.setRequired(model.isFulfilled());
				}
			}
		};
	}

	public static ConditionalBehavior<Boolean> enableIf(final ConditionalModel<?> model) {
		return new ConditionalBehavior<Boolean>() {
			@Override
			protected void apply(final Component component) {
				component.setEnabled(model.isFulfilled());
			}
		};
	}

	public static ConditionalBehavior<Boolean> defaultButtonIf(final ConditionalModel<?> model) {
		return new ConditionalBehavior<Boolean>() {
			@Override
			protected void apply(final Component component) {
				if (component.isVisibleInHierarchy() && model.isFulfilled()) {
					final Form<?> form = component.findParent(Form.class);
					form.setDefaultButton((IFormSubmittingComponent) component);
				}
			}
		};
	}

}
