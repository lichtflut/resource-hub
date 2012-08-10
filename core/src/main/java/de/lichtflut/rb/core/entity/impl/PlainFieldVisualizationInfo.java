package de.lichtflut.rb.core.entity.impl;

import de.lichtflut.rb.core.entity.FieldVisualizationInfo;

/**
 * <p>
 *  Simple implementation of a field's visualization information.
 * </p>
 * <p/>
 * <p>
 * Created 10.08.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class PlainFieldVisualizationInfo implements FieldVisualizationInfo {

    private boolean embedded = false;
    private boolean floating = false;
    private String style = "";

    // ----------------------------------------------------

    @Override
    public boolean isEmbedded() {
        return embedded;
    }

    @Override
    public boolean isFloating() {
        return floating;
    }

    @Override
    public String getStyle() {
        return style;
    }

    // ----------------------------------------------------


    public void setEmbedded(boolean embedded) {
        this.embedded = embedded;
    }

    public void setFloating(boolean floating) {
        this.floating = floating;
    }

    public void setStyle(String style) {
        this.style = style;
    }

}
