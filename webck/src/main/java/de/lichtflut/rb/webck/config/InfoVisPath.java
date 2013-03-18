package de.lichtflut.rb.webck.config;

import com.sun.jersey.core.util.Base64;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.QualifiedName;

import java.util.HashMap;
import java.util.Map;

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
public class InfoVisPath {

    private final String ctxPath;

    private String domain;

    private String service;

    private final Map<String, Object> params = new HashMap<String, Object>();

    // ----------------------------------------------------

    public InfoVisPath(String ctxPath) {
        this.ctxPath = ctxPath;
    }

    public InfoVisPath(String ctxPath, String domain) {
        this.ctxPath = ctxPath;
        this.domain = domain;
    }

    // ----------------------------------------------------

    public InfoVisPath tree() {
        this.service = "tree";
        return this;
    }

    // ----------------------------------------------------

    public InfoVisPath domain(String domain) {
        this.domain = domain;
        return this;
    }

    public InfoVisPath append(String param, Object value) {
        this.params.put(param, value);
        return this;
    }

    public InfoVisPath appendEncoded(String param, Object value) {
        this.params.put(param, encode(value));
        return this;
    }

    public InfoVisPath withRoot(String root) {
        this.params.put("root", encode(root));
        return this;
    }

    public InfoVisPath withRoot(ResourceID root) {
        this.params.put("root", encode(root));
        return this;
    }

    public InfoVisPath ofType(String type) {
        this.params.put("type", type);
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
