/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.util.ArrayList;
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

	private List<IRBEntity> dataPool = MockNewRBEntityFactory.getListOfPersons();

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
				System.out.println(e.getType() + " + " + id);
				if (e.getType().equals(id)) {
					list.add(e);
				}
			}
			System.out.println(list.size() + " -size");
			return list;
		}
		return dataPool;
	}

	/**
	 * Returns a list of all Entities.
	 * @return -
	 */
	public List<IRBEntity> findAll() {
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

}
