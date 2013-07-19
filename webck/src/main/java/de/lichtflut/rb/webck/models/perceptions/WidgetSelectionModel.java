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
package de.lichtflut.rb.webck.models.perceptions;

import de.lichtflut.rb.core.query.QueryContext;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.query.QueryResult;
import org.arastreju.sge.query.SimpleQueryResult;
import org.arastreju.sge.query.script.QueryScriptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* <p>
*  Model providing a widget's selection result.
* </p>
* <p/>
* <p>
*   Created 19.07.13
* </p>
*
* @author Oliver Tigges
*/
public class WidgetSelectionModel extends DerivedDetachableModel<QueryResult, WidgetSpec> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WidgetSelectionModel.class);

    @SpringBean
    private ViewSpecificationService viewSpecificationService;

    @SpringBean
    private ServiceContext serviceContext;

    // ----------------------------------------------------

    public WidgetSelectionModel(IModel<WidgetSpec> spec) {
        super(spec);
        Injector.get().inject(this);
    }

    @Override
    protected QueryResult derive(WidgetSpec widget) {
        QueryContext qc = QueryContext.from(serviceContext.getUser());
        try {
            return viewSpecificationService.load(widget, qc);
        } catch (Exception e) {
            LOGGER.warn("Widget data selection for {} failed due to: {}", widget.getSelection(), e.getMessage());
            return SimpleQueryResult.EMPTY;
        }
    }
}
