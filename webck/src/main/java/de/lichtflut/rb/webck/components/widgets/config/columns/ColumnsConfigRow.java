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
package de.lichtflut.rb.webck.components.widgets.config.columns;

import de.lichtflut.rb.core.viewspec.ColumnDef;
import de.lichtflut.rb.webck.common.OrderedNodesContainer;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.fields.PropertyPickerField;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Panel for configuration of a (list) widget's columns.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ColumnsConfigRow extends TypedPanel<ColumnDef> {
	
	/**
	 * @param id
	 * @param model
	 * @param container 
	 */
	@SuppressWarnings("rawtypes")
	public ColumnsConfigRow(final String id, final IModel<ColumnDef> model, final OrderedNodesContainer container) {
		super(id, model);
		
		setOutputMarkupId(true);
		final PropertyPickerField picker = new PropertyPickerField("property", 
				new PropertyModel<ResourceID>(model, "property"));
		picker.setRequired(true);
		add(picker);
		
		add(new AjaxLink("up") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				container.moveUp(model.getObject(), 1);
				notifyOrderChange();
			}
		});
		
		add(new AjaxLink("down") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				container.moveDown(model.getObject(), 1);
				notifyOrderChange();
			}
		});
	}
	
	// ----------------------------------------------------
	
	void notifyOrderChange() {
		send(this, Broadcast.BUBBLE, new ModelChangeEvent<Void>(ModelChangeEvent.ANY));
	}
	
}
