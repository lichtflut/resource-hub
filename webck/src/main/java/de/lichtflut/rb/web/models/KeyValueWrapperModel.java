/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.models;

import org.apache.wicket.model.IModel;

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

	private IRBField field;

	/**
	 * Constructor.
	 * @param field - instance of {@link IRBField} which represents a Key-Value-Pair
	 */
	public KeyValueWrapperModel(final IRBField field){
		this.field = field;
	}
	@Override
	public void detach() {
		// Do nothing
	}

	@Override
	public String getObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setObject(final String object) {
		// TODO Auto-generated method stub
	}



}
