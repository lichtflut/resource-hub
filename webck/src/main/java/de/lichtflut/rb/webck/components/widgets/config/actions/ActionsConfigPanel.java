/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;

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

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param specModel The widget specification model.
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
			protected void onUpdate(final AjaxRequestTarget target) {
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
			protected WidgetAction derive(final WidgetSpec spec) {
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

		/**
         * Constructor.
		 */
		public ActionTypeModel(final IModel<WidgetSpec> specModel, final IModel<WidgetAction> actionModel) {
			this.specModel = specModel;
			this.actionModel = actionModel;
		}

		@Override
		public ActionType getObject() {
			if (actionModel.getObject().getAssociations().isEmpty()) {
				return ActionType.NONE;
			} else {
				return ActionType.INSTANTIATE;
			}
		}

		@Override
		public void setObject(final ActionType type) {
			final WidgetAction action = actionModel.getObject();
			switch (type) {
				case INSTANTIATE:
					action.setActionType(WDGT.ACTION_INSTANTIATE);
                    specModel.getObject().addAction(action);
					break;
				case NONE:
					SNOPS.remove(specModel.getObject(), WDGT.SUPPORTS_ACTION, action);
					break;
				default:
					break;
			}
		}

		@Override
		public void detach() {
		}
	}

}
