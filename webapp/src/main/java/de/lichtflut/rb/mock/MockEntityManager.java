/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;

/**
 * <p>
 * 	This class provides a static {@link EntityManager} for testing purposes.
 * </p>
 * 
 * Created: Aug 18, 2011
 * 
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class MockEntityManager implements EntityManager, Serializable {

	private final List<RBEntity> dataPool;

	// -----------------------------------------------------

	/**
	 * Constructor.
	 * @param dataPool
	 */
	public MockEntityManager(final List<RBEntity> dataPool) {
		this.dataPool = dataPool;
	}

	// -----------------------------------------------------

	@Override
	public RBEntity find(final ResourceID resourceID) {
		for (RBEntity e : dataPool) {
			if (e.getID().equals(resourceID)) {
				((RBEntityImpl) e).initializeFields();
				return e;
			}
		}
		return null;
	}

	@Override
	public List<RBEntity> findAllByType(final ResourceID type) {
		if (type != null) {
			List<RBEntity> list = new ArrayList<RBEntity>();
			for (RBEntity e : dataPool) {
				if (e.getType().equals(type)) {
					((RBEntityImpl) e).initializeFields();
					list.add(e);
				}
			}
			return list;
		}
		return dataPool;
	}

	@Override
	public void store(final RBEntity entity) {
		if (dataPool.contains(entity)) {
			dataPool.remove(entity);
			dataPool.add(entity);

		} else {
			dataPool.add(entity);
		}
	}

	@Override
	public void delete(final RBEntity entity) {
		dataPool.remove(entity);
	}

}
