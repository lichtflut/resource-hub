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
package de.lichtflut.rb.application.custom;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.application.base.RBBasePage;
import de.lichtflut.rb.webck.browsing.JumpTarget;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.widgets.PerspectivePanel;
import de.lichtflut.rb.webck.models.viewspecs.PerspectiveModel;

/**
 * <p>
 *  Page for organization perspective.
 * </p>
 *
 * <p>
 * 	Created Feb 2, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerspectivePage extends RBBasePage {
	
	public static final String VIEW_ID = "view";
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param parameters The parameters.
	 */
	public PerspectivePage(PageParameters parameters) {
		super(parameters);

		RBWebSession.get().getHistory().clear(createJumpTarget(parameters));
		
		final StringValue viewParam = parameters.get(VIEW_ID);
		
		add(new PerspectivePanel("perspective", modelFor(viewParam)));
	}

    /**
     * Constructor.
     * @param perspectiveID The unique ID of the perspective..
     */
    public PerspectivePage(final ResourceID perspectiveID) {
        super();
        RBWebSession.get().getHistory().clear(createJumpTarget());
        add(new PerspectivePanel("perspective", new PerspectiveModel(perspectiveID)));
    }
	
	// ----------------------------------------------------

    protected JumpTarget createJumpTarget() {
        return new JumpTarget(getClass(), new PageParameters());
    }

    protected JumpTarget createJumpTarget(PageParameters parameters) {
        return new JumpTarget(getClass(), parameters);
    }
	
	private PerspectiveModel modelFor(final StringValue viewParam) {
		if (viewParam.isEmpty()) {
			// dummy perspective
			return new PerspectiveModel(new SimpleResourceID());
		} else {
			return new PerspectiveModel(new SimpleResourceID(viewParam.toString()));	
		}
	}
	
}
