package de.lichtflut.rb.core.content;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.naming.QualifiedName;

import java.io.Serializable;
import java.util.Date;

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
