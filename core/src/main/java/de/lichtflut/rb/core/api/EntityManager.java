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
     * @param type - id of the Resourcetype
     * @return a list of {@link RBEntity}
     */
    List<RBEntity> findAllByType(ResourceID type);

    /**
     *
     * @param entity /
     *
     * Stores an Entity and returns the saved entity.
     */
    void store(RBEntity entity);

    /**
     * Deletes an {@link RBEntity}.
     * @param entity - instance of {@link RBEntity} which is to be deleted
     */
    void delete(RBEntity entity);
}
