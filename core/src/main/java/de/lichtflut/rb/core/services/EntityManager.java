package de.lichtflut.rb.core.services;

import java.util.List;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBEntity;

/**
 * .
 * Created: Aug 11, 2011
 *
 * @author Raphael Esterle
 *
 */
public interface EntityManager {

    /**
     * Find an Entity by its {@link ResourceID}.
     * @param resourceID  of the Entity
     * @return {@link RBEntity}
     */
    RBEntity find(ResourceID resourceID);

    // -----------------------------------------------------
    
    /**
     * Create and initialize a new {@link RBEntity} with given type.
     */
    RBEntity create(ResourceID type);
    
    /**
     * Store an Entity.
     * @param entity The entity.
     */
    void store(RBEntity entity);

    /**
     * Delete an {@link RBEntity}.
     * @param entityID - The ID of the entity to be deleted
     */
    void delete(ResourceID entityID);

	/**
	 * Change the type of an entity.
	 * @param entity The entity.
	 * @param type The new type.
	 */
	void changeType(RBEntity entity, ResourceID type);

}
