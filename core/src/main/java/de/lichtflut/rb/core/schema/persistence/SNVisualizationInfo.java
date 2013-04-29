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
package de.lichtflut.rb.core.schema.persistence;

import static de.lichtflut.rb.core.schema.RBSchema.HAS_STYLE;
import static de.lichtflut.rb.core.schema.RBSchema.IS_EMBEDDED;
import static de.lichtflut.rb.core.schema.RBSchema.IS_FLOATING;

import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.ResourceView;

/**
 * <p>
 *  Node representation of a field visualization.
 * </p>
 *
 * <p>
 *  Created 17.08.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNVisualizationInfo extends ResourceView {

    public SNVisualizationInfo(ResourceNode resource) {
        super(resource);
    }

    public SNVisualizationInfo() {
    }

    // ----------------------------------------------------

    public boolean isEmbedded() {
        return booleanValue(IS_EMBEDDED);
    }

    public SNVisualizationInfo setEmbedded(boolean embedded) {
        setValue(IS_EMBEDDED, embedded);
        return this;
    }

    public boolean isFloating() {
        return booleanValue(IS_FLOATING);
    }

    public SNVisualizationInfo setFloating(boolean floating) {
        setValue(IS_FLOATING, floating);
        return this;
    }

    public String getStyle() {
        return stringValue(HAS_STYLE);
    }

    public SNVisualizationInfo setStyle(String style) {
        setValue(HAS_STYLE, style);
        return this;
    }

    // ----------------------------------------------------


    @Override
    public String toString() {
        return getClass().getSimpleName() + "["
                + "embedded=" + isEmbedded() + ";"
                + "floating=" + isFloating() + ";"
                + "style='" + getStyle() + "';"
                + "]";
    }
}
