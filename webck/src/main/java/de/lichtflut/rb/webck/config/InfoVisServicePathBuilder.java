package de.lichtflut.rb.webck.config;

/**
 * <p>
 *  Provider for URIs of the info vis REST service.
 * </p>
 *
 * <p>
 *  Created Jan 11, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public interface InfoVisServicePathBuilder {

    InfoVisPath create(String domain);

}
