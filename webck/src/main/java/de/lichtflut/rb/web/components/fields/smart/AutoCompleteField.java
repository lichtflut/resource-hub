/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.fields.smart;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

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
 * @param <T> /
 */
@SuppressWarnings("serial")
public class AutoCompleteField<T> extends TextField<T> {

	/**
	 * @param id /
	 */
	public AutoCompleteField(final String id) {
		super(id);
	}

	/**
	 * @param id /
	 * @param type /
	 */
	public AutoCompleteField(final String id, final Class<T> type) {
		super(id, type);
	}

	/**
	 * @param id /
	 * @param model /
	 * @param type /
	 */
	public AutoCompleteField(final String id, final IModel<T> model, final Class<T> type) {
		super(id, model, type);
	}

	/**
	 * @param id /
	 * @param model /
	 */
	public AutoCompleteField(final String id, final IModel<T> model) {
		super(id, model);
	}

	// -----------------------------------------------------

	/* (non-Javadoc)
	 * @see org.apache.wicket.Component#onInitialize()
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize();
	}

}
