package de.lichtflut.rb.rest.api.util;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.rest.api.common.QuickInfoRVO;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 *<p>
 *  Builds the quick info for an entity according to it's schema.
 * </p>
 *
 * <p>
 *  Created Mar 15, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class QuickInfoBuilder {

    private final SchemaProvider schemaProvider;

    // ----------------------------------------------------

    public QuickInfoBuilder(SchemaProvider schemaProvider) {
        this.schemaProvider = schemaProvider;
    }

    // ----------------------------------------------------

    public QuickInfoRVO build(ResourceNode node, Locale locale) {
        QuickInfoRVO quickInfo = new QuickInfoRVO();
        ResourceSchema schema = schemaProvider.ofEntity(node);
        List<PropertyDeclaration> declarations = schema.getQuickInfo();
        for (PropertyDeclaration decl : declarations) {
            String label = decl.getFieldLabelDefinition().getLabel(locale);
            String values = values(node, decl.getPropertyDescriptor());
            quickInfo.add(decl.getPropertyDescriptor(), label, values);
        }
        return quickInfo;
    }

    // ----------------------------------------------------

    private String values(ResourceNode subject, ResourceID predicate) {
        StringBuilder sb = new StringBuilder();
        Set<SemanticNode> objects = SNOPS.objects(subject, predicate);
        boolean first = true;
        for (SemanticNode current : objects) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            if (current.isResourceNode()) {
                sb.append(current.asResource().toURI());
            } else {
                sb.append(current.asValue().getStringValue());
            }
        }
        return sb.toString();
    }

}
