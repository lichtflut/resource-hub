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
package de.lichtflut.rb.webck.models.viewspecs;

import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Model for loading of perspectives by their ID.
 * </p>
 *
 * <p>
 * 	Created Feb 6, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerspectiveModel extends AbstractLoadableDetachableModel<Perspective> {
	
	private final ResourceID perspectiveID;
	
	@SpringBean
	private ViewSpecificationService viewSpecificationService;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor. 
	 */
	public PerspectiveModel(final ResourceID perspectiveID) {
		this.perspectiveID = perspectiveID;
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------

	@Override
	public Perspective load() {
        Perspective perspective = viewSpecificationService.findPerspective(perspectiveID);
        if (perspective == null) {
            perspective = viewSpecificationService.initializePerspective(perspectiveID);
            viewSpecificationService.store(perspective);
        }
        return perspective;
    }

}
