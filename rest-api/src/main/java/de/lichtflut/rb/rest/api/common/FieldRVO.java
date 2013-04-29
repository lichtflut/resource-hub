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
package de.lichtflut.rb.rest.api.common;

/**
 * <p>
 *  Represents a field with id, label and value.
 * </p>
 *
 * <p>
 *  Created Mar 15, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class FieldRVO {

    private String id;
    private String label;
    private String value;

    // ----------------------------------------------------

    public String getId() {
        return id;
    }

    public FieldRVO setId(String id) {
        this.id = id;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public FieldRVO setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getValue() {
        return value;
    }

    public FieldRVO setValue(String value) {
        this.value = value;
        return this;
    }
}
