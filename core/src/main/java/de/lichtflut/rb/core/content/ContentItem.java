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
package de.lichtflut.rb.core.content;

import java.io.Serializable;
import java.util.Date;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SemanticNode;

/**
 * <p>
 *  Represents a item of some content.
 * </p>
 *
 * <p>
 *  Created 21.09.12
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ContentItem extends Serializable {

	String getID();

	// ----------------------------------------------------

	String getTitle();

	void setTitle(String title);

	// ----------------------------------------------------

	String getContentAsString();

	void setContent(String content);

	// ----------------------------------------------------

	/**
	 * Get the node representing the creator. This may either be the resource representing the creator, or
	 * a string value node.
	 * @return The node representing the creator of this content item.
	 */
	SemanticNode getCreator();

	/**
	 * Get the name of the creator.
	 * @return The creator's name.
	 */
	String getCreatorName();

	/**
	 * Set the node representing the creator.
	 * @param creator The creator.
	 */
	void setCreator(ResourceID creator);

	/**
	 * Set the name of the creator.
	 * @param creator The creator.
	 */
	void setCreator(String creator);

	// ----------------------------------------------------

	Date getCreateDate();

	void setCreateDate(Date date);

	Date getModificationDate();

	void setModificationDate(Date date);


}
