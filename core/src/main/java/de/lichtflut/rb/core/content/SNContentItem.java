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

import de.lichtflut.rb.core.RBSystem;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.DC;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.TimeMask;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNTimeSpec;
import org.arastreju.sge.naming.QualifiedName;

import java.util.Date;

/**
 * <p>
 *  Content item based on a semantic node.
 * </p>
 *
 * <p>
 *  Created 24.09.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNContentItem extends ResourceView implements ContentItem {

    /**
     * Create a content item based on given resource.
     * @param resource The resource representing the content.
     */
    public SNContentItem(ResourceNode resource) {
        super(resource);
    }

    /**
     * Create a new content item with a given qualified name.
     * @param qn The qualified name.
     */
    public SNContentItem(QualifiedName qn) {
        super(qn);
    }

    /**
     * Create a new content item..
     */
    public SNContentItem() {
    }

    // ----------------------------------------------------

    @Override
    public String getID() {
        return getQualifiedName().toURI();
    }

    @Override
    public String getTitle() {
        return stringValue(DC.TITLE);
    }

    @Override
    public void setTitle(String title) {
        setValue(DC.TITLE, title);
    }

    @Override
    public String getContentAsString() {
        return stringValue(RBSystem.HAS_CONTENT);
    }

    @Override
    public void setContent(String content) {
        setValue(RBSystem.HAS_CONTENT, content);
    }

    // ----------------------------------------------------


    @Override
    public SemanticNode getCreator() {
        return SNOPS.fetchObject(this, DC.CREATOR);
    }

    @Override
    public String getCreatorName() {
        SemanticNode node = getCreator();
        if (node == null) {
            return "";
        } else if (node.isResourceNode()) {
            return node.asResource().getQualifiedName().getSimpleName();
        } else {
            return node.asValue().getStringValue();
        }
    }

    @Override
    public void setCreator(ResourceID creator) {
        setValue(DC.CREATOR, creator);
    }

    @Override
    public void setCreator(String creator) {
        setValue(DC.CREATOR, creator);
    }

    @Override
    public Date getCreateDate() {
        return timeValue(DC.CREATED);
    }

    @Override
    public void setCreateDate(Date date) {
        setValue(DC.CREATED, new SNTimeSpec(new Date(), TimeMask.TIMESTAMP));
    }

    // ----------------------------------------------------

    @Override
    public Date getModificationDate() {
        return timeValue(DC.MODIFIED);
    }

    @Override
    public void setModificationDate(Date date) {
        setValue(DC.MODIFIED, new SNTimeSpec(new Date(), TimeMask.TIMESTAMP));
    }

    // ----------------------------------------------------


    @Override
    public String toString() {
        return "ContentItem[" + getID() + ": " +  getTitle() + "]";
    }
}
