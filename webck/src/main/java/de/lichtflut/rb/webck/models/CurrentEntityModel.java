/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.webck.application.RBWebSession;


/**
 * <p>
 *  Model containing the entity currently displayed.
 * </p>
 *
 * <p>
 * 	Created Jan 3, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class CurrentEntityModel extends AbstractReadOnlyModel<ResourceID> {

	/** 
	* {@inheritDoc}
	*/
	@Override
	public ResourceID getObject() {
		 EntityHandle handle = RBWebSession.get().getHistory().getCurrentEntity();
		 if (handle != null) {
			 return handle.getId();
		 } else {
			 return null;
		 }
	}

}
