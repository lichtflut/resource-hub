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
package de.lichtflut.rb.core.query;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.security.RBUser;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.ResourceNode;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  Context of a query or query script.
 * </p>
 *
 * <p>
 *  Created July 18, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class QueryContext {

    private RBUser user;

    private Map<String, String> vars = new HashMap<String, String>();

    // ----------------------------------------------------

    public static QueryContext from(RBUser user) {
        return new QueryContext()
                .put("user.qn", user.getQualifiedName().toURI())
                .put("user.name", user.getName())
                .put("user.email", user.getEmail())
                .setUser(user);
    }

    // ----------------------------------------------------

    public String substitute(String s, Conversation conversation) {
        build(conversation);
        for (String key : vars.keySet()) {
            s = s.replaceAll(key, vars.get(key));
        }
        return s;
    }

    // ----------------------------------------------------

    public QueryContext put(String key, String value) {
        vars.put("\\$\\{" + key + "\\}", value);
        return this;
    }

    public QueryContext setUser(RBUser user) {
        this.user = user;
        return this;
    }

    // ----------------------------------------------------

    @Override
    public String toString() {
        return "QueryContext{" + vars + '}';
    }

    // ----------------------------------------------------

    /**
     * Set variables that need to be resolved with conversation.
     */
    private void build(Conversation conversation) {
        if (user != null) {
            ResourceNode userNode = conversation.findResource(user.getQualifiedName());
            if (userNode != null) {
                final ResourceNode person = SNOPS.fetchObjectAsResource(userNode, RBSystem.IS_RESPRESENTED_BY);
                if (person != null) {
                    put("user.person", person.toURI());
                    return;
                }
            }
        }
        // default
        put("user.person", "nil");
    }

}
