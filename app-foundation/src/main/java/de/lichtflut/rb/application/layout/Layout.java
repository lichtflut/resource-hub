package de.lichtflut.rb.application.layout;

import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * <p>
 *  Layout interface for RB applications.
 * </p>
 *
 * <p>
 * Created 13.07.12
 * </p>
 *
 * @author Oliver Tigges
 */
public interface Layout {

    void addLayout(IHeaderResponse response);

}
