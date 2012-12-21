package de.lichtflut.rb.core.entity;

import de.lichtflut.rb.core.entity.impl.AbstractRBField;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.StatementMetaInfo;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.StatementOrigin;

import java.io.Serializable;

/**
 * <p>
 *  Represents one value of an RBField which can be one of:
 *
 *  <ul>
 *      <li>Value node</li>
 *      <li>Resource ID</li>
 *      <li>referenced/embedded RBEntity</li>
 *  </ul>
 * </p>
 *
 * <p>
 *  Created 21.12.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBFieldValue implements Serializable {

    private RBField field;

    private Statement stmt;

    private Object value;

    // ----------------------------------------------------

    public RBFieldValue(RBField field, Statement stmt) {
        this.field = field;
        this.stmt = stmt;
        this.value = stmt.getObject();
    }

    public RBFieldValue(RBField field, Object value) {
        this.field = field;
        this.value = value;
    }

    public RBFieldValue(AbstractRBField field) {
        this.field = field;
    }

    // ----------------------------------------------------

    public Object getValue() {
        return value;
    }

    public void setValue(Object newValue) {
        this.value = newValue;
    }

    public void setValue(SemanticNode node) {
        this.value = node;
    }

    public void setValue(RBEntity entity) {
        this.value = entity;
    }

    public StatementMetaInfo getStatementMetaInfo() {
        if (stmt != null) {
            return stmt.getMetaInfo();
        } else {
            return null;
        }
    }

    // ----------------------------------------------------

    public boolean isRemoved() {
        return value == null;
    }

    public boolean isAdded() {
        return stmt == null;
    }

    public boolean isInherited() {
        StatementMetaInfo metaInfo = getStatementMetaInfo();
        return metaInfo != null && StatementOrigin.INHERITED.equals(metaInfo.getOrigin());
    }

}
