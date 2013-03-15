package de.lichtflut.rb.rest.api.infovis;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.common.SchemaIdentifyingType;
import de.lichtflut.rb.rest.api.common.QuickInfoRVO;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
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
 *
 * <p>
 *  Created Jan 11, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class TreeNodeRVO implements Comparable<TreeNodeRVO> {

    private String id;
    private String name;
    private String[] types;
    private String primaryType;

    private QuickInfoRVO quickInfo;

    @JsonIgnore
    private ValueNode orderNumber;

    private Collection<TreeNodeRVO> children = new ArrayList<TreeNodeRVO>();


    // ----------------------------------------------------

    public TreeNodeRVO() {
    }

    public TreeNodeRVO(ResourceNode resource, Locale locale) {
        ResourceLabelBuilder lb = ResourceLabelBuilder.getInstance();
        this.id = resource.toURI();
        this.name = lb.getLabel(resource, locale);
        this.types = toStrings(SNOPS.objects(resource, RDF.TYPE));
        this.primaryType = lb.getLabel(SchemaIdentifyingType.of(resource), locale);

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

    public String[] getTypes() {
        return types;
    }

    public Collection<TreeNodeRVO> getChildren() {
        return children;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public void addChildren(Collection<TreeNodeRVO> children) {
        this.children.addAll(children);
    }

    public void addChildren(TreeNodeRVO... children) {
        Collections.addAll(this.children, children);
    }

    public QuickInfoRVO getQuickInfo() {
        return quickInfo;
    }

    public void setQuickInfo(QuickInfoRVO quickInfo) {
        this.quickInfo = quickInfo;
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

    // ----------------------------------------------------

    private String[] toStrings(Collection<SemanticNode> nodes) {
        String[] result = new String[nodes.size()];
        int idx = 0;
        for (SemanticNode node : nodes) {
            if (node.isResourceNode()) {
                result[idx] = node.asResource().toURI();
            } else {
                result[idx] = node.asValue().getStringValue();
            }
            idx++;
        }
        return result;
    }

    private String toString(SemanticNode node) {
        if (node.isResourceNode()) {
            return node.asResource().toURI();
        } else {
            return node.asValue().getStringValue();
        }
    }

}
