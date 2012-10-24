/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
