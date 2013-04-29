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

import com.sun.jersey.core.util.Base64;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  Builder object for the query path,
 * </p>
 * <p/>
 * <p>
 * Created 12.03.13
 * </p>
 *
 * @author Oliver Tigges
 */
public class QueryPath {

    private final String ctxPath;

    private String domain;

    private String service;

    private final Map<String, Object> params = new HashMap<String, Object>();

    // ----------------------------------------------------

    public QueryPath(String ctxPath) {
        this.ctxPath = ctxPath;
    }

    public QueryPath(String ctxPath, String domain) {
        this.ctxPath = ctxPath;
        this.domain = domain;
    }

    // ----------------------------------------------------

    public QueryPath queryResources() {
        this.service = "resources";
        return this;
    }

    public QueryPath queryEntities() {
        this.service = "entities";
        return this;
    }

    public QueryPath queryClasses() {
        this.service = "classes";
        return this;
    }

    public QueryPath queryProperties() {
        this.service = "properties";
        return this;
    }

    public QueryPath queryUsers() {
        this.service = "users";
        return this;
    }

    // ----------------------------------------------------

    public QueryPath domain(String domain) {
        this.domain = domain;
        return this;
    }

    public QueryPath append(String param, Object value) {
        this.params.put(param, value);
        return this;
    }

    public QueryPath appendEncoded(String param, Object value) {
        this.params.put(param, encode(value));
        return this;
    }

    public QueryPath ofType(String type) {
        this.params.put("type", encode(type));
        return this;
    }

    public QueryPath inScope(String scope) {
        this.params.put("scope", encode(scope));
        return this;
    }

    public QueryPath withSuperClass(String clazz) {
        this.params.put("superclass", encode(clazz));
        return this;
    }

    public QueryPath withSuperProperty(String clazz) {
        this.params.put("superproperty", encode(clazz));
        return this;
    }

    // ----------------------------------------------------

    public String toURI() {
        StringBuilder sb = new StringBuilder(ctxPath);
        sb.append("/domains/").append(domain);
        sb.append("/").append(service);
        boolean first = !ctxPath.contains("?");
        for (String name : params.keySet()) {
            if (first) {
                sb.append("?");
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(name);
            sb.append("=");
            sb.append(params.get(name));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return toURI();
    }

    // ----------------------------------------------------

    private String encode(Object value) {
        if (value != null) {
            return new String(Base64.encode(value.toString()));
        } else {
            return "";
        }
    }
}
