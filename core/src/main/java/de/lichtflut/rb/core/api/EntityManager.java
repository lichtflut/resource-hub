package de.lichtflut.rb.core.api;

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
     *
     * @param resourceID /
     * @return {@link RBEntity}
     *
     * Returns an Entity for a given ResourceID.
     */
    RBEntity find(ResourceID resourceID);

    /**
     * Finds all {@link RBEntity}s for a given type.
     * @param type - id of the Resource Type
     * @return a list of {@link RBEntity}
     */
    List<RBEntity> findByType(ResourceID type);
    
    // -----------------------------------------------------
    
    /**
     * Create and initialize a new {@link RBEntity} with given type. The entitiy will not
     * be stored. 
     */
    RBEntity create(ResourceID type);
    
    /**
     * Stores an Entity.
     * @param entity The entity.
     */
    void store(RBEntity entity);

    /**
     * Deletes an {@link RBEntity}.
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
