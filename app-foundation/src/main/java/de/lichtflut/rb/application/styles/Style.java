package de.lichtflut.rb.application.styles;

import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * <p>
 *  Abstract base for styles of RB applications.
 * </p>
 *
 * <p>
 * Created 13.07.12
 * </p>
 *
 * @author Oliver Tigges
 */
public interface Style {

    void addStyle(IHeaderResponse response);

}
