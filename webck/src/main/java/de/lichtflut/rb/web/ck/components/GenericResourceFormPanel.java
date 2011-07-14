/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.IModel;
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
			final WebMarkupContainer container = new WebMarkupContainer("customValueContainer");
			container.setOutputMarkupId(true);
			
			final RepeatingView customview = new RepeatingView("customValues");

			container.add(customview);
			AjaxButton button = new AjaxButton("customInput", Model.of("Add Vaules!")) {

				@Override
				protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
					System.out.println("HALLO +++++++++++++++++++++++++++++++++++++++++++");
					buildCustomItem(instance, form, target);
				}

				@Override
				protected void onError(final AjaxRequestTarget target, final Form<?> form) {
					System.out.println("ERROR +++++++++++++++++++++++++++++++++++++++++++");
					// TODO: ADD GenericResourceForm parameter!!!!!!
					Fragment fragment = new Fragment(customview.newChildId(), "addNonSchemaValues", this.getParent().getParent(), null);
					fragment.setOutputMarkupId(true);
					fragment.add(new TextField("key"));
					fragment.add(new TextField("value"));
					customview.add(fragment);
					target.add(container);
					target.focusComponent(fragment);
				}
			};
			form.add(container);
			form.add(button);
		}// End of if(schema==null)

	}// End of Method init

	/**
	 * Appends GenericResourceForm to add custom fields.
	 * @param instance -
	 * @param form -
	 * @param target -
	 * @return {@link Component}
	 */
	private void buildCustomItem(final RBEntity instance, Form<?> form, AjaxRequestTarget target) {
//		final RepeatingView view = new RepeatingView("customInput");
//		final Fragment fragment = new Fragment(view.newChildId(), "customProperties", this);
//		
//		Fragment f = new Fragment(view.newChildId(), "addNonSchemaValues", this);
//		f.add(new TextField("key"));
//		f.add(new TextField("value"));
//		view.add(f);
//		
//		AjaxButton button = new AjaxButton("addCustomFields") {
//
//			@Override
//			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
//				Fragment f = new Fragment(view.newChildId(), "addNonSchemaValues", this);
//				f.add(new TextField("key"));
//				f.add(new TextField("value"));
//				view.add(f);
////				view.modelChanged();
//			}
//
//			@Override
//			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
//				// TODO Auto-generated method stub
//			}
//		};
//		// TODO Auto-generated method stub
//		button.add(new Label("linkLabel", "add additional values"));
//		fragment.add(button);
//		view.add(fragment);
		System.out.println("########################################################");
	}

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
		Fragment fragment = new Fragment(view.newChildId(), "referenceInput",
				this);
		fragment.add((new Label("propertyLabel", instance
				.getSimpleAttributeName(attribute) + (required ? " (*)" : ""))));
		// Decide which input-field should be used
		Fragment f = null;

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
				// Do nothing
			}

			@Override
			protected void onSubmit(final AjaxRequestTarget target,
					final Form<?> form) {
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
