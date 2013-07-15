package de.lichtflut.rb.webck.config;

/**
 * <p>
 *  Path builder for download URIs.
 * </p>
 *
 * <p>
 *  Created July 15, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public interface DownloadPathBuilder {

    DownloadPath createDownloadPath(String domain);

}
