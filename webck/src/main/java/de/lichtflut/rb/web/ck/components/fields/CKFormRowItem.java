/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.fields;

import org.apache.wicket.markup.html.basic.Label;

import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.spi.INewRBServiceProvider;
import de.lichtflut.rb.web.ck.components.CKComponent;

/**
 * A RBFieldPanel represents a {@link IRBField}.
 *
 * Created: Aug 16, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public abstract class CKFormRowItem extends CKComponent {

	private IRBField field;
	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param field - instance of {@link IRBField}
	 */
	public CKFormRowItem(final String id, final IRBField field) {
		super(id);
		this.field = field;
		buildComponent();
	}


	@Override
	protected void initComponent(final CKValueWrapperModel model) {
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
			add(new ResourceField("field", field){
				@Override
				public INewRBServiceProvider getServiceProvider() {
					return CKFormRowItem.this.getServiceProvider();
				}
				@Override
				public CKComponent setViewMode(final ViewMode mode) {return null;}
			});
			break;
		default:
			add(new CKTextField("field", field));
			break;
		}
	}

}
