/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.fields;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.ck.components.CKComponent;

/**
 * This field displays a String in a simple {@link TextField}.
 *
 * Created: Aug 16, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
class CKStringField extends CKComponent {

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param field -
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CKStringField(final String id, final IRBField field) {
		super(id);
		setDefaultModel(new ListModel(field.getFieldValues()));
			add(new Label("label", new Model(field.getLabel())));
			add(createField("value", field));
	}

	/**
	 * Creates a {@link RepeatingView} containing a {@link Label} and a {@link TextField}.
	 * @param fieldName - Value set as Label
	 * @param field - value set as Textfield
	 * @return RepeatingView containing all the field's values
	 */
	private RepeatingView createField(final String fieldName, final IRBField field) {
		RepeatingView view = new RepeatingView("value");
		if(field.getFieldValues().size() == 0){
			Fragment f = new Fragment(view.newChildId(), "textInput", this);
			f.add(new TextField<String>("input", Model.of("")));
			view.add(f);
		}else{
			for (Object object : field.getFieldValues()) {
				Fragment f = new Fragment(view.newChildId(), "textInput", this);
				f.add(new TextField<String>("input", Model.of(object.toString())));
				view.add(f);
			}
		}
		return view;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RBServiceProvider getServiceProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CKComponent setViewMode(final ViewMode mode) {
		// TODO Auto-generated method stub
		return null;
	}

}
