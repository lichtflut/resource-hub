package de.lichtflut.rb.core.api;

import java.io.Serializable;
import java.util.List;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.IRBEntity;

/**
 * .
 * Created: Aug 11, 2011
 *
 * @author Raphael Esterle
 *
 */
public interface INewRBEntityManagement extends Serializable {

    /**
     *
     * @param resourceID /
     * @return {@link IRBEntity}
     *
     * Returns an Entity for a given ResourceID.
     */
    IRBEntity find(ResourceID resourceID);

    /**
     * Finds all {@link IRBEntity}s for a given type.
     * @param type - id of the Resourcetype
     * @return a list of {@link IRBEntity}
     */
    List<IRBEntity> findAllByType(ResourceID type);

    /**
     *
     * @param entity /
     * @return
     *
     * Stores an Entity and returns the saved entity.
     */
    IRBEntity store(IRBEntity entity);

    /**
     * Deletes an {@link IRBEntity}.
     * @param entity - instance of {@link IRBEntity} which is to be deleted
     */
    void delete(IRBEntity entity);
}
