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
package de.lichtflut.rb.core.schema.model.impl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.Namespace;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.infra.exceptions.NoLongerSupportedException;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;

/**
 * <p>
 * Implementation of {@link Constraint}.
 * </p>
 * <p>
 * All but public constraints should be constructed by using the appropriate <code>buildXXX</code>
 * -methods to ensure integrity
 * </p>
 * <p>
 * Public-constraints must set the following properties:
 * <ul>
 * <li>
 * Name</li>
 * <li>
 * literal constraint</li>
 * <li>
 * setPublic</li>
 * <li>
 * applicable Datatypes</li>
 * </ul>
 * </p>
 * 
 * 
 * </p> Created: May 25, 2012
 * 
 * @author Ravi Knox
 */
public class ConstraintImpl implements Constraint {

	private QualifiedName qn;

	private String name;

	private boolean isPublic;

	private String literalConstraint;

	private ResourceID resourceConstraint;

	private List<Datatype> applicableDatatypes;

	// ---------------- Constructor -------------------------

	@Deprecated
	public ConstraintImpl(final ResourceNode node) {
		throw new NoLongerSupportedException();
	}

	/**
	 * Constructor.
	 */
	public ConstraintImpl(final QualifiedName qn) {
		this.qn = qn;
	}

	/**
	 * Default constructor for new constraints.
	 */
	public ConstraintImpl() {
		this(QualifiedName.from(Namespace.UUID, UUID.randomUUID().toString()));
	}

	// ------------------------------------------------------

	public void buildReferenceConstraint(final ResourceID reference, final boolean isLiteralReference) {
		this.setPublic(false);
		this.setTypeConstraint(reference);
	}

	public void buildLiteralConstraint(final String pattern) {
		this.setPublic(false);
		this.setLiteralConstraint(pattern);
	}

	// ------------------------------------------------------


	@Override
	public QualifiedName getQualifiedName() {
		return qn;
	}

	@Override
	public String getName() {
		if (name != null) {
			return name;
		} else {
			return "<unnamed>";
		}
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(final boolean isPublic) {
		this.isPublic = isPublic;
	}

	// ----------------------------------------------------

	@Override
	public boolean isLiteral() {
		return literalConstraint != null;
	}

	@Override
	public String getLiteralConstraint() {
		return literalConstraint;
	}

	public void setLiteralConstraint(final String pattern) {
		this.literalConstraint = pattern;
	}

	@Override
	public ResourceID getTypeConstraint() {
		return resourceConstraint;
	}

	public void setTypeConstraint(final ResourceID resourceConstraint) {
		this.resourceConstraint = resourceConstraint;
	}

	@Override
	public List<Datatype> getApplicableDatatypes() {
		if (applicableDatatypes != null) {
			return applicableDatatypes;
		} else {
			return Collections.emptyList();
		}
	}

	public void setApplicableDatatypes(final List<Datatype> datatypes) {
		this.applicableDatatypes = datatypes;
	}

	// ------------------------------------------------------

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Constraint id: " + qn);
		sb.append(", name: " + getName());
		sb.append(", is public: " + isPublic());
		sb.append(", is literal: " + isLiteral());
		sb.append(", applicable datatypes: " + getApplicableDatatypes());
		if(isLiteral()){
			sb.append(", has pattern: " + getLiteralConstraint());
		}else {
			sb.append(", has type constraint: " + getTypeConstraint());
		}
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicableDatatypes == null) ? 0 : applicableDatatypes.hashCode());
		result = prime * result + (isPublic ? 1231 : 1237);
		result = prime * result + ((literalConstraint == null) ? 0 : literalConstraint.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((qn == null) ? 0 : qn.hashCode());
		result = prime * result + ((resourceConstraint == null) ? 0 : resourceConstraint.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ConstraintImpl)) {
			return false;
		}
		ConstraintImpl other = (ConstraintImpl) obj;
		if (applicableDatatypes == null) {
			if (other.applicableDatatypes != null) {
				return false;
			}
		} else if (!applicableDatatypes.equals(other.applicableDatatypes)) {
			return false;
		}
		if (isPublic != other.isPublic) {
			return false;
		}
		if (literalConstraint == null) {
			if (other.literalConstraint != null) {
				return false;
			}
		} else if (!literalConstraint.equals(other.literalConstraint)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (qn == null) {
			if (other.qn != null) {
				return false;
			}
		} else if (!qn.equals(other.qn)) {
			return false;
		}
		if (resourceConstraint == null) {
			if (other.resourceConstraint != null) {
				return false;
			}
		} else if (!resourceConstraint.equals(other.resourceConstraint)) {
			return false;
		}
		return true;
	}

}
