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
package de.lichtflut.rb.webck.components.widgets.builtin;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.entity.EntityInfoPanel;
import de.lichtflut.rb.webck.components.entity.EntityPanel;
import de.lichtflut.rb.webck.components.widgets.PredefinedWidget;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.CurrentPersonModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNull;

/**
 * <p>
 *  Widget showing the person associated with the current user.
 * </p>
 *
 * <p>
 * 	Created Jan 31, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ThatsMeWidget extends PredefinedWidget {
	
	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param spec The specification.
	 */
	public ThatsMeWidget(String id, WidgetSpec spec, ConditionalModel<Boolean> perspectiveInConfigMode) {
		super(id, Model.of(spec), perspectiveInConfigMode);
		
		final CurrentPersonModel model = new CurrentPersonModel();
		
		add(new EntityInfoPanel("info", model));
		
		add(new EntityPanel("entity", model));
		
		add(new Label("noEntityHint", new ResourceModel("message.person-not-found"))
			.add(visibleIf(isNull(model))));

	}
	
}
