/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.schema;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 * This Component lists a ResourceSchema's properties.
 * </p>
 * Created: Nov 9, 2012
 *
 * @author Ravi Knox
 */
public class SchemaPropertyDetailListPanel extends Panel {

	/**
	 * Constructor.
	 * @param id
	 * @param model
	 */
	public SchemaPropertyDetailListPanel(final String id, final IModel<ResourceSchema> model) {
		super(id, model);
		// TODO Auto-generated constructor stub
	}

}
