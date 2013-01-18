package de.lichtflut.rb.rest.api.infovis;

import com.sun.jersey.core.util.Base64;
import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import de.lichtflut.rb.rest.api.query.ResultItemRVO;
import de.lichtflut.rb.rest.delegate.providers.ServiceProvider;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <p>
 *  Abstract base for information visualization services..
 * </p>
 *
 * <p>
 * 	Created Jan 11, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class AbstractInfoVisService extends RBServiceEndpoint {

    protected Conversation conversation(String domain, RBUser user) {
        final ServiceProvider provider = getProvider(domain, user);
        return provider.getConversation();
    }

    protected Conversation conversation(String domain, RBUser user, Context context) {
        final ServiceProvider provider = getProvider(domain, user);
        return provider.getConversation(context);
    }

    protected String decodeBase64(String encoded) {
        if (encoded == null) {
            return null;
        }
        byte[] decoded = Base64.decode(encoded);
        return new String(decoded);
    }
}
