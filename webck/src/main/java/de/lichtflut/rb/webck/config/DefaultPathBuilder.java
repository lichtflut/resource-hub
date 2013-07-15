package de.lichtflut.rb.webck.config;

import org.apache.wicket.request.cycle.RequestCycle;

/**
 * <p>
 *  Default builder for paths to HTTP resources.
 * </p>
 *
 * <p>
 *  Created July 15, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class DefaultPathBuilder implements DownloadPathBuilder, InfoVisServicePathBuilder {

    @Override
    public DownloadPath createDownloadPath(String domain) {
        return new DownloadPath(context(), domain);
    }

    @Override
    public InfoVisPath createInfoVisPath(String domain) {
        return new InfoVisPath(context(), domain);
    }

    // ----------------------------------------------------

    private String context() {
        return RequestCycle.get().getRequest().getContextPath() + "/service";
    }

}
