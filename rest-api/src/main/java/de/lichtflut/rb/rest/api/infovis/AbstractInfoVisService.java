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
