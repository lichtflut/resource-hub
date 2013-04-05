package de.lichtflut.rb.rest.api.common;

/**
 * <p>
 *  Represents a field with id, label and value.
 * </p>
 *
 * <p>
 *  Created Mar 15, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class FieldRVO {

    private String id;
    private String label;
    private String value;

    // ----------------------------------------------------

    public String getId() {
        return id;
    }

    public FieldRVO setId(String id) {
        this.id = id;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public FieldRVO setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getValue() {
        return value;
    }

    public FieldRVO setValue(String value) {
        this.value = value;
        return this;
    }
}
