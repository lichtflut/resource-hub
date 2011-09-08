/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.spi.IRBServiceProvider;

/**
 * This Panel represents an {@link IRBEntity}.
 * The following Attributes will be displayed
 * <ul>
 * <li>Label</li>
 * <li>Image</li>
 * <li>Short description</li>
 * </ul>
 *
 * If none of these attributes can be attained through {@link IRBEntity}s standard methods,
 * a {@link String} representation of the {@link IRBEntity} will be displayed.
 *
 * Created: Aug 31, 2011
 *
 * @author Ravi Knox
 */
public class ResourceInfoPanel extends CKComponent {

	private static final long serialVersionUID = 1L;
	private IRBEntity entity;

	/**
	 * @param id - wicket:id
	 * @param entity - {@link IRBEntity} which is to be displayed
	 */
	public ResourceInfoPanel(final String id, final IRBEntity entity) {
		super(id);
		this.entity = entity;
		buildComponent();
	}

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
	public IRBServiceProvider getServiceProvider() {return null;}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CKComponent setViewMode(final ViewMode mode) {return null;}

}
