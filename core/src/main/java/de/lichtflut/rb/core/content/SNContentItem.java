package de.lichtflut.rb.core.content;

import de.lichtflut.rb.core.RBSystem;
import org.arastreju.sge.apriori.DC;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  Content item based on a semantic node.
 * </p>
 * <p>
 *  Created 24.09.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNContentItem extends ResourceView implements ContentItem {

    public SNContentItem(ResourceNode resource) {
        super(resource);
    }

    public SNContentItem(QualifiedName qn) {
        super(qn);
    }

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

}
