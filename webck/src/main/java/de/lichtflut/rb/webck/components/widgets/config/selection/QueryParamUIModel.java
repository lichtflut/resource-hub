/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config.selection;

import java.io.Serializable;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.naming.QualifiedName;

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
class QueryParamUIModel implements Serializable {

	private final IModel<SNSelectionParameter> model;
	
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
		SNSelectionParameter parameter = this.model.getObject();
		boolean isRelation = isResourceReference(parameter.getTerm());
		
		final ParameterType type;
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
	 * @param type the type to set
	 */
	public void setType(ParameterType type) {
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
		return model.getObject().getTerm();
	}

	/**
	 * @param term the value to set
	 */
	public void setValue(SemanticNode term) {
		model.getObject().setTerm(term);
	}
	
	// ----------------------------------------------------
	
	static boolean isResourceReference(SemanticNode node) {
		if (node == null) {
			return false;
		} else if (node.isResourceNode()) {
			return true;
		} else {
			return QualifiedName.isUri(node.asValue().getStringValue());
		}
	}
	
}