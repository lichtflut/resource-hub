package de.lichtflut.rb.core.entity;

import de.lichtflut.rb.core.schema.model.Datatype;
import org.arastreju.sge.model.DetachedStatement;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.SemanticNode;

/**
 * <p>
 *  Represents one value of an RBField.
 * </p>
 *
 * <p>
 *  Created 21.12.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBFieldValue {

    private Statement stmt;

    private SemanticNode value;

    private RBField field;

    // ----------------------------------------------------

    public SemanticNode getValue() {
        return value;
    }

    public void setValue(Object newValue) {
        if (newValue == null) {
            value = null;
        } else if (field.getVisualizationInfo().isEmbedded() && newValue instanceof RBEntity) {
            final RBEntity ref = (RBEntity) newValue;
            value = ref.getID();
        } else if (field.isResourceReference()) {
            final ResourceID ref = (ResourceID) newValue;
            value = new SimpleResourceID(ref.getQualifiedName());
        } else {
            final ElementaryDataType datatype = Datatype.getCorrespondingArastrejuType(field.getDataType());
            value = new SNValue(datatype, newValue);
        }
    }

    public boolean isRemoved() {
        return value == null;
    }

}
