/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.ck.components.fields.CKFormRowItem;

/**
 * <p>
 * This {@link CKComponent} displays details of a RBentity.
 * </p>
 * <p>
 * Changes to the RBEntiy can be made by using this Panel as well.
 * </p>
 *
 * Created: Aug 16, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public abstract class GenericResourceDetailPanel extends CKComponent  {

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param entity - Instance of {@link IRBEntity}
	 */
	@SuppressWarnings("rawtypes")
	public GenericResourceDetailPanel(final String id, final IRBEntity entity) {
		super(id);
		add(new FeedbackPanel("feedbackPanel"));
		Form form = new Form("form"){
		@Override
		protected void onSubmit() {
			super.onSubmit();
			getParent().replaceWith(new GenericResourceDetailPanel(id, entity) {
				@Override
				public CKComponent setViewMode(final ViewMode mode) {
					// TODO Auto-generated method stub
					return null;
				}
				@Override
				protected void initComponent(final CKValueWrapperModel model) {
					// TODO Auto-generated method stub
				}
				@Override
				public RBServiceProvider getServiceProvider() {
					// TODO Auto-generated method stub
					return null;
				}
			});
		}
	};
		RepeatingView view = new RepeatingView("field-item");
		for (IRBField field : entity.getAllFields()) {
			Fragment f = new Fragment(view.newChildId(), "field", this);
			f.add(new CKFormRowItem("row", field));
			view.add(f);
		}
		form.add(view);
		add(form);
	}

}
