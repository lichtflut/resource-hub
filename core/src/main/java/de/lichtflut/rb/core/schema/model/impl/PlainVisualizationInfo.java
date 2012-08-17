package de.lichtflut.rb.core.schema.model.impl;

import de.lichtflut.rb.core.schema.model.VisualizationInfo;

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
public class PlainVisualizationInfo implements VisualizationInfo {

    private boolean embedded = false;
    private boolean floating = false;
    private String style = "";

    // ----------------------------------------------------

    /**
     * Default constructor. Creates a non embedded, not floating info.
     */
    public PlainVisualizationInfo() {
    }

    /**
     * Copy constructor.
     */
    public PlainVisualizationInfo(VisualizationInfo other) {
        this.embedded = other.isEmbedded();
        this.floating = other.isFloating();
        this.style = other.getStyle();
    }

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

    // ----------------------------------------------------

    @Override
    public String toString() {
        return "visualize {" +
                "embedded=" + embedded +
                ", floating=" + floating +
                ", style='" + style + '\'' +
                '}';
    }
}
