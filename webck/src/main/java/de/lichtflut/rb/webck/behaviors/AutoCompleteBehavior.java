/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.webck.resources.SharedResourceProvider;

/**
 * <p>
 *  [DESCRIPTION].
 * </p>
 *
 * <p>
 * 	Created May 30, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class AutoCompleteBehavior extends AbstractDefaultAjaxBehavior {

	/**
	 *
	 */
	public AutoCompleteBehavior() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#onBind()
	 */
	@Override
	protected void onBind() {

		getCallbackUrl();

	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(final Component c, final IHeaderResponse response) {
		super.renderHead(c, response);
		final SharedResourceProvider resourceProvider = new SharedResourceProvider();
		response.renderJavaScriptReference(resourceProvider.getJQueryCore());
		response.renderJavaScriptReference(resourceProvider.getJQueryUI());
	}


	/* (non-Javadoc)
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected void respond(final AjaxRequestTarget target) {
		throw new NotYetImplementedException();
	}


}
