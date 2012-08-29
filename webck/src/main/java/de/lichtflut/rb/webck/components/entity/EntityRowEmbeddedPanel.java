/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.and;
import static de.lichtflut.rb.webck.models.ConditionalModel.lessThan;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.components.form.RBSubmitLink;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.fields.FieldCardinalityModel;
import de.lichtflut.rb.webck.models.fields.FieldLabelModel;
import de.lichtflut.rb.webck.models.fields.FieldSizeModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValueModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValuesListModel;

/**
 * <p>
 *  Panel containing the fields of an embedded entity.
 * </p>
 *
 * <p>
 * 	Created Aug 22, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EntityRowEmbeddedPanel extends Panel {

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param model The model containing the field with the embedded entity.
	 */
	public EntityRowEmbeddedPanel(final String id, final IModel<RBField> model) {
		super(id, model);


	}

}
