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
package de.lichtflut.rb.rest.api.query;

import com.sun.jersey.core.util.Base64;
import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import de.lichtflut.rb.rest.delegate.providers.ServiceProvider;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <p>
 *  Abstract base for query services.
 * </p>
 *
 * <p>
 * 	Created Jun 28, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class AbstractQueryService extends RBServiceEndpoint {

    protected List<ResultItemRVO> buildResult(QueryResult result) {
        final List<ResultItemRVO> rvoList = new ArrayList<ResultItemRVO>();
        int count = 0;
        for (ResourceNode node : result) {
            ResultItemRVO item = map(node);
            rvoList.add(item);
            count++;
            if (count >= 20) {
                break;
            }

        }
        result.close();
        return rvoList;
    }

    protected Query createQuery(String domain, RBUser user) {
        return conversation(domain, user).createQuery();
    }

    protected Query createQuery(String domain, RBUser user, Context context) {
        return conversation(domain, user, context).createQuery();
    }


    protected String decodeBase64(String encoded) {
        if (encoded == null || encoded.trim().length() == 0) {
            return null;
        }
        byte[] decoded = Base64.decode(encoded);
        return new String(decoded);
    }

    // ----------------------------------------------------

    private Conversation conversation(String domain, RBUser user, Context context) {
        final ServiceProvider provider = getProvider(domain, user);
        return provider.getConversation(context);
    }

    private ResultItemRVO map(ResourceNode node) {
        ResultItemRVO item = new ResultItemRVO();
        item.setId(node.toURI());

        String label = ResourceLabelBuilder.getInstance().getLabel(node, Locale.getDefault());
        String scope = getScopeName(node);

        if (scope == null) {
            item.setLabel(label);
        } else {
            item.setLabel(label + " (" + scope + ")");
        }

        item.setInfo(label);

        return item;
    }

    private String getScopeName(ResourceNode node) {
        SemanticNode scope = SNOPS.fetchObject(node, RB.HAS_SCOPE);
        if (scope == null) {
            return null;
        } else if (scope.isValueNode()) {
            return scope.asValue().getStringValue();
        } else {
            return ResourceLabelBuilder.getInstance().getLabel(scope.asResource(), Locale.getDefault());
        }
    }

}
