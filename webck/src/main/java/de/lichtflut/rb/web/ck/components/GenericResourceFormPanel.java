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
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.web.behaviors.DatePickerBehavior;
import de.lichtflut.rb.web.components.validators.GenericResourceValidator;
import de.lichtflut.rb.web.models.GenericResourceModel;

/**
 * <p>
 * TODO: [DESCRIPTION].
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
	private Fragment f = null;
	// Constructors
	/**
	 * @param id
	 *            /
	 * @param schema
	 *            /
	 * @param instance
	 *            /
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
	 * @param schema
	 *            /
	 * @param in
	 *            /
	 *
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
				// Get predefined tickets, if there are some
				Collection<Integer> tickets = instance.getTicketsFor(attribute);
				for (int cnt = 0; cnt < minimum_cnt; cnt++) {
					GenericResourceModel model;
					try {
						if (cnt >= (tickets.size())) {
							model = new GenericResourceModel(instance,
									attribute);
						} else {
							model = new GenericResourceModel(instance,
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
					addNonSchemaFields(form, nonSchemaView, target);
				}

				@Override
				protected void onError(final AjaxRequestTarget target, final Form<?> form) {
					// TODO: ADD GenericResourceModel parameter!!!!!!
					addNonSchemaFields(form, nonSchemaView, target);
				}
			};
			form.add(nonSchemaView);
			form.add(button);
		}// End of if(schema==null)

	}// End of Method init

	// -----------------------------------------------------


	/**
	 * @param instance
	 *            /
	 * @param model
	 *            /
	 * @param attribute
	 *            /
	 * @param view
	 *            /
	 * @param required
	 *            /
	 * @param expendable
	 *            /
	 * @return fragment /
	 */
	private Component buildItem(final RBEntity instance,
			final GenericResourceModel model, final String attribute,
			final RepeatingView view, final boolean required,
			final boolean expendable) {
		final Fragment fragment = new Fragment(view.newChildId(), "referenceInput",
				this);
		fragment.add((new Label("propertyLabel", instance
				.getSimpleAttributeName(attribute) + (required ? " (*)" : ""))));
		// Decide which input-field should be used
		f = null;

		switch ((ElementaryDataType) instance.getMetaInfoFor(attribute,
				MetaDataKeys.TYPE)) {
		case RESOURCE:
			// Works only with one Resource reference so far...
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
					return object.toString();
				}
				@Override
				public String getIdValue(final RBEntity object, final int index) {
					// TODO Auto-generated method stub
					return object.getQualifiedName().toURI();
				}
			};

			f.add(new DropDownChoice<RBEntity>("option", model, entityList, renderer){
				protected boolean wantOnSelectionChangedNotifications(){
					return false;
				}
				protected void onSelectionChanged(final RBEntity entity){
					model.setObject(entity);
				}
			});
			break;
		case BOOLEAN:
			f = new Fragment("propertyInput", "booleanInput", this);
			f.add(new CheckBox("input", model));
			break;
		case DATE:
			f = new Fragment("propertyInput", "textInput", this);
			TextField field = new TextField("input", model);
			field.add(new DatePickerBehavior());
			f.add(field.add(
					new GenericResourceValidator(instance
							.getValidatorFor(attribute))).setRequired(required));
			break;
		default:
			f = new Fragment("propertyInput", "textInput", this);
			f.add(new TextField("input", model).add(
					new GenericResourceValidator(instance
							.getValidatorFor(attribute))).setRequired(required));
			break;
		}
		fragment.add(f);
		AjaxButton button = new AjaxButton("addField") {

			@Override
			protected void onError(final AjaxRequestTarget target,
					final Form<?> form) {
				addSingleNonSchemaField(form, view, target);
			}

			@Override
			protected void onSubmit(final AjaxRequestTarget target,
					final Form<?> form) {

				addSingleNonSchemaField(form, view, target);
				// This does not really work, so do nothing
				// TODO: Fix it
				/*
				 * Component item = buildItem(instance,new
				 * GenericResourceModel(instance, attribute), attribute, view,
				 * false, true); // first execute javascript which creates a
				 * placeholder tag in markup for this item
				 * target.prependJavascript( String.format(
				 * "var item=document.createElement('%s');item.id='%s';"+
				 * "Wicket.$('%s').appeinitComponentndChild(item);", "tr",
				 * item.getMarkupId(), item.getMarkupId())); view.add(item);
				 * target.addComponent(item);
				 */
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
	 * @param container -
	 * @param view -
	 * @param target -
	 */
	private void addSingleNonSchemaField(final WebMarkupContainer container,
			final RepeatingView view, final AjaxRequestTarget target) {
		Fragment fragment = new Fragment(view.newChildId(), "newTextInput", this, null);
		fragment.setOutputMarkupId(true);
		fragment.add(new TextField("newInput"));
//		f.setOutputMarkupId(true);
		view.setOutputMarkupId(true);
		f.add(fragment);
		target.add(view.getParent());
		target.focusComponent(fragment);
	}

	/**
	 * @param container -
	 * @param customview -
	 * @param target -
	 */
	private void addNonSchemaFields(final WebMarkupContainer container,
			final RepeatingView customview, final AjaxRequestTarget target) {
		Fragment fragment = new Fragment(customview.newChildId(), "addNonSchemaValues", this, null);
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
