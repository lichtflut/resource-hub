/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config.selection;

import java.io.Serializable;

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

	private final SNSelectionParameter parameter;
	
	private ParameterType type;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public QueryParamUIModel(SNSelectionParameter parameter, ParameterType type) {
		this.parameter = parameter;
		this.type = type;
	}
	
	/**
	 * @param field
	 * @param value
	 */
	public QueryParamUIModel(SNSelectionParameter parameter) {
		this.parameter = parameter;
		
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
	}

	// ----------------------------------------------------

	/**
	 * @return the type
	 */
	public ParameterType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(ParameterType type) {
		this.type = type;
		switch (type) {
		case RESOURCE_TYPE:
			setField(RDF.TYPE);
			break;
		case ANY_FIELD_RELATION:
		case ANY_FIELD_VALUE:
			parameter.unsetField();
			break;
		}
	}

	/**
	 * @return the field
	 */
	public ResourceID getField() {
		switch (type) {
		case RESOURCE_TYPE:
			return RDF.TYPE;
		case ANY_FIELD_RELATION:
		case ANY_FIELD_VALUE:
			return null;
		default:
			return parameter.getField();
		}
	}

	/**
	 * @param field the field to set
	 */
	public void setField(ResourceID field) {
		parameter.setField(field);
	}

	/**
	 * @return the value
	 */
	public SemanticNode getValue() {
		return parameter.getTerm();
	}

	/**
	 * @param term the value to set
	 */
	public void setValue(SemanticNode term) {
		parameter.setTerm(term);
	}
	
	// ----------------------------------------------------
	
	static boolean isResourceReference(SemanticNode node) {
		if (node.isResourceNode()) {
			return true;
		} else {
			return QualifiedName.isUri(node.asValue().getStringValue());
		}
	}
	
}