/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.util.List;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.api.INewRBEntityManagement;
import de.lichtflut.rb.core.schema.model.IRBEntity;

/**
 * <p>
 * This class provides a static {@link INewRBEntityManagement} for testing purposes.
 * </p>
 * Created: Aug 18, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class MockRBEntityManagement implements INewRBEntityManagement {

	private List<IRBEntity> entities = MockNewRBEntityFactory.getListOfNewRBEntities();

	@Override
	public IRBEntity find(final ResourceID resourceID) {
		for (IRBEntity e : entities) {
			if(e.getID().equals(resourceID)){
				return e;
			}
		}
		return null;
	}

	@Override
	public List<IRBEntity> findAllByType(final ResourceID id) {
		return entities;
	}

	@Override
	public IRBEntity store(final IRBEntity entity) {
		if(entities.contains(entity)){
			entities.remove(entity);
			entities.add(entity);

		}else{
			entities.add(entity);
		}
		return entity;
	}

	@Override
	public void delete(final IRBEntity entity) {
		entities.remove(entity);
	}

}
