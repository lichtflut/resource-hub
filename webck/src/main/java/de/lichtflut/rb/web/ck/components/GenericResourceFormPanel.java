/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.RBEntity;
import de.lichtflut.rb.core.schema.model.RBEntity.MetaDataKeys;
import de.lichtflut.rb.core.schema.model.RBInvalidAttributeException;
import de.lichtflut.rb.core.schema.model.RBInvalidValueException;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.web.behaviors.DatePickerBehavior;
import de.lichtflut.rb.web.components.validators.GenericResourceValidator;
import de.lichtflut.rb.web.models.GenericResourceModel;

/**
 * <p>
 * This Form Panel is used to show or edit {@link RBEntity}s.
 * Its components will be created according to a given {@link ResourceSchema}.
 * </p>
 *
 * <p>
 * Created May 6, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public abstract class GenericResourceFormPanel extends CKComponent {

	private ResourceSchema schema;
	private RBEntity instance;

	// ------------------------------------------------------------

	/**
	 * Constrcutor.
	 * @param id - {@link CKComponent} ID
	 * @param schema - {@link ResourceSchema}
	 * @param instance - {@link RBEntity} Instance
	 */
	public GenericResourceFormPanel(final String id,
			final ResourceSchema schema, final RBEntity instance) {
		super(id);
		this.schema=schema;
		this.instance = instance;
		this.buildComponent();
	}

	// -----------------------------------------------------

	/**
	 * Initializes this {@link CKComponent}.
	 * @param schema - {@link ResourceSchema}
	 * @param in - {@link RBEntity}
	 */
	private void init(final ResourceSchema schema, final RBEntity in) {

		final RBEntity instance = (in == null ? schema.generateRBEntity() : in);
		final Form form = new Form("form") {
			@Override
			protected void onSubmit() {
				super.onSubmit();
				if (getServiceProvider().getRBEntityManagement()
						.createOrUpdateRBEntity(instance)) {
					info("This instance has been successfully updated/created");
				} else {
					error("Somethin went wrong, instance could'nt be created/updated");
				}
				// Here should be a redirect or something like that
			}
		};
		form.setOutputMarkupId(true);
		this.add(form);
		this.add(new FeedbackPanel("feedback").setEscapeModelStrings(false));

		if (schema != null) {
			final RepeatingView view = new RepeatingView("propertylist");
			for (final String attribute : (Collection<String>) instance
					.getAttributeNames()) {
				boolean required = true;
				int minimum_cnt = (Integer) instance.getMetaInfoFor(attribute,
						MetaDataKeys.MIN);
				if (minimum_cnt == 0) {
					minimum_cnt = 1;
					required = false;
				}
				GenericResourceModel model;
				// Get predefined tickets, if there are some
				Collection<Integer> tickets = instance.getTicketsFor(attribute);
				if(tickets.size() > minimum_cnt){
					minimum_cnt = tickets.size();
				}
				for (int cnt = 0; cnt < minimum_cnt; cnt++) {
					try {
						if (cnt >= (tickets.size())) {
							model = new GenericResourceModel<RBEntity>(instance,
									attribute);
						} else {
							model = new GenericResourceModel<RBEntity>(instance,
									attribute, (Integer) tickets.toArray()[cnt]);
						}
					} catch (IllegalArgumentException any) {
						// If something went wrong with model creation, skip
						// this field
						continue;
					}
					view.add(buildItem(instance, model, attribute, view,
							required, (cnt + 1) == minimum_cnt));
				}
			}
			form.add(view);
			// Add container for custom key-value pairs
			final RepeatingView nonSchemaView = new RepeatingView("customValues");
			AjaxButton button = new AjaxButton("customInput", Model.of("Add Vaules!")) {

				@Override
				protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
					// TODO: ADD GenericResourceModel parameter!!!!!!
					addNonSchemaFields(form, nonSchemaView, target, null);
				}

				@Override
				protected void onError(final AjaxRequestTarget target, final Form<?> form) {
					// TODO: ADD GenericResourceModel parameter!!!!!!
					addNonSchemaFields(form, nonSchemaView, target, null);
				}
			};
			form.add(nonSchemaView);
			form.add(button);
		}// End of if(schema==null)

	}// End of Method init

	// -----------------------------------------------------


	/**
	 * @param instance -
	 * @param model -
	 * @param attribute -
	 * @param view -
	 * @param required -
	 * @param expendable -
	 * @return fragment -
	 */
	private Component buildItem(final RBEntity instance,
			final GenericResourceModel model, final String attribute,
			final RepeatingView view, final boolean required,
			final boolean expendable) {
		final Fragment fragment = new Fragment(view.newChildId(), "referenceInput",
				this);
		final RepeatingView inputFields = new RepeatingView("propertyInput");
		fragment.add((new Label("propertyLabel", instance
				.getSimpleAttributeName(attribute) + (required ? " (*)" : ""))));
		// Decide which input-field should be used
		Fragment f = null;f = null;
		switch ((ElementaryDataType) instance.getMetaInfoFor(attribute,
				MetaDataKeys.TYPE)) {
		case RESOURCE:
			// TODO: Works only with one Resource Type reference so far...
			ResourceID uri = null;
			List<PropertyAssertion> propertyAssertionsList = (List) instance.getResourceSchema().getPropertyAssertions();
			for (PropertyAssertion assertion : propertyAssertionsList) {
				if(null!=assertion.getConstraints()){
					Set<Constraint> constraints =  assertion.getConstraints();
					for (Constraint constraint : constraints) {
						uri = constraint.getResourceTypeConstraint();
					}
				}
			}
			f = new Fragment("propertyInput", "resource", this);
			List entityList = (List) getServiceProvider().getRBEntityManagement()
				.loadAllRBEntitiesForSchema(getServiceProvider().getResourceSchemaManagement()
						.getResourceSchemaForResourceType(uri));

			IChoiceRenderer renderer = new IChoiceRenderer<RBEntity>() {
				@Override
				public Object getDisplayValue(final RBEntity object) {
					return object.getQualifiedName();
				}
				@Override
				public String getIdValue(final RBEntity object, final int index) {
					return object.getQualifiedName().toString();
				}
			};

			f.add(new DropDownChoice<RBEntity>("option", model, entityList, renderer){
				protected boolean wantOnSelectionChangedNotifications(){
					return false;
				}
				@Override
				protected void onSelectionChanged(final RBEntity entity){
					model.setObject(getServiceProvider().getRBEntityManagement()
							.loadRBEntity(entity.getQualifiedName()));
					}
			}.setType(RBEntity.class));
			inputFields.add(f);
			break;
		case BOOLEAN:
			f = new Fragment("propertyInput", "booleanInput", this);
			f.add(new CheckBox("input", model));
			inputFields.add(f);
			break;
		case DATE:
			f = new Fragment("propertyInput", "textInput", this);
			TextField field = new TextField("input", model);
			field.add(new DatePickerBehavior());
			f.add(field.add(
					new GenericResourceValidator(instance
							.getValidatorFor(attribute))).setRequired(required));
			inputFields.add(f);
			break;
		default:
			f = new Fragment("propertyInput", "textInput", this);
			f.add(new TextField("input", model).add(
					new GenericResourceValidator(instance
							.getValidatorFor(attribute))).setRequired(required));
			inputFields.add(f);
			break;
		}
		fragment.add(inputFields);
		AjaxButton button = new AjaxButton("addField") {

			@Override
			protected void onError(final AjaxRequestTarget target,
					final Form<?> form) {
				addFieldAndCheckVisibility(instance, attribute, inputFields,
						target, form);
			}

			@Override
			protected void onSubmit(final AjaxRequestTarget target,
					final Form<?> form) {
				addFieldAndCheckVisibility(instance, attribute, inputFields,
						target, form);
			}

			/**
			 * @param instance
			 * @param attribute
			 * @param inputFields
			 * @param target
			 * @param form
			 */
			private void addFieldAndCheckVisibility(final RBEntity instance,
					final String attribute, final RepeatingView inputFields,
					final AjaxRequestTarget target, final Form<?> form) {
				addSingleField(form, inputFields, target, instance, attribute);
				if((Integer)(instance.getTicketsFor(attribute).size())
						>= (Integer) instance.getMetaInfoFor(attribute, MetaDataKeys.MAX)){
					this.setVisible(false);
				}
			}
		};
		button.add(new Label("linkLabel", "(+)"));
		button.setVisible((expendable && ((Integer) instance.getMetaInfoFor(
				attribute, MetaDataKeys.MAX)) > ((Integer) instance
				.getMetaInfoFor(attribute, MetaDataKeys.CURRENT))));
		fragment.add(button);
		return fragment;
	}

	/**
	 * Adds a single field to a form.
	 * This method is called by the {@link GenericResourceFormPanel}.
	 * @param container -
	 * @param view -
	 * @param target -
	 * @param attribute -
	 * @param entity -
	 */
	private void addSingleField(final WebMarkupContainer container,
			final RepeatingView view, final AjaxRequestTarget target,
				final RBEntity entity, final String attribute) {
		//TODO REFACTOR (call buildItem?)
		Fragment fragment = null;
		int ticket = -1;
		try {
			ticket = entity.generateTicketFor(attribute);
		} catch (RBInvalidValueException e) {
			error("Could not generate Ticket");
			e.printStackTrace();
		} catch (RBInvalidAttributeException e) {
			error("Could not generate Ticket");
			e.printStackTrace();
		}
		final GenericResourceModel model = new GenericResourceModel(entity,attribute, ticket);
		switch((ElementaryDataType) entity.getMetaInfoFor(attribute, MetaDataKeys.TYPE)){
		case RESOURCE:
			ResourceID uri = null;
			List<PropertyAssertion> propertyAssertionsList = (List) entity.getResourceSchema().getPropertyAssertions();
			for (PropertyAssertion assertion : propertyAssertionsList) {
				if(null!=assertion.getConstraints()){
					Set<Constraint> constraints =  assertion.getConstraints();
					for (Constraint constraint : constraints) {
						uri = constraint.getResourceTypeConstraint();
					}
				}
			}
			fragment = new Fragment(view.newChildId(), "resource", this);
			List entityList = (List) getServiceProvider().getRBEntityManagement()
				.loadAllRBEntitiesForSchema(getServiceProvider().getResourceSchemaManagement()
						.getResourceSchemaForResourceType(uri));

			IChoiceRenderer renderer = new IChoiceRenderer<RBEntity>() {
				@Override
				public Object getDisplayValue(final RBEntity object) {
					return object.getQualifiedName();
				}
				@Override
				public String getIdValue(final RBEntity object, final int index) {
					return object.getQualifiedName().toString();
				}
			};

			fragment.add(new DropDownChoice<RBEntity>("option", model, entityList, renderer){
				protected boolean wantOnSelectionChangedNotifications(){
					return false;
				}
				@Override
				protected void onSelectionChanged(final RBEntity entity){
					model.setObject(getServiceProvider().getRBEntityManagement()
							.loadRBEntity(entity.getQualifiedName()));
					}
			}.setType(RBEntity.class));
			break;
		case BOOLEAN:
			fragment = new Fragment(view.newChildId(), "booleanInput", this);
			fragment.add(new CheckBox("input", model));
			break;
		case DATE:
			fragment = new Fragment(view.newChildId(), "textInput", this);
			TextField field = new TextField("input", model);
			field.add(new DatePickerBehavior());
			fragment.add(field.add(new GenericResourceValidator(entity.getValidatorFor(attribute))));
			break;
		default:
			fragment = new Fragment(view.newChildId(), "textInput", this);
			fragment.add(new TextField("input", model));
			break;
		}
		fragment.setOutputMarkupId(true);
		view.add(fragment);
		target.add(container);
		target.focusComponent(fragment);
	}

	/**
	 * @param container -
	 * @param customview -
	 * @param target -
	 * @param model -
	 */
	private void addNonSchemaFields(final WebMarkupContainer container,
			final RepeatingView customview, final AjaxRequestTarget target, final GenericResourceModel model) {
		Fragment fragment = new Fragment(customview.newChildId(), "addNonSchemaValues", this, model);
		fragment.setOutputMarkupId(true);
		fragment.add(new TextField("key"));
		fragment.add(new TextField("value"));
		customview.add(fragment);
		target.add(container);
		target.focusComponent(fragment);
	}

	// -----------------------------------------------------

	/**
	 * @param view
	 *            /
	 * @return
	 * 			  /
	 */
	public CKComponent setViewMode(final ViewMode view) {
		throw new NotYetImplementedException();
	}

	// -----------------------------------------------------

	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		init(schema, instance);
	}
}
