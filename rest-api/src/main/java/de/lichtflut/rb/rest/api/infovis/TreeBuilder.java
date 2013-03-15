package de.lichtflut.rb.rest.api.infovis;

import de.lichtflut.rb.rest.api.common.QuickInfoRVO;
import de.lichtflut.rb.rest.api.util.QuickInfoBuilder;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.traverse.TraversalFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
* <p>
*  Builder for trees of nodes.
* </p>
 *
* <p>
*   Created 11.01.13
* </p>
*
* @author Oliver Tigges
*/
class TreeBuilder {

    private final Set<SemanticNode> visited = new HashSet<SemanticNode>();

    private final QuickInfoBuilder qiBuilder;

    private final Locale locale;

    private final TraversalFilter filter;

    // ----------------------------------------------------

    public TreeBuilder(QuickInfoBuilder qiBuilder, Locale locale, TraversalFilter filter) {
        this.qiBuilder = qiBuilder;
        this.locale = locale;
        this.filter = filter;
    }

    // ----------------------------------------------------

    public TreeNodeRVO build(ResourceNode resource) {
        TreeNodeRVO rvo = create(resource);

        appendChildren(resource, rvo);

        return rvo;
    }

    // ----------------------------------------------------

    private void appendChildren(ResourceNode resource, TreeNodeRVO rvo) {
        List<TreeNodeRVO> children = new ArrayList<TreeNodeRVO>();
        visited.add(resource);
        for (Statement stmt : resource.getAssociations()) {
            SemanticNode object = stmt.getObject();
            if (!object.isResourceNode() || visited.contains(object)) {
                continue;
            }
            switch (filter.accept(stmt)) {
                case STOP:
                    break;
                case ACCEPPT_CONTINUE:
                case ACCEPT:
                    ResourceNode child = object.asResource();
                    TreeNodeRVO childRVO = create(child);
                    children.add(childRVO);
                    appendChildren(child, childRVO);
                default:
                    break;
            }
        }
        Collections.sort(children);
        rvo.addChildren(children);
    }

    private TreeNodeRVO create(ResourceNode node) {
        TreeNodeRVO rvo = new TreeNodeRVO(node, locale);

        QuickInfoRVO qi = qiBuilder.build(node, locale);
        rvo.setDetails(qi);

        return rvo;
    }
}
