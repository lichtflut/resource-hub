package de.lichtflut.rb.rest.api.common;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.common.SchemaIdentifyingType;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.ValueNode;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonUnwrapped;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * <p>
 *  Object representing an entities quick info.
 * </p>
 *
 * <p>
 *  Created Mar 15, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class QuickInfoRVO  {

    private final List<FieldRVO> details = new ArrayList<FieldRVO>();

    // ----------------------------------------------------

    @JsonProperty
    public List<FieldRVO> getDetails() {
        return details;
    }

    // ----------------------------------------------------

    public void add(ResourceID id, String label, String values) {
        add(new FieldRVO().setId(id.toURI()).setLabel(label).setValue(values));
    }

    public void add(FieldRVO field) {
        details.add(field);
    }

}
