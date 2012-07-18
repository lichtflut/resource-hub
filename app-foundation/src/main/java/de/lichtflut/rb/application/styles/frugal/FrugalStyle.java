package de.lichtflut.rb.application.styles.frugal;

import de.lichtflut.rb.application.styles.Style;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * <p>
 *  The frugal default style of RB applications.
 * </p>
 *
 * <p>
 * Created 13.07.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class FrugalStyle implements Style {

    public static ResourceReference STYLE =
            new JavaScriptResourceReference(FrugalStyle.class, "frugal-style-1.0.css");

    public static ResourceReference WIDGETS =
            new JavaScriptResourceReference(FrugalStyle.class, "frugal-widgets-1.0.css");

    public static ResourceReference INFOVIS =
            new JavaScriptResourceReference(FrugalStyle.class, "frugal-infovis-1.0.css");


    // ----------------------------------------------------

    @Override
    public void addStyle(IHeaderResponse response) {
        response.renderCSSReference(STYLE);
        response.renderCSSReference(WIDGETS);
        response.renderCSSReference(INFOVIS);
    }
}
