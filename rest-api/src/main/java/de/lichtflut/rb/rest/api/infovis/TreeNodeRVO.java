package de.lichtflut.rb.rest.api.infovis;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.ValueNode;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

/**
 * <p>
 *  Object representing a single tree node.
 * </p>
 * <p>
 *  Created Jan 11, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class TreeNodeRVO implements Comparable<TreeNodeRVO> {

    private String id;
    private String name;
    private String data;

    @JsonIgnore
    private ValueNode orderNumber;

    private Collection<TreeNodeRVO> children = new ArrayList<TreeNodeRVO>();

    // ----------------------------------------------------

    public TreeNodeRVO() {
    }

    public TreeNodeRVO(ResourceNode resource, Locale locale) {
        this.id = resource.toURI();
        this.name = ResourceLabelBuilder.getInstance().getLabel(resource, locale);

        SemanticNode serialNumber = SNOPS.fetchObject(resource, Aras.HAS_SERIAL_NUMBER);
        if (serialNumber != null && serialNumber.isValueNode()) {
            orderNumber = serialNumber.asValue();
        }
    }

    // ----------------------------------------------------


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Collection<TreeNodeRVO> getChildren() {
        return children;
    }

    public void addChildren(Collection<TreeNodeRVO> children) {
        this.children.addAll(children);
    }

    public void addChildren(TreeNodeRVO... children) {
        Collections.addAll(this.children, children);
    }

    // ----------------------------------------------------

    @Override
    public int compareTo(TreeNodeRVO other) {
        if (orderNumber != null) {
            if (other.orderNumber != null) {
                return orderNumber.compareTo(other.orderNumber);
            } else {
                // only other has no serial number
                return -1;
            }
        } else {
            if (other.orderNumber != null) {
                // only this has no serial number
                return 1;
            } else {
                // no serial number
                return 0;
            }
        }
    }
}
