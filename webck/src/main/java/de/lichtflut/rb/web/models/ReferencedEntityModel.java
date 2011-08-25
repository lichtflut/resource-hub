/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.models;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.IRBEntity;

/**
 * [TODO Insert description here.
 *
 * Created: Aug 25, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public abstract class ReferencedEntityModel implements IModel<ResourceID> {

	private IRBEntity entity;

	/**
	 * Default Constructor.
	 * @param entity - instance of {@link IRBEntity}
	 */
	public ReferencedEntityModel(final IRBEntity entity) {
		this.entity = entity;
	}

	@Override
	public ResourceID getObject() {
		return entity.getID();
	}

	@Override
	public void setObject(final ResourceID id) {
		this.entity = resolve(id);
	}

	@Override
	public void detach() {
	}

	/**
	 * Resolves a {@link ResourceID} and returns an instance of {@link IRBEntity}.
	 * @param id - {@link ResourceID}
	 * @return {@link IRBEntity}
	 */
	public abstract IRBEntity resolve(final ResourceID id);

}
