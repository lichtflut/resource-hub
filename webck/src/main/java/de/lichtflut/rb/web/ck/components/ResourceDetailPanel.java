/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.web.ck.components.CKComponent.CKValueWrapperModel;
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
public abstract class ResourceDetailPanel extends CKComponent  {

	private IRBEntity entity;
	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param entity - Instance of {@link IRBEntity}
	 */
	public ResourceDetailPanel(final String id, final IRBEntity entity) {
		super(id);
		this.entity = entity;
		buildComponent();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		setOutputMarkupId(true);
		add(new FeedbackPanel("feedbackPanel"));
		Form form = new Form("form") {
			@Override
			protected void onSubmit() {
				// Do nothing
			}
		};
		RepeatingView view = new RepeatingView("field-item");
		for (IRBField field : entity.getAllFields()) {
			view.add(new CKFormRowItem(view.newChildId(), field));
		}
		form.add(view);
		add(form);

	}
}
