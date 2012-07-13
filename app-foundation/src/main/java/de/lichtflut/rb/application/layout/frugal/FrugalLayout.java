package de.lichtflut.rb.application.layout.frugal;

import de.lichtflut.rb.application.layout.Layout;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * <p>
 *  The default layout for an RB Application
 * </p>
 *
 * <p>
 * Created 13.07.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class FrugalLayout implements Layout {

    public static ResourceReference SITE_STRUCTURE =
            new JavaScriptResourceReference(FrugalLayout.class, "frugal-site-structure-0.1.css");

    // ----------------------------------------------------

    @Override
    public void addLayout(IHeaderResponse response) {
        response.renderCSSReference(SITE_STRUCTURE);
    }
}

