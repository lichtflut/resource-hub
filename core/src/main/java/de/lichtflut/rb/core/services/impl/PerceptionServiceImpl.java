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
package de.lichtflut.rb.core.services.impl;

import de.lichtflut.rb.core.perceptions.Perception;
import de.lichtflut.rb.core.perceptions.PerceptionItem;
import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import de.lichtflut.rb.core.services.PerceptionService;
import de.lichtflut.rb.core.services.ServiceContext;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>
 *  Service for management of IT items and landscapes.
 * </p>
 *
 * <p>
 *  Created 29.11.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerceptionServiceImpl implements PerceptionService {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(PerceptionServiceImpl.class);

	@SuppressWarnings("unused")
	private final ServiceContext context;

	private final ArastrejuResourceFactory arasFactory;

	// ----------------------------------------------------

	public PerceptionServiceImpl(final ServiceContext context, final ArastrejuResourceFactory arasFactory) {
		this.context = context;
		this.arasFactory = arasFactory;
	}

	// ----------------------------------------------------

	@Override
	public PerceptionItem findItemByID(final QualifiedName qn) {
		return PerceptionItem.from(conversation().findResource(qn));
	}

	// ----------------------------------------------------

    @Override
    public List<PerceptionItem> getItemsOfPerception(QualifiedName qn) {
        Perception perception = Perception.from(conversation().findResource(qn));
        if (perception != null) {
            return perception.getItems();
        } else {
            throw new IllegalArgumentException("Requested perception does not exist: " + qn);
        }
    }

    @Override
    public void addItemToPerception(PerceptionItem item, QualifiedName qn) {
        Perception attachedPerception = Perception.from(conversation().findResource(qn));
        PerceptionItem attachedItem = PerceptionItem.from(conversation().resolve(item));
        if (attachedPerception != null) {
            // setting both directions although should already set by aras:inverseOf
            attachedItem.setPerception(attachedPerception);
            attachedPerception.addItem(item);
        } else {
            throw new IllegalArgumentException("Requested perception does not exist: " + qn);
        }
    }

    // ----------------------------------------------------

	@Override
	public List<PerceptionItem> getBaseItemsOfPerception(final QualifiedName qn) {
		Perception perception = Perception.from(conversation().findResource(qn));
		if (perception != null) {
			return perception.getTreeRootItems();
		} else {
			throw new IllegalArgumentException("Requested perception does not exist: " + qn);
		}
	}

	@Override
	public void addBaseItemToPerception(final PerceptionItem item, final QualifiedName qn) {
		Perception attachedPerception = Perception.from(conversation().findResource(qn));
        conversation().attach(item);
		if (attachedPerception != null) {
            item.setPerception(attachedPerception);
			attachedPerception.addTreeRootItem(item);
		} else {
			throw new IllegalArgumentException("Requested perception does not exist: " + qn);
		}
	}

	// ----------------------------------------------------

	private Conversation conversation() {
		return arasFactory.getConversation();
	}

}
