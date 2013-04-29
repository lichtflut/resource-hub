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
package de.lichtflut.rb.webck.components.widgets.config.selection;

import java.io.Serializable;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.viewspec.impl.SNSelectionParameter;

/**
 * <p>
 *  Specifivation object of a single paramter of a selection.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
class QueryParamUIModel implements Serializable, IDetachable {

	private final IModel<SNSelectionParameter> model;

	private ParameterType type;

	// ----------------------------------------------------

	/**
	 * @param field
	 * @param value
	 */
	public QueryParamUIModel(final IModel<SNSelectionParameter> model) {
		this.model = model;
	}

	// ----------------------------------------------------

	/**
	 * @return the type
	 */
	public ParameterType getType() {
		if (type != null) {
			return type;
		}
		SNSelectionParameter parameter = this.model.getObject();
		boolean isRelation = isResourceReference(parameter.getTerm());
		if (parameter.getField() == null) {
			if (isRelation) {
				type = ParameterType.ANY_FIELD_RELATION;
			} else {
				type = ParameterType.ANY_FIELD_VALUE;
			}
		} else if (RDF.TYPE.equals(parameter.getField())) {
			type = ParameterType.RESOURCE_TYPE;
		} else if (isRelation) {
			type = ParameterType.SPECIAL_FIELD_RELATION;
		} else {
			type = ParameterType.SPECIAL_FIELD_VALUE;
		}
		return type;
	}

	/**
	 * @param type Set the type explicitly.
	 */
	public void setType(final ParameterType type) {
		this.type = type;
		switch (type) {
			case RESOURCE_TYPE:
				setField(RDF.TYPE);
				break;
			case ANY_FIELD_RELATION:
			case ANY_FIELD_VALUE:
				model.getObject().unsetField();
				break;
			default:
				break;
		}
	}

	/**
	 * @return the field
	 */
	public ResourceID getField() {
		switch (getType()) {
			case RESOURCE_TYPE:
				return RDF.TYPE;
			case ANY_FIELD_RELATION:
			case ANY_FIELD_VALUE:
				return null;
			default:
				return model.getObject().getField();
		}
	}

	/**
	 * @param field the field to set
	 */
	public void setField(final ResourceID field) {
		model.getObject().setField(field);
	}

	/**
	 * @return the value
	 */
	public SemanticNode getValue() {
		switch (getType()) {
			case RESOURCE_TYPE:
			case SPECIAL_FIELD_RELATION:
			case ANY_FIELD_RELATION:
				return getResourceValue();
			default:
				return model.getObject().getTerm();
		}
	}

	/**
	 * @param term the value to set
	 */
	public void setValue(final SemanticNode term) {
		model.getObject().setTerm(term);
	}

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void detach() {
		type = null;
	}

	// ----------------------------------------------------

	private ResourceID getResourceValue() {
		SemanticNode term = model.getObject().getTerm();
		if (term != null && term.isResourceNode()) {
			return term.asResource();
		} else {
			return null;
		}
	}

	private boolean isResourceReference(final SemanticNode node) {
		if (node == null) {
			return false;
		} else {
			return node.isResourceNode();
		}
	}

}