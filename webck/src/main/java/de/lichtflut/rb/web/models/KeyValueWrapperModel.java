/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.models;

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;

/**
 * [TODO Insert description here.
 *
 * Created: Aug 30, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class KeyValueWrapperModel implements IModel<String> {

	private IRBEntity entity;
	/**
	 * Constructor.
	 * @param field - instance of {@link IRBField} which represents a Key-Value-Pair
	 */
	public KeyValueWrapperModel(final IRBField field){
	}

	/**
	 * Constructor.
	 * @param entity - instance of {@link IRBEntity} to which a new {@link IRBField} will be added.
	 */
	public KeyValueWrapperModel(final IRBEntity entity){
		this.entity = entity;
	}

	@Override
	public void detach() {
		// Do nothing
	}

	@Override
	public String getObject() {
		return "";
	}

	@Override
	public void setObject(final String object) {
		// TODO Auto-generated method stub
	}



}
