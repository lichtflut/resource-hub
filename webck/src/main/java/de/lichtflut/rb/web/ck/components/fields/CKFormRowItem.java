/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.fields;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import de.lichtflut.rb.core.schema.model.IRBField;

/**
 * A RBFieldPanel represents a {@link IRBField}.
 *
 * Created: Aug 16, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class CKFormRowItem extends Panel {

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param field - instance of {@link IRBField}
	 */
	public CKFormRowItem(final String id, final IRBField field) {
		super(id);
		this.setOutputMarkupId(true);
		// Check if minOccur is one. If yes append '*'
		if(field.getCardinality().getMinOccurs() == 1){
			Label label = new Label("label", field.getLabel() + " <span class=\"required\">*</span>");
			add(label.setEscapeModelStrings(false));
		}else{
			add(new Label("label", field.getLabel()));
		}
		// Add appropriate Input-Field
		switch(field.getDataType()){
		case BOOLEAN:
			add(new CKCheckBoxField("field", field));
			break;
		case RESOURCE:
//			add(new CKDropDownChoice("field", field));
			add(new ResourceField("field", field));
			break;
		default:
			add(new CKTextField("field", field));
			break;
		}
	}

}
