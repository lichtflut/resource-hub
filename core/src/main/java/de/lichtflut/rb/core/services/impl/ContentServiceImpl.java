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

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.Conversation;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.query.Query;

import de.lichtflut.infra.exceptions.NotYetSupportedException;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.content.ContentItem;
import de.lichtflut.rb.core.content.SNContentItem;
import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import de.lichtflut.rb.core.services.ContentService;
import de.lichtflut.rb.core.services.ServiceContext;

/**
 * <p>
 *  Implementation of ContentService.
 * </p>
 *
 * <p>
 *  Created 24.09.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class ContentServiceImpl implements ContentService {

	@SuppressWarnings("unused")
	private final ServiceContext context;

	private final ArastrejuResourceFactory arasFactory;

	// ----------------------------------------------------

	public ContentServiceImpl(final ServiceContext context, final ArastrejuResourceFactory arasFactory) {
		this.context = context;
		this.arasFactory = arasFactory;
	}

	// ----------------------------------------------------

	@Override
	public ContentItem findById(final String id) {
		if (id == null) {
			return null;
		}
		ResourceNode resource = conversation().findResource(QualifiedName.fromURI(id));
		return new SNContentItem(resource);
	}

	@Override
	public void store(final ContentItem item) {
		if (item instanceof SNContentItem) {
			SNContentItem snContentItem = (SNContentItem) item;
			conversation().attach(snContentItem);
		} else {
			throw new NotYetSupportedException("Content item of class: " + item.getClass());
		}
	}

	@Override
	public void remove(final String id) {
		conversation().remove(new SimpleResourceID(id));
	}

	// ----------------------------------------------------

	@Override
	public List<ContentItem> getAttachedItems(final ResourceID owner) {
		final List<ContentItem> result = new ArrayList<ContentItem>();
		final Query query = conversation().createQuery();
		query.addField(RBSystem.IS_ATTACHED_TO, owner.toURI());
		for(ResourceNode node : query.getResult()) {
			result.add(new SNContentItem(node));
		}
		return result;
	}

	@Override
	public void attachToResource(final ContentItem contentItem, final ResourceID target) {
		ResourceNode resource = conversation().findResource(QualifiedName.fromURI(contentItem.getID()));
		if (resource != null) {
			resource.addAssociation(RBSystem.IS_ATTACHED_TO, target);
		} else {
			throw new IllegalStateException("ContentItem can not be attached. Not yet stored: " + contentItem);
		}
	}

	// ----------------------------------------------------

	private Conversation conversation() {
		return arasFactory.getConversation();
	}

}
