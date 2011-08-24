/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.fields;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;

import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.ck.components.CKComponent;

/**
 * [TODO Insert description here.
 *
 * Created: Aug 22, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
abstract class AddValueAjaxButton extends CKComponent {

	private IRBField field;
	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param field -
	 */
	public AddValueAjaxButton(final String id, final IRBField field) {
		super(id);
		this.field = field;
		initComponent(null);
	}

	/**
	 * Check if visibility is allowed.
	 * @return True if Cardinality MAX > field.size
	 */
	private boolean checkVisibility() {
		if((field.getCardinality().getMaxOccurs()) > (field.getFieldValues().size())){
			return true;
		}
		return false;
	}

	/**
	 * TODO Comment.
	 * @param target -
	 * @param form -
	 */
	public abstract void addField(final AjaxRequestTarget target, final Form<?> form);

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		setOutputMarkupId(true);
		AjaxButton button = new  AjaxButton("addField") {
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				addFieldAndCheckVisibility(target, form);
			}

			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				addFieldAndCheckVisibility(target, form);
			}

			private void addFieldAndCheckVisibility(final AjaxRequestTarget target, final Form<?> form) {
				addField(target, form);
				this.setVisible(checkVisibility());
			}

		};

		add(button);
		button.setVisible(checkVisibility());
		button.add(new Label("linkLabel", "(+)"));
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
