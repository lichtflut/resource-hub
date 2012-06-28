package de.lichtflut.rb.rest.api.query;

import com.sun.jersey.core.util.Base64;
import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.common.TermSearcher;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.nodes.ResourceNode;
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
        for (ResourceNode node : result) {
            ResultItemRVO item = new ResultItemRVO();
            item.setId(node.toURI());
            item.setLabel(ResourceLabelBuilder.getInstance().getLabel(node, Locale.getDefault()));
            item.setInfo(ResourceLabelBuilder.getInstance().getLabel(node, Locale.getDefault()));
            rvoList.add(item);
        }
        return rvoList;
    }

    protected Query createQuery(String domain, RBUser user) {
        return conversation(domain, user).createQuery();
    }

    private ModelingConversation conversation(String domain, RBUser user) {
        final ServiceProvider provider = getProvider(domain, user);
        return provider.getConversation();
    }

    protected String decodeBase64(String encoded) {
        if (encoded == null) {
            return null;
        }
        byte[] decoded = Base64.decode(encoded);
        return new String(decoded);
    }
}
