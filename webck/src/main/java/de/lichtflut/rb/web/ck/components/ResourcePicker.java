/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.mock.MockNewRBEntityFactory;
import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.web.models.ReferencedEntityModel;

/**
 * Component for searching and displaying of referenced resources.
 *
 * Created: Aug 25, 2011
 *
 * @author Ravi Knox
 */
public abstract class ResourcePicker extends CKComponent {

	private static final long serialVersionUID = 1L;

	// ------------------------------------------------------------

	/**
	 * Default constructor.
	 * @param id The wicket ID.
	 */
	public ResourcePicker(final String id) {
		super(id);
	}

	/**
	 * @param id The wicket ID.
	 * @param entity The exitisting entity to be displayed.
	 */
	public ResourcePicker(final String id, final IRBEntity entity) {
		super(id);
		initModel(entity);
	}

	// ------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initComponent(final CKValueWrapperModel model) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CKComponent setViewMode(final ViewMode mode) {
		return this;
	}

	// ------------------------------------------------------------

	/**
	 * Initializes the Component's DefaultModel.
	 * @param entity - instance of {@link IRBEntity}
	 */
	private void initModel(final IRBEntity entity) {
		setDefaultModel(new ReferencedEntityModel(entity) {
			@Override
			public IRBEntity resolve(final ResourceID id) {
				return MockNewRBEntityFactory.createComplexNewRBEntity();
			}
		});
	}

}
