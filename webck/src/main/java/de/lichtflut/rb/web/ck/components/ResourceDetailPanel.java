/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.HashSet;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.impl.RBFieldImpl;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.web.ck.behavior.CKBehavior;
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
public abstract class ResourceDetailPanel extends CKComponent  {

	private static final long serialVersionUID = 2891070130452612280L;
	private RBEntity entity;
	private String componentID;
	private boolean readOnly;

	// ---------- CONSTRUCTOR -------------------------------------

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param entity - Instance of {@link RBEntity}
	 */
	public ResourceDetailPanel(final String id, final RBEntity entity) {
		this(id, entity, true);
	}

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param entity - Instance of {@link RBEntity}
	 * @param readOnly - true if this form is readOnly, false if it should accept input.
	 */
	public ResourceDetailPanel(final String id, final RBEntity entity, final boolean readOnly) {
		super(id);
		this.componentID = id;
		this.entity = entity;
		this.readOnly = readOnly;
		this.buildComponent();
	}

	// ------------------------------------------------------------

	@SuppressWarnings("serial")
	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		if(readOnly){
			this.add(new Readable("container"));
		}else{
			this.add(new Writeable("container"){
				@Override
				public ServiceProvider getServiceProvider() {
					return ResourceDetailPanel.this.getServiceProvider();
				}
				@Override
				public CKComponent setViewMode(final ViewMode mode) {
					return null;
				}
			});
		}
	}

	// ------------------------------------------------------------

	/**
	 * Helperclass.
	 *
	 * Created: Aug 30, 2011
	 *
	 * @author Ravi Knox
	 */
	@SuppressWarnings("serial")
	class KeyValueField extends Panel{

		/**
		 * Constructor.
		 * @param id - wicket:id
		 * @param field - instance of {@link RBField}
		 */
		public KeyValueField(final String id, final RBField field){
			super(id);
			int index = (entity.getAllFields().size());
			WebMarkupContainer container = new WebMarkupContainer("container");
			index++;
			container.add(new TextField<String>("key", new NewGenericResourceModel<String>(field, index)));
			container.add(new TextField<String>("value", new NewGenericResourceModel<String>(field, index)));
			add(container);
		}

		/**
		 * Constructor.
		 * @param id - wicket:id
		 * @param entity - instance of {@link RBField}
		 */
		public KeyValueField(final String id, final RBEntity entity){
			super(id);
			WebMarkupContainer container = new WebMarkupContainer("container");
			final TextField<String> predicate = new TextField<String>("key", Model.of(""));
			final TextField<String> value = new TextField<String>("value", Model.of(""));
			predicate.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

				@Override
				protected void onUpdate(final AjaxRequestTarget target) {
					if(!value.getModelObject().equals("")){
						addRBField(predicate.getModelObject(), value.getModelObject());
					}
				}
			});
			value.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

				@Override
				protected void onUpdate(final AjaxRequestTarget target) {
					if(!value.getModelObject().equals("")){
						addRBField(predicate.getModelObject(), value.getModelObject());

					}
				}
			});
			container.add(predicate);
			container.add(value);
			add(container);
		}

		/**
		 * Add an {@link RBField} to the existing {@link RBEntity}.
		 * @param predicate - Predicate of the new {@link RBField}
		 * @param value - Value of the new {@link RBField}
		 */
		private void addRBField(final String predicate, final String value){
			HashSet<SemanticNode> values = new HashSet<SemanticNode>();
			values.add(new SNValue(ElementaryDataType.STRING, value));
			RBField field = new RBFieldImpl(new SimpleResourceID(predicate), values);
			entity.addField(field);
		}
	}

	/**
	 *
	 * [TODO Insert description here.
	 *
	 * Created: Sep 11, 2011
	 *
	 * @author Ravi Knox
	 */
	class Readable extends Panel{

		private static final long serialVersionUID = 7498726777908211739L;


		// ------------------------------------------------------------

		/**
		 * Constructor.
		 * @param id - wicket:id
		 */
		@SuppressWarnings("serial")
		public Readable(final String id){
			super(id);
			this.add(new ResourceInfoPanel("infoPanel", entity));

			ListView<RBField> view = new ListView<RBField>("details", entity.getAllFields()) {
				@Override
				protected void populateItem(final ListItem<RBField> item) {
					RBField field = item.getModelObject();
					RepeatingView valueList = new RepeatingView("valueList");
					if (field.isResourceReference()) {
						addResourceField(valueList, field);
					} else {
						String value = "";
						for (Object o : field.getValues()) {
							if(o != null){
								value = value.concat(o.toString() + ", ");
							}else{
								value = value.concat("");
							}
						}
						if (value.length() > 0) {
							value = value.substring(0, value.length() - 2);
						}
						valueList.add(new Label(valueList.newChildId(), value));
					}
					item.add(new Label("label", field.getLabel()));
					item.add(valueList);
				}
			};
			Link<String> link = new Link<String>("editLink"){

				@Override
				public void onClick() {
					ResourceDetailPanel.this.replaceWith(new ResourceDetailPanel(componentID, entity, false) {
						@Override
						public CKComponent setViewMode(final ViewMode mode) {
							return null;
						}
						@Override
						public ServiceProvider getServiceProvider() {
							return ResourceDetailPanel.this.getServiceProvider();
						}
					});
				}
			};
			link.add(new Label("label", "Edit"));
			this.add(view);
			this.add(link);
		}

		/**
		 * @param valueList -
		 * @param field - instance of {@link RBField}
		 */
		private void addResourceField(final RepeatingView valueList, final RBField field) {
			if(field.getValues().isEmpty() || field.getValues() == null){
				valueList.add(new Label(valueList.newChildId(), ""));
			}
			for (Object o : field.getValues()) {
				if (o != null) {
				final RBEntity e = (RBEntity) o;
					valueList.add(createLinkForEntity(valueList, e));
				} else {
					valueList.add(new Label(valueList.newChildId(), ""));
				}
			}
		}

		/**
		 * @param valueList -
		 * @param e - {@link RBEntity} for which a {@link CKLink} will be created
		 * @return a {@link CKLink} for the given {@link RBEntity}
		 */
		@SuppressWarnings("serial")
		private CKLink createLinkForEntity(final RepeatingView valueList, final RBEntity e) {
			CKLink link = new CKLink(valueList.newChildId(), e.getLabel(), CKLinkType.CUSTOM_BEHAVIOR);
			link.addBehavior(CKLink.ON_LINK_CLICK_BEHAVIOR, new CKBehavior() {
				@Override
				public Object execute(final Object... objects) {
					ResourceDetailPanel.this.replaceWith(new ResourceDetailPanel(componentID, e) {
						@Override
						public CKComponent setViewMode(final ViewMode mode) {
							return null;
						}
						@Override
						public ServiceProvider getServiceProvider() {
							return ResourceDetailPanel.this.getServiceProvider();
						}
					});
					return null;
				}
			});
			return link;
		}
	}
	/**
	 *
	 * [TODO Insert description here.
	 *
	 * Created: Sep 11, 2011
	 *
	 * @author Ravi Knox
	 */
	abstract class Writeable extends CKComponent {

		private static final long serialVersionUID = 8741545512115763711L;

		// ------------------------------------------------------------

		/**
		 * Constructor.
		 * @param id - wicket:id
		 */
		public Writeable(final String id) {
			super(id);
			buildComponent();
		}

		// ------------------------------------------------------------

		@SuppressWarnings("serial")
		@Override
		protected void initComponent(final CKValueWrapperModel model){
			final RepeatingView view = new RepeatingView("fieldItem");
			this.setOutputMarkupId(true);
			this.add(new ResourceInfoPanel("infoPanel", entity));
			this.add(new FeedbackPanel("feedbackPanel"));
			@SuppressWarnings("rawtypes")
			Form form = new Form("form") {
				@Override
				protected void onSubmit() {
					getServiceProvider().getEntityManager().store(entity);
				}
			};
			for (RBField field : entity.getAllFields()) {
				view.add(new CKFormRowItem(view.newChildId(), field) {
					@Override
					public ServiceProvider getServiceProvider() {
						return ResourceDetailPanel.this.getServiceProvider();
					}

					@Override
					public CKComponent setViewMode(final ViewMode view) {
						return null;
					}
				});
			}
			form.add(new AjaxButton("addKeyValue") {
				@Override
				protected void onSubmit(final AjaxRequestTarget target,
						final Form<?> form) {
					view.add(new KeyValueField(view.newChildId(), entity));
					form.add(view);
					target.add(form);
				}

				@Override
				protected void onError(final AjaxRequestTarget target,
						final Form<?> form) {
					onSubmit(target, form);
				}
			});
			form.add(view);
			this.add(form);
		}
	}
}
