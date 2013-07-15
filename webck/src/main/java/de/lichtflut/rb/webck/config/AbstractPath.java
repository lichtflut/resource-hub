package de.lichtflut.rb.webck.config;

import com.sun.jersey.core.util.Base64;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.QualifiedName;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  Abstract path to some HTTP resource.
 * </p>
 *
 * <p>
 *  Created July 15, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class AbstractPath<T extends AbstractPath> {

    private final String ctxPath;

    private String domain;

    private String service;

    private final Map<String, Object> params = new HashMap<String, Object>();

    // ----------------------------------------------------

    public AbstractPath(String ctxPath) {
        this.ctxPath = ctxPath;
    }

    public AbstractPath(String ctxPath, String domain) {
        this.ctxPath = ctxPath;
        this.domain = domain;
    }

    // ----------------------------------------------------

    public T domain(String domain) {
        this.domain = domain;
        return (T) this;
    }

    public T service(String service) {
        this.service = service;
        return (T) this;
    }

    public T param(String param, Object value) {
        this.params.put(param, value);
        return (T) this;
    }

    public T paramEncoded(String param, Object value) {
        this.params.put(param, encode(value));
        return (T) this;
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

    protected String encode(Object value) {
        if (value instanceof String) {
            return new String(Base64.encode(value.toString()));
        } else if (value instanceof ResourceID) {
            return encode( ((ResourceID)value).toURI());
        } else if (value instanceof QualifiedName) {
            return encode( ((QualifiedName)value).toURI());
        } else if (value != null) {
            return encode(value.toString());
        } else {
            return "";
        }
    }


}
