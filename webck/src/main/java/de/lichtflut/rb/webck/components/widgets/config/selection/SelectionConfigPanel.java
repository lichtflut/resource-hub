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
package de.lichtflut.rb.webck.components.widgets.config.selection;

import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static de.lichtflut.rb.core.viewspec.Selection.SelectionType;

/**
 * <p>
 *  Panel for configuration of a {@link Selection}.
 * </p>
 *
 * <p>
 * 	Created Jan 27, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SelectionConfigPanel extends TypedPanel<Selection> {
	
	/**
     * Constructor.
	 * @param id The wicket ID.
	 * @param model The model providing the selection.
	 */
	public SelectionConfigPanel(String id, final IModel<Selection> model) {
		super(id, model);

        IModel<SelectionType> typeModel = new PropertyModel<SelectionType>(model, "type");

        final DropDownChoice<SelectionType> typeChooser =
                new DropDownChoice<SelectionType>("type", typeModel, Arrays.asList(SelectionType.values()));
        add(typeChooser);

        add(new TextArea<String>("expression", new PropertyModel<String>(model, "queryExpression")));
		
	}
	
}
