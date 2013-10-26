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
 *  Default id builder to query services.
 * </p>
 *
 * <p>
 * 	Created Jun 22, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class DefaultQueryServicePathBuilder implements QueryServicePathBuilder {

    @Override
    public QueryPath create(String domain) {
        return new QueryPath(context(), domain);
    }

    // ----------------------------------------------------

    @Override
	public String queryResources(String domain, String type) {
        return create(domain).queryResources()
                .ofType(type)
                .toURI();
	}

    @Override
    public String queryEntities(String domain, String type) {
        return create(domain).queryEntities()
                .ofType(type)
                .toURI();
	}

    @Override
    public String queryClasses(String domain, String superClass) {
        return create(domain).queryClasses()
                .withSuperClass(superClass)
                .toURI();
    }

    @Override
    public String queryProperties(String domain, String superProperty) {
        return create(domain).queryProperties()
                .withSuperProperty(superProperty)
                .toURI();
    }

    @Override
    public String queryUsers(String domain) {
        return create(domain).queryUsers().toURI();
    }

    // ----------------------------------------------------

    private String context() {
        return RequestCycle.get().getRequest().getContextPath() + "/service";
    }

}
