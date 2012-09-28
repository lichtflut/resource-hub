package de.lichtflut.rb.core.content;

import org.arastreju.sge.naming.QualifiedName;

import java.io.Serializable;

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

    String getTitle();

    void setTitle(String title);

    String getContentAsString();

    void setContent(String content);
}
