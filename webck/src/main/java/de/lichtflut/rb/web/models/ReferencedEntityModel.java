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

	private IModel<IRBEntity> entity;

	/**
	 * Default Constructor.
	 * @param entity - instance of {@link IRBEntity}
	 */
	public ReferencedEntityModel(final IModel<IRBEntity> entity) {
		this.entity = entity;
	}

	@Override
	public ResourceID getObject() {
		if (entity != null && entity.getObject() != null) {
			return entity.getObject().getID();
		} else {
			return null;
		}
	}

	@Override
	public void setObject(final ResourceID id) {
		entity.setObject(resolve(id));
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
