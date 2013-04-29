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
package de.lichtflut.rb.webck.models.fields;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.model.IModel;

/**
* <p>
*   This model checks if a field is 'floating' or 'breaking' and returns the
 *   corresponding CSS class.
* </p>
*
* <p>
*   Created 19.10.12
* </p>
*
* @author Oliver Tigges
*/
public class RBFieldLabelCssClassModel extends DerivedDetachableModel<String, RBField> {

    public RBFieldLabelCssClassModel(IModel<RBField> original) {
        super(original);
    }

    @Override
    protected String derive(RBField original) {
        if (original.getVisualizationInfo().isFloating()) {
            return "floating";
        } else {
            return "breaking";
        }
    }
}
