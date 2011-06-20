/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.fields;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNText;

import de.lichtflut.rb.web.models.SemanticAttributeModel;

/**
 * <p>
 *  Special text field for objects of type {@link SNText}.
 * </p>
 *
 * <p>
 * 	Created May 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class SNTextField extends TextField<SNText> {

	/**
	 * @param id /
	 */
	public SNTextField(final String id) {
		super(id, SNText.class);
	}


	/**
	 * @param id /
	 * @param model /
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SNTextField(final String id, final IModel model) {
		super(id, model, SNText.class);
	}

	/**
	 * @param id /
	 * @param predicate /
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SNTextField(final String id, final ResourceID predicate) {
		super(id, new SemanticAttributeModel(predicate), SNText.class);
	}

}
