package de.lichtflut.rb.rest.api.infovis;

import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.traverse.NotPredicateFilter;
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

    private final Locale locale;

    private final Set<SemanticNode> visited = new HashSet<SemanticNode>();

    private final TraversalFilter filter;

    // ----------------------------------------------------

    public TreeBuilder(Locale locale) {
        this(locale, new NotPredicateFilter(RDF.TYPE, RDFS.SUB_CLASS_OF));
    }

    public TreeBuilder(Locale locale, TraversalFilter filter) {
        this.locale = locale;
        this.filter = filter;
    }

    // ----------------------------------------------------

    public TreeNodeRVO build(ResourceNode resource) {
        TreeNodeRVO rvo = new TreeNodeRVO(resource, locale);

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
                    TreeNodeRVO childRVO = new TreeNodeRVO(child, locale);
                    children.add(childRVO);
                    appendChildren(child, childRVO);
                default:
                    break;
            }
        }
        Collections.sort(children);
        rvo.addChildren(children);
    }
}
