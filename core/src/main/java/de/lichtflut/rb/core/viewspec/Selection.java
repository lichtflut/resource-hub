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
package de.lichtflut.rb.core.viewspec;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.query.Query;

/**
 * <p>
 *  Selection of nodes.
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface Selection extends ResourceNode {

    enum SelectionType {

        BY_QUERY(WDGT.SELECT_BY_QUERY),
        BY_SCRIPT(WDGT.SELECT_BY_SCRIPT),
        BY_TYPE(WDGT.SELECT_BY_TYPE) ,
        BY_VALUE(WDGT.SELECT_BY_VALUE),
        BY_RELATION(WDGT.SELECT_BY_RELATION);

        private ResourceID predicate;

        public ResourceID getPredicate() {
            return predicate;
        }

        SelectionType(ResourceID predicate) {
            this.predicate = predicate;
        }
    }

    // ----------------------------------------------------

    SelectionType getType();

    void setType(SelectionType type);

    // ----------------------------------------------------

    String getQueryExpression();

    void setQueryExpression(String expr);

    // ----------------------------------------------------

	boolean isDefined();
	
	void adapt(Query query);

}
