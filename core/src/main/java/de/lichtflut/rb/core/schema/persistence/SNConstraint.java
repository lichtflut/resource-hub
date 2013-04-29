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
package de.lichtflut.rb.core.schema.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNBoolean;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.Datatype;

/**
 * <p>
 *  Semantic node representing a constraint.
 * </p>
 *
 * <p>
 * Created 31.08.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNConstraint extends ResourceView {

	public SNConstraint() {
		super();
	}

	public SNConstraint(final QualifiedName qn) {
		super(qn);
	}

	public SNConstraint(final ResourceNode resource) {
		super(resource);
	}

	// ----------------------------------------------------

	public String getName() {
		return stringValue(RBSchema.HAS_NAME);
	}

	public SNConstraint setName(final String name) {
		if (name != null) {
			setValue(RBSchema.HAS_NAME, name);
		} else {
			removeValues(RBSchema.HAS_NAME);
		}
		return this;
	}

	// ----------------------------------------------------

	public String getLiteralConstraint() {
		return stringValue(RBSchema.HAS_LITERAL_CONSTRAINT);
	}

	public void setLiteralConstraint(final String pattern) {
		if ((pattern != null) && !pattern.isEmpty()) {
			setValue(RBSchema.HAS_LITERAL_CONSTRAINT, new SNText(pattern));
		} else {
			removeValues(RBSchema.HAS_LITERAL_CONSTRAINT);
		}
	}

	// ----------------------------------------------------

	public ResourceID getTypeConstraint() {
		SemanticNode referenceNode = SNOPS.singleObject(this, RBSchema.HAS_TYPE_CONSTRAINT);
		if (null == referenceNode) {
			return null;
		}
		return referenceNode.asResource();
	}

	public void setTypeConstraint(final ResourceID type) {
		setValue(RBSchema.HAS_TYPE_CONSTRAINT, type);
	}

	// ----------------------------------------------------

	public boolean isPublic() {
		return Boolean.TRUE.equals(booleanValue(RBSchema.IS_PUBLIC_CONSTRAINT));
	}

	public void setPublic(final boolean isPublic) {
		setValue(RBSchema.IS_PUBLIC_CONSTRAINT, new SNBoolean(isPublic));
		if(true){
			SNOPS.associate(this, RDF.TYPE, RBSchema.PUBLIC_CONSTRAINT);
		}
	}

	// ----------------------------------------------------

	public List<Datatype> getApplicableDatatypes() {
		List<Datatype> datatypes = new ArrayList<Datatype>();
		for (SemanticNode snType : SNOPS.objects(this, RBSchema.HAS_DATATYPE)) {
			datatypes.add(Datatype.valueOf(snType.asValue().getStringValue()));
		}
		return datatypes;
	}

	public void setApplicableDatatypes(final List<Datatype> datatypes) {
		Collection<SNText> typeNodes = new ArrayList<SNText>();
		for (Datatype datatype : datatypes) {
			typeNodes.add(new SNText(datatype.name()));
		}
		SNOPS.assure(this, RBSchema.HAS_DATATYPE, typeNodes);
	}

	// ----------------------------------------------------


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Constraint '").append(getQualifiedName()).append("'");
		sb.append(" name: ").append(getName());
		sb.append(" is public: ").append(isPublic());
		return sb.toString();
	}
}
