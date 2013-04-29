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
package de.lichtflut.rb.webck.config;

import org.apache.wicket.request.cycle.RequestCycle;

/**
 * <p>
 *  Default URI builder to info vis services.
 * </p>
 *
 * <p>
 *  Created Jan 11, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class DefaultInfoVisServicePathBuilder implements InfoVisServicePathBuilder {

    @Override
    public InfoVisPath create(String domain) {
        return new InfoVisPath(context(), domain);
    }

    // ----------------------------------------------------

    private String context() {
        return RequestCycle.get().getRequest().getContextPath() + "/service/infovis";
    }

}
