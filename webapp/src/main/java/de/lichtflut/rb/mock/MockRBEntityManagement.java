/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.entity.RBEntity;

/**
 * <p>
 * This class provides a static {@link EntityManager} for testing purposes.
 * </p>
 * Created: Aug 18, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class MockRBEntityManagement implements EntityManager {

	private List<RBEntity> dataPool;

	/**
	 * Constructor.
	 */
	public MockRBEntityManagement(){
		dataPool = new ArrayList<RBEntity>();
		 dataPool.addAll(MockNewRBEntityFactory.getAllEntities());
	}

	@Override
	public RBEntity find(final ResourceID resourceID) {
		for (RBEntity e : dataPool) {
			if(e.getID().equals(resourceID)){
				return e;
			}
		}
		return null;
	}

	@Override
	public List<RBEntity> findAllByType(final ResourceID id) {
		if (id != null) {
			List<RBEntity> list = new ArrayList<RBEntity>();
			for (RBEntity e : dataPool) {
				if (e.getType().equals(id)) {
					list.add(e);
				}
			}
			return list;
		}
		return dataPool;
	}

	@Override
	public void store(final RBEntity entity) {
		if(dataPool.contains(entity)){
			dataPool.remove(entity);
			dataPool.add(entity);

		}else{
			dataPool.add(entity);
		}
	}

	@Override
	public void delete(final RBEntity entity) {
		dataPool.remove(entity);
	}

	@Override
	public List<ResourceID> findAllTypes() {
		List<ResourceID> list = new ArrayList<ResourceID>();
		for (RBEntity e : dataPool) {
			if(!list.contains(e.getType())){
				list.add(e.getType());
			}
		}
		return list;
	}

}
