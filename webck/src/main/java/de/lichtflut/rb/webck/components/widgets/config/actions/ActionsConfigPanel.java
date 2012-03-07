/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config.actions;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetAction;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SNWidgetAction;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.fields.ClassPickerField;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import de.lichtflut.rb.webck.models.resources.ResourcePropertyModel;

/**
 * <p>
 *  Panel for configuration of widget actions. Currently only one action can be configured.
 * </p>
 *
 * <p>
 * 	Created Mar 7, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ActionsConfigPanel extends TypedPanel<WidgetSpec> {
	
	public enum ActionType {
		NONE,
		INSTANTIATE
	}
	
	@SpringBean
	protected ServiceProvider provider;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param widgetModel The widget specification model.
	 */
	public ActionsConfigPanel(final String id, final IModel<WidgetSpec> specModel) {
		super(id, specModel);
		
		setOutputMarkupId(true);
		
		final IModel<WidgetAction> actionModel = initActionModel(specModel);

		final IModel<ActionType> categoryModel = new ActionTypeModel(specModel, actionModel);
		
		final IModel<ResourceID> typeModel = new ResourcePropertyModel<ResourceID>(actionModel, WDGT.CREATE_INSTANCE_OF);
		
		final DropDownChoice<ActionType> category = 
				new DropDownChoice<ActionType>("category", categoryModel,
				Arrays.asList(ActionType.values()), new EnumChoiceRenderer<ActionType>(this)) ;
		
		category.add(new OnChangeAjaxBehavior() {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(ActionsConfigPanel.this);				
			}
		});
		add(category);
		
		final ClassPickerField typePicker = new ClassPickerField("typePicker", typeModel);
		typePicker.add(visibleIf(areEqual(categoryModel, ActionType.INSTANTIATE)));
		add(typePicker);
		
	}
	
	// ----------------------------------------------------
	
	private IModel<WidgetAction> initActionModel(final IModel<WidgetSpec> widgetModel) {
		return new DerivedModel<WidgetAction, WidgetSpec>(widgetModel) {
			@Override
			protected WidgetAction derive(WidgetSpec spec) {
				final List<WidgetAction> actions = spec.getActions();
				if (actions.isEmpty()) {
					return new SNWidgetAction();
				} else {
					return actions.get(0);
				}
			}
		};
	}
	
	private final class ActionTypeModel implements IModel<ActionType> {

		private final IModel<WidgetAction> actionModel;
		private final IModel<WidgetSpec> specModel;
		
		// ----------------------------------------------------

		/** Constructor
		 * @param actionModel
		 */
		public ActionTypeModel(IModel<WidgetSpec> specModel, IModel<WidgetAction> actionModel) {
			this.specModel = specModel;
			this.actionModel = actionModel;
		}
		
		/** 
		 * {@inheritDoc}
		 */
		@Override
		public ActionType getObject() {
			 if (actionModel.getObject().getAssociations().isEmpty()) {
				 return ActionType.NONE;
			 } else {
				 return ActionType.INSTANTIATE;
			 }
		}
		
		/** 
		 * {@inheritDoc}
		 */
		@Override
		public void setObject(ActionType type) {
			final WidgetAction action = actionModel.getObject();
			switch (type) {
			case INSTANTIATE:
				SNOPS.assure(action, RDF.TYPE, WDGT.ACTION_INSTANTIATE);
				specModel.getObject().addAssociation(WDGT.SUPPORTS_ACTION, action);
				break;
			case NONE:
				SNOPS.remove(specModel.getObject(), WDGT.SUPPORTS_ACTION, action);
			default:
				break;
			}
		}

		/** 
		 * {@inheritDoc}
		 */
		@Override
		public void detach() {
		}
	}
	
}
