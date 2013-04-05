package de.lichtflut.rb.rest.api.infovis;

import de.lichtflut.rb.core.services.SchemaManager;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.context.Context;

import com.sun.jersey.core.util.Base64;

import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import de.lichtflut.rb.rest.delegate.providers.ServiceProvider;

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

	protected Conversation conversation(final String domain, final RBUser user) {
		final ServiceProvider provider = getProvider(domain, user);
		return provider.getConversation();
	}

	protected Conversation conversation(final String domain, final RBUser user, final Context context) {
		final ServiceProvider provider = getProvider(domain, user);
		return provider.getConversation(context);
	}

    protected SchemaManager schemaManager(final String domain, final RBUser user) {
        final ServiceProvider provider = getProvider(domain, user);
        return provider.getSchemaManager();
    }

    protected String decodeBase64(final String encoded) {
		if (encoded == null) {
			return null;
		}
		byte[] decoded = Base64.decode(encoded);
		return new String(decoded);
	}
}
