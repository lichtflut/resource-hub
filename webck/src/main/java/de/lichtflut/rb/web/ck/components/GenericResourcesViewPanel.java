/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.List;

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.spi.RBServiceProvider;

/**
 * [TODO Insert description here.
 *
 * Created: Aug 18, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class GenericResourcesViewPanel extends CKComponent {

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param entites - List of {@link IRBEntity}
	 */
	public GenericResourcesViewPanel(final String id,final List<IRBEntity> entites){
		super(id);
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RBServiceProvider getServiceProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CKComponent setViewMode(final ViewMode mode) {
		// TODO Auto-generated method stub
		return null;
	}

}
