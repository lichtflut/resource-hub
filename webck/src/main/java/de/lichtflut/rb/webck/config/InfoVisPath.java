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

import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Builder object for the info vis path,
 * </p>
 *
 * <p>
 *  Created Mar. 12, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class InfoVisPath extends AbstractPath<InfoVisPath> {

    public InfoVisPath(String ctxPath) {
        super(ctxPath);
    }

    public InfoVisPath(String ctxPath, String domain) {
        super(ctxPath, domain);
    }

    // ----------------------------------------------------

    public InfoVisPath tree() {
        return service("infovis/tree");
    }

    public InfoVisPath withRoot(String root) {
        return paramEncoded("root", root);
    }

    public InfoVisPath withRoot(ResourceID root) {
        return paramEncoded("root", root);
    }

    public InfoVisPath ofType(String type) {
        return param("type", type);
    }

}
