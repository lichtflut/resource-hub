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
package de.lichtflut.rb.core.services;

import de.lichtflut.rb.core.perceptions.PerceptionItem;
import org.arastreju.sge.naming.QualifiedName;

import java.util.List;

/**
 * <p>
 *  Service interface for Perceptions.
 * </p>
 *
 * <p>
 *  Created 29.11.12
 * </p>
 *
 * @author Oliver Tigges
 */
public interface PerceptionService {

    /**
     * Find a perception item by it's qualified name.
     * @param qn The qualified name.
     * @return The item or null.
     */
    PerceptionItem findItemByID(QualifiedName qn);

    // ----------------------------------------------------

    /**
     * Get all item's of the perception.
     * @param qn The qualified name of the perception.
     * @return The perception's items.
     */
    List<PerceptionItem> getItemsOfPerception(QualifiedName qn);

    /**
     * Add a new item to the perception.
     * @param item The new item.
     * @param qn The perception's qualified name.
     */
    void addItemToPerception(PerceptionItem item, QualifiedName qn);

    // ----------------------------------------------------

    /**
     * Get the base/root item's of the perception. Starting with this item the tree can be build.
     * @param qn The qualified name of the perception.
     * @return The perception's root items.
     */
    List<PerceptionItem> getBaseItemsOfPerception(QualifiedName qn);

    /**
     * Add a new base/root item to the perception.
     * @param item The new base/root item.
     * @param qn The perception's qualified name.
     */
    void addBaseItemToPerception(PerceptionItem item, QualifiedName qn);

}
