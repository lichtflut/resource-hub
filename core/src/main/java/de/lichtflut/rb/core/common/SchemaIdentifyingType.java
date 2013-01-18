package de.lichtflut.rb.core.common;

import de.lichtflut.rb.core.RBSystem;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNClass;

/**
 * <p>
 *  Helper to get the 'schema identifying' type of a resource.
 *  The detection of a resource's type is a three step process.
 *  <ol>
 *      <li>Check if the resource has directly assigned the type via system:hasSchemaIdentifyingType</li>
 *      <li>Check if any of the super classes has assigned the type via system:hasSchemaIdentifyingType</li>
 *      <li>Grab one of the rfd:type statements of the resource</li>
 *  </ol>
 * </p>
 * <p>
 *  Created 11.01.13
 * </p>
 *
 * @author Oliver Tigges
 */
public class SchemaIdentifyingType {

    public static SNClass of(ResourceID resource) {
        return of(resource.asResource());
    }

    public static SNClass of(ResourceNode node) {
        SemanticNode fallback = null;
        SemanticNode inherited = null;
        for (Statement assoc : node.getAssociations()) {
            if (RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE.equals(assoc.getPredicate())) {
                // return directly
                return SNClass.from(assoc.getObject());
            }
            if (RDFS.SUB_CLASS_OF.equals(assoc.getPredicate())) {
                SNClass superClass = SNClass.from(assoc.getObject());
                inherited = getSchemaIdentifyingType(superClass, inherited);
            }
            if (RDF.TYPE.equals(assoc.getPredicate()) && !RBSystem.ENTITY.equals(assoc.getObject())) {
                fallback = assoc.getObject();
            }
        }

        if (inherited != null) {
            return SNClass.from(inherited);
        }
        return SNClass.from(fallback);
    }

    // ----------------------------------------------------

    private static SemanticNode getSchemaIdentifyingType(SNClass superClass, SemanticNode defaultClass) {
        if (superClass == null) {
            return defaultClass;
        }

        for (Statement assoc : superClass.getAssociations()) {
            if (RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE.equals(assoc.getPredicate())) {
                // return directly
                return SNClass.from(assoc.getObject());
            }
        }
        return defaultClass;
    }

}
