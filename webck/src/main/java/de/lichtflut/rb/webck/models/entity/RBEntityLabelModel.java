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
package de.lichtflut.rb.webck.models.entity;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  Model for obtaining the locale specific label of an {@link RBEntity}.
 * </p>
 *
 * <p>
 * 	Created Dec 23, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBEntityLabelModel extends DerivedModel<String, RBEntity> {

	/**
	 * Constructor.
	 * @param model The entity model.
	 */
	public RBEntityLabelModel(final IModel<RBEntity> model) {
		super(model);
	}

    /**
     * Constructor
     * @param entity The entity.
     */
    public RBEntityLabelModel(RBEntity entity) {
        super(entity);
    }

    // ----------------------------------------------------
	
	@Override
	protected String derive(RBEntity original) {
		final String label = original.getLabel();
		if (original.isTransient() || StringUtils.isBlank(label)) {
			return getDefault();
		} else {
			return label;
		}
	}
	
}
