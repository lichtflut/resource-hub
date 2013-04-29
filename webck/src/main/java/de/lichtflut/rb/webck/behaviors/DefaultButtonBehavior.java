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
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;

/**
 * <p>
 *  Makes a component the default behavior during configuration phase.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class DefaultButtonBehavior extends Behavior {

	@Override
	public void onConfigure(Component component) {
		super.onConfigure(component);
		if (component.isVisibleInHierarchy()) {
			final Form<?> form = component.findParent(Form.class);
            if (form == null) {
                throwNotPlacedInFormException(component);
            }
			form.setDefaultButton((IFormSubmittingComponent) component);
		}

	}

    // ----------------------------------------------------

    private void throwNotPlacedInFormException(Component component) {
        throw new IllegalStateException("A 'DefaultButtonBehavior' has been added to a component that is not" +
                " placed beneath a form: " + component);

    }


}
