package de.lichtflut.rb.webck.config;

import com.sun.jersey.core.util.Base64;
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
    public String getTree(String domain, String entity) {
        final StringBuilder sb = preparePathBuilder(domain);
        sb.append("/tree");
        if (entity != null) {
            sb.append("?root=");
            sb.append(encode(entity));
        }
        return sb.toString();
    }

    // ----------------------------------------------------

    private StringBuilder preparePathBuilder(String domain) {
        final String ctx = RequestCycle.get().getRequest().getContextPath();
        final StringBuilder sb = new StringBuilder(ctx);
        sb.append("/service/infovis");
        sb.append("/domains/").append(domain);
        return sb;
    }

    String encode(String orig) {
        return new String(Base64.encode(orig));
    }

}
