package de.lichtflut.rb.core.services;

import java.util.List;
import java.util.Map;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;

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
	 * <p>
	 * Store an Entity.
	 * </p>
	 * 
	 * @param entity The entity.
	 */
	void store(RBEntity entity);

	/**
	 * Validates an Entity against its Schema.
	 * @param entity {@link RBEntity} to validate
	 * @return a Map containting the {@link ErrorCodes} and a list of fields, that produced an error, Map is empty if no error occured
	 */
	Map<Integer, List<RBField>> validate(RBEntity entity);

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
