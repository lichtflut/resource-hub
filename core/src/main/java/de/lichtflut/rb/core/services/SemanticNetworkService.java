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

import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  This service allows interactions with the semantic network, i.e. the arastreju engine.
 *  This service operates on the current conversation.
 * </p>
 *
 * <p>
 *  Created 01.08.12
 * </p>
 *
 * @author Oliver Tigges
 */
public interface SemanticNetworkService {

    /**
     * Resolve a resource.
     * @param rid The ID to resolve.
     * @return The resolved node.
     */
    ResourceNode resolve(ResourceID rid);

    /**
     * Resolve a resource using th given read contexts.
     * @param rid The ID to resolve.
     * @param contexts The contexts to be regarded when traversing the node.
     * @return The resolved node.
     */
    ResourceNode resolve(ResourceID rid, Context... contexts);

    /**
     * Fine a node by it's qualified name.
     * @param qn The qualified name.
     * @return The node or null if not found.
     */
    ResourceNode find(QualifiedName qn);

    /**
     * Remove a node identified by it's qualified name.
     * @param qn The qualified name.
     */
    void remove(QualifiedName qn);

    /**
     * Remove a node.
     * @param resource The resource to be removed.
     */
    void remove(ResourceID resource);

    // ----------------------------------------------------

    void attach(ResourceNode node);

    /**
     * Add a statement.
     * @param statement The statement to add.
     */
    void add(Statement statement);

    /**
     * Remove a statement.
     * @param statement The statement to be removed.
     */
    void remove(Statement statement);
}
