/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNTimeSpec;

import de.lichtflut.rb.webck.behaviors.DatePickerBehavior;
import de.lichtflut.rb.webck.models.SemanticAttributeModel;

/**
 * <p>
 *  Special text field for objects of type {@link SNTimeSpec}.
 * </p>
 *
 * <p>
 * 	Created May 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class SNTimeSpecField extends TextField<SNTimeSpec> {

	/**
	 * @param id /
	 */
	public SNTimeSpecField(final String id) {
		super(id, SNTimeSpec.class);
	}


	/**
	 * @param id /
	 * @param model /
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SNTimeSpecField(final String id, final IModel model) {
		super(id, model, SNTimeSpec.class);
	}

	/**
	 * @param id /
	 * @param predicate /
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SNTimeSpecField(final String id, final ResourceID predicate) {
		super(id, new SemanticAttributeModel(predicate), SNTimeSpec.class);
	}

	// -----------------------------------------------------

	/* (non-Javadoc)
	 * @see org.apache.wicket.Component#onInitialize()
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new DatePickerBehavior());
	}

}
