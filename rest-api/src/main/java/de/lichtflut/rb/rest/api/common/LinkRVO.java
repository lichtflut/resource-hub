package de.lichtflut.rb.rest.api.common;

/**
 * <p>
    Common RVO for links.
 * </p>
 *
 * <p>
 *  Created June 25, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class LinkRVO {

    private String rel;

    private String href;

    // ----------------------------------------------------

    public LinkRVO(String rel, String href) {
        this.rel = rel;
        this.href = href;
    }

    // ----------------------------------------------------

    public String getRel() {
        return rel;
    }

    public String getHref() {
        return href;
    }

}
