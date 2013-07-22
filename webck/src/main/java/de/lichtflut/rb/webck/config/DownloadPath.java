package de.lichtflut.rb.webck.config;

import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  Path for a download, e.g. of Assets, VSpecs or RSFs
 * </p>
 *
 * <p>
 *  Created July 15, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class DownloadPath extends AbstractPath<DownloadPath> {

    public DownloadPath(String ctxPath, String domain) {
        super(ctxPath, domain);
    }

    // ----------------------------------------------------

    public DownloadPath perspective() {
        return service("viewspecs/perspectives");
    }

    public DownloadPath entity() {
        return service("graphs");
    }

    public DownloadPath withID(String id) {
        return param("id", id);
    }

    public DownloadPath withQN(QualifiedName qn) {
        return paramEncoded("qn", qn);
    }

}
