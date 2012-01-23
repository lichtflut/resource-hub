/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.browsing.BrowsingHistory;
import de.lichtflut.rb.webck.browsing.BrowsingState;
import de.lichtflut.rb.webck.browsing.EntityBrowsingStep;

/**
 * <p>
 *  Special model concerning {@link BrowsingHistory}.
 * </p>
 *
 * <p>
 * 	Created Dec 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class BrowsingContextModel {

	public static ConditionalModel<Boolean> isInViewMode() {
		return new ConditionalModel<Boolean>() {
			@Override
			public boolean isFulfilled() {
				return getObject();
			}
			
			@Override
			public Boolean getObject() {
				return BrowsingState.VIEW.equals(RBWebSession.get().getHistory().getCurrentStep().getState());
			}
		};
	}
	
	public static ConditionalModel<Boolean> isInCreateReferenceMode() {
		return new ConditionalModel<Boolean>() {
			@Override
			public boolean isFulfilled() {
				return getObject();
			}
			
			@Override
			public Boolean getObject() {
				return BrowsingState.CREATE_REFERENCE.equals(RBWebSession.get().getHistory().getCurrentStep().getState());
			}
		};
	}
	
	public static ConditionalModel<Boolean> isInCreateMode() {
		return new ConditionalModel<Boolean>() {
			@Override
			public boolean isFulfilled() {
				return getObject();
			}
			
			@Override
			public Boolean getObject() {
				return BrowsingState.CREATE.equals(RBWebSession.get().getHistory().getCurrentStep().getState());
			}
		};
	}
	
	public static IModel<EntityBrowsingStep> currentStepModel() {
		return new AbstractReadOnlyModel<EntityBrowsingStep>() {
			@Override
			public EntityBrowsingStep getObject() {
				 return RBWebSession.get().getHistory().getCurrentStep();
			}
		};
	}
	
	public static IModel<ResourceID> currentEntityModel() {
		return new AbstractReadOnlyModel<ResourceID>() {
			@Override
			public ResourceID getObject() {
				 EntityHandle handle = RBWebSession.get().getHistory().getCurrentStep().getHandle();
				 if (handle != null) {
					 return handle.getId();
				 } else {
					 return null;
				 }
			}
		};
	}
	
}
