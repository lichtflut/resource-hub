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

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Base for REST value objects.
 * </p>
 *
 * <p>
 *  Created July 22, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class BaseRVO {

    private final List<LinkRVO> links = new ArrayList<LinkRVO>();

    // ----------------------------------------------------

    public List<LinkRVO> getLinks() {
        return links;
    }

}