/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.api.RBEntityManager;
import de.lichtflut.rb.core.schema.model.IRBEntity;

/**
 * <p>
 * This class provides a static {@link RBEntityManager} for testing purposes.
 * </p>
 * Created: Aug 18, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class MockRBEntityManagement implements RBEntityManager {

	private List<IRBEntity> dataPool = MockNewRBEntityFactory.getAllEntities();

	@Override
	public IRBEntity find(final ResourceID resourceID) {
		for (IRBEntity e : dataPool) {
			if(e.getID().equals(resourceID)){
				return e;
			}
		}
		return null;
	}

	@Override
	public List<IRBEntity> findAllByType(final ResourceID id) {
		if (id != null) {
			List<IRBEntity> list = new ArrayList<IRBEntity>();
			for (IRBEntity e : dataPool) {
				if (e.getType().equals(id)) {
					list.add(e);
				}
			}
			return list;
		}
		return dataPool;
	}

	@Override
	public IRBEntity store(final IRBEntity entity) {
		if(dataPool.contains(entity)){
			dataPool.remove(entity);
			dataPool.add(entity);

		}else{
			dataPool.add(entity);
		}
		return entity;
	}

	@Override
	public void delete(final IRBEntity entity) {
		dataPool.remove(entity);
	}

	@Override
	public List<ResourceID> findAllTypes() {
		List<ResourceID> list = new ArrayList<ResourceID>();
		for (IRBEntity e : dataPool) {
			if(!list.contains(e.getType())){
				list.add(e.getType());
			}
		}
		return list;
	}

}
