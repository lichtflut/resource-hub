/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.models;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.browsing.BrowsingHistory;
import de.lichtflut.rb.webck.browsing.BrowsingState;
import de.lichtflut.rb.webck.browsing.EntityBrowsingStep;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

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
