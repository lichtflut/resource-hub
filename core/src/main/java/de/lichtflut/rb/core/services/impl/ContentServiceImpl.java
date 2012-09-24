package de.lichtflut.rb.core.services.impl;

import de.lichtflut.infra.exceptions.NotYetSupportedException;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.content.ContentItem;
import de.lichtflut.rb.core.content.SNContentItem;
import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import de.lichtflut.rb.core.services.ContentService;
import de.lichtflut.rb.core.services.ServiceContext;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);

    private ServiceContext context;

    private ArastrejuResourceFactory arasFactory;

    // ----------------------------------------------------

    public ContentServiceImpl(ServiceContext context, ArastrejuResourceFactory arasFactory) {
        this.context = context;
        this.arasFactory = arasFactory;
    }

    // ----------------------------------------------------

    @Override
    public ContentItem findById(String id) {
        ResourceNode resource = conversation().findResource(new QualifiedName(id));
        return new SNContentItem(resource);
    }

    @Override
    public void store(ContentItem item) {
        if (item instanceof SNContentItem) {
            SNContentItem snContentItem = (SNContentItem) item;
            conversation().attach(snContentItem);
        } else {
            throw new NotYetSupportedException("Content item of class: " + item.getClass());
        }
    }

    // ----------------------------------------------------

    private ModelingConversation conversation() {
        return arasFactory.getConversation(RBSystem.VIEW_SPEC_CTX);
    }

}
