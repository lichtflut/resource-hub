/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.common.EntityLabelBuilder;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.fields.PropertyPickerField;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.components.links.ConfirmedLink;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.types.PropertyRowListModel;

/**
 * <p>
 *  Panel for editing of a Resource Schema.
 * </p>
 *
 * <p>
 * 	Created Sep 22, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SchemaEditorPanel extends Panel {
	
	@SpringBean
	private ServiceProvider provider;
	
	// ----------------------------------------------------
	
	/**
	 *  Constructor.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SchemaEditorPanel(final String id, final IModel<ResourceSchema> model) {
		super(id);
		
		final PropertyRowListModel rowModel = new PropertyRowListModel(model);
		
		setOutputMarkupPlaceholderTag(true);
		setOutputMarkupId(true);
		
		add(new Label("type", new PropertyModel(model, "describedType")));
		
		add(new TypeHierarchyPanel("hierarchy", new PropertyModel(model, "describedType")));
		
		final Form<?> form = new Form("form");
		form.setOutputMarkupId(true);
		form.add(new FeedbackPanel("feedback"));
		
		final TextArea<EntityLabelBuilder> labelExpression = 
				new TextArea<EntityLabelBuilder>("labelExpression", new PropertyModel(model, "labelBuilder"));
		labelExpression.setType(EntityLabelBuilder.class);
		form.add(labelExpression);
		
		form.add(createRows(rowModel));
		
		form.add(new RBStandardButton("addRow") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				rowModel.getObject().add(new PropertyRow());
				target.add(SchemaEditorPanel.this);
			}
		});
		
		form.add(new RBDefaultButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				final ResourceSchema original = model.getObject();
				final ResourceSchemaImpl schema = new ResourceSchemaImpl(original.getDescribedType());
				schema.setLabelBuilder(original.getLabelBuilder());
				for (PropertyRow row: rowModel.getObject()) {
					schema.addPropertyDeclaration(row.asPropertyDeclaration());	
				}
				provider.getSchemaManager().store(schema);
				send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.TYPE));
				info("Schema saved succesfully.");
				target.add(form);
			}
		});
		
		form.add(new RBStandardButton("delete") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				final ResourceSchema original = model.getObject();
				provider.getTypeManager().removeType(original.getDescribedType());
				info("Schema deleted.");
				SchemaEditorPanel.this.setVisible(false);
				target.add(SchemaEditorPanel.this);
				send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.TYPE));
			}
		});
		
		add(form);
		
	}

	// ----------------------------------------------------
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected ListView<PropertyRow> createRows(final PropertyRowListModel rowModel) {
		return new ListView<PropertyRow>("listView", rowModel) {
			@Override
			protected void populateItem(final ListItem<PropertyRow> item) {
				final PropertyRow row = item.getModelObject();
				
				final IModel<ResourceID> property = new PropertyModel<ResourceID>(row, "propertyDescriptor");
				item.add(new PropertyPickerField("property", property).setRequired(true));
				item.add(createCreateLink(property));
				
				item.add(new TextField<String>("fieldLabel", new PropertyModel(row, "defaultLabel")));

				item.add(new TextField("min", new PropertyModel(row, "min")));
				
				final PropertyModel<Boolean> unboundModel = new PropertyModel<Boolean>(row, "unbounded");
				final TextField maxField = new TextField("max", new PropertyModel(row, "max"));
				maxField.add(ConditionalBehavior.visibleIf(ConditionalModel.isFalse(unboundModel)));
				maxField.setOutputMarkupPlaceholderTag(true);
				item.add(maxField);
				
				final CheckBox checkBox = new CheckBox("unbounded", unboundModel);
				checkBox.add(new AjaxFormComponentUpdatingBehavior("onclick") {
					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						if (!row.isUnbounded()) {
							row.setMax(Math.max(1, row.getMin()));
						}
						target.add(maxField);
					}
				});
				item.add(checkBox);
				
				final ConstraintsEditorPanel constraintsEditor = 
						new ConstraintsEditorPanel("constraints", item.getModel());
				item.add(constraintsEditor);
				
				final boolean isPrivateTD = !row.hasPublicConstraint();
				
				final DropDownChoice<Datatype> dataTypeChoice = 
						new DropDownChoice<Datatype>("dataType", new PropertyModel(item.getModel(), "dataType"),
						Arrays.asList(Datatype.values()), new EnumChoiceRenderer<Datatype>(this)) ;
				
				dataTypeChoice.setEnabled(isPrivateTD);
				dataTypeChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						target.add(constraintsEditor);
					}
				});
				item.add(dataTypeChoice);
				
				item.add(new AjaxLink("up") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						rowModel.moveUp(item.getIndex());
						update();
					}
				});
				
				item.add(new AjaxLink("down") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						rowModel.moveDown(item.getIndex());
						update();
					}
				});
				
				item.add(createRemoveRowLink(rowModel, item.getIndex()));
			}
		};
	}
	
	protected Component createCreateLink(final IModel<ResourceID> targetModel) {
		final AjaxSubmitLink link = new AjaxSubmitLink("createLink") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		};
		return link.setVisible(false);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private AjaxLink createRemoveRowLink(final PropertyRowListModel rowModel, final int index) {
		return new ConfirmedLink("removeLink", new ResourceModel("global.message.remove-property-declaration-confirmation")) {
			@Override
			protected void applyActions(AjaxRequestTarget target) {
				rowModel.remove(index);
				update();
			}
		};
	}
	
	// -----------------------------------------------------
	
	protected String max(final PropertyDeclaration pa) {
		if (pa.getCardinality().isUnbound()) {
			return "unbound";
		} else {
			return "" + pa.getCardinality().getMaxOccurs();
		}
	}
	
	protected void update() {
		RBAjaxTarget.add(this);
	}
	
}
