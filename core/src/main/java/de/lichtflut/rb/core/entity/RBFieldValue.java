package de.lichtflut.rb.core.entity;

import java.io.Serializable;

import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.StatementMetaInfo;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.StatementOrigin;

import de.lichtflut.rb.core.entity.impl.AbstractRBField;

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

	@SuppressWarnings("unused")
	private final RBField field;

	private Statement stmt;

	private Object value;

	// ----------------------------------------------------

	public RBFieldValue(final RBField field, final Statement stmt) {
		this.field = field;
		this.stmt = stmt;
		this.value = stmt.getObject();
	}

	public RBFieldValue(final RBField field, final Object value) {
		this.field = field;
		this.value = value;
	}

	public RBFieldValue(final AbstractRBField field) {
		this.field = field;
	}

	// ----------------------------------------------------

	public Object getValue() {
		return value;
	}

	public void setValue(final Object newValue) {
		this.value = newValue;
	}

	public void setValue(final SemanticNode node) {
		this.value = node;
	}

	public void setValue(final RBEntity entity) {
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
