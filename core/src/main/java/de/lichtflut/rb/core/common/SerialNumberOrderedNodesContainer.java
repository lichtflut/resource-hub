package de.lichtflut.rb.core.common;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.structure.OrderBySerialNumber;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  DESCRITPION.
 * </p>
 *
 * <p>
 *  Created 28.09.12
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class SerialNumberOrderedNodesContainer {

    public void moveUp(ResourceNode node, int positions) {
        final List<? extends ResourceNode> list = getList();
        int currentIndex = list.indexOf(node);
        int newIndex = currentIndex - positions;
        if (newIndex < 0) {
            newIndex = 0;
        }
        swap(node, list.get(newIndex));
    }

    public void moveDown(ResourceNode node, int positions) {
        final List<? extends ResourceNode> list = getList();
        int currentIndex = list.indexOf(node);
        int newIndex = currentIndex + positions;
        if (newIndex >= list.size()) {
            newIndex = list.size() - 1;
        }
        swap(node, list.get(newIndex));
    }

    public void swap(ResourceNode a, ResourceNode b) {
        int aSN = getSerialNumber(a);
        int bSN = getSerialNumber(b);
        setSerialNumnber(a, bSN);
        setSerialNumnber(b, aSN);
        ensureCorrectness(getList());
    }

    // ----------------------------------------------------

    protected abstract List<? extends ResourceNode> getList();

    // ----------------------------------------------------

    private void setSerialNumnber(ResourceNode n, int sn) {
        SNOPS.assure(n, Aras.HAS_SERIAL_NUMBER, sn);
    }

    private int getSerialNumber(ResourceNode n) {
        SemanticNode sn = SNOPS.fetchObject(n, Aras.HAS_SERIAL_NUMBER);
        if (sn != null) {
            return sn.asValue().getIntegerValue().intValue();
        } else {
            return -1;
        }
    }

    private void ensureCorrectness(List<? extends ResourceNode> list) {
        Collections.sort(list, new OrderBySerialNumber());
        int idx = 1;
        for (ResourceNode node : list) {
            SNOPS.assure(node, Aras.HAS_SERIAL_NUMBER, idx++);
        }
    }

}
