/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config.selection;

import de.lichtflut.rb.core.viewspec.impl.SNSelectionParameter;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SemanticNode;

import java.io.Serializable;

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
	public QueryParamUIModel(IModel<SNSelectionParameter> model) {
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
	public void setType(ParameterType type) {
		this.type = type;
		switch (type) {
		case RESOURCE_TYPE:
			setField(RDF.TYPE);
			break;
		case ANY_FIELD_RELATION:
		case ANY_FIELD_VALUE:
			model.getObject().unsetField();
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
	public void setField(ResourceID field) {
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
	public void setValue(SemanticNode term) {
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
	
	private boolean isResourceReference(SemanticNode node) {
		if (node == null) {
			return false;
		} else {
			return node.isResourceNode();
		}
	}

}