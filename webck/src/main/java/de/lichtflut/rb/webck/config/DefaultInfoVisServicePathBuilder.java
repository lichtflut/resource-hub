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
