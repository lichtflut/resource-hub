/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
