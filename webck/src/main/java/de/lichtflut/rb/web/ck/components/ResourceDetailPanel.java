/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.HashSet;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNEntity;

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.impl.RBField;
import de.lichtflut.rb.core.spi.INewRBServiceProvider;
import de.lichtflut.rb.web.ck.components.fields.CKFormRowItem;
import de.lichtflut.rb.web.models.NewGenericResourceModel;

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
		this.setOutputMarkupId(true);
		this.add(new FeedbackPanel("feedbackPanel"));
		Form form = new Form("form") {
			@Override
			protected void onSubmit() {
				getServiceProvider().getRBEntityManagement().store(entity);
			}
		};
		final RepeatingView view = new RepeatingView("fieldItem");
		for (IRBField field : entity.getAllFields()) {
			view.add(new CKFormRowItem(view.newChildId(), field){
				@Override
				public INewRBServiceProvider getServiceProvider() {
					return ResourceDetailPanel.this.getServiceProvider();
				}
				@Override
				public CKComponent setViewMode(final ViewMode mode) {return null;}
			});
		}
		form.add(new AjaxButton("addKeyValue") {
			@Override
			protected void onSubmit(final AjaxRequestTarget target,final Form<?> form) {
				final SNEntity snEntity = new SNEntity();
				IRBField field = new RBField((PropertyAssertion)null, new HashSet<SemanticNode>(){{add(snEntity);}});
				entity.addField(field);
				view.add(new KeyValueField(view.newChildId(), field));
				form.add(view);
				target.add(form);
			}
			@Override
			protected void onError(final AjaxRequestTarget target,final  Form<?> form) {
				onSubmit(target, form);
			}
		});
		form.add(view);
		this.add(form);

	}

	/**
	 * Helperclass.
	 *
	 * Created: Aug 30, 2011
	 *
	 * @author Ravi Knox
	 */
	class KeyValueField extends Panel{

		/**
		 * Constructor.
		 * @param id - wicket:id
		 * @param field - instance of {@link IRBField}
		 */
		public KeyValueField(final String id, final IRBField field){
			super(id);
			int index = (entity.getAllFields().size());
			WebMarkupContainer container = new WebMarkupContainer("container");
			index++;
			container.add(new TextField<String>("key", new NewGenericResourceModel<String>(field, index)));
			container.add(new TextField<String>("value", new NewGenericResourceModel<String>(field, index)));
			add(container);
		}
	}
}
