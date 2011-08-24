package de.lichtflut.rb.core.api.impl;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNValue;

import de.lichtflut.rb.core.api.INewRBEntityManagement;
import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.NewRBEntity;

/**
 *
 * @author Raphael Esterle
 *
 */

public class NewRBEntityManagement implements INewRBEntityManagement {

    private final ArastrejuGate gate;

    /**
     * <p>
     * This is the standard constructor.
     * </p>
     *
     * @param gate
     *            the ArastrejuGate instance which is necessary to store, update
     *            and resolve ResourceTypeInstances
     */
    public NewRBEntityManagement(final ArastrejuGate gate) {
        this.gate = gate;
    }

    @Override
    public NewRBEntity find(final ResourceID resourceID) {
        return find(resourceID, null);
    }

    /**
     *
     * @param resourceID /
     * @param schema /
     * @return /
     */
    public NewRBEntity find(final ResourceID resourceID,
            final ResourceSchema schema) {
        ModelingConversation mc = gate.startConversation();
        ResourceNode node = mc.findResource(resourceID.getQualifiedName());
        mc.close();
        return new NewRBEntity(node, schema);
    }

    @Override
    public IRBEntity store(final IRBEntity entity) {

        ModelingConversation mc = gate.startConversation();

        ResourceNode rnNew = entity.getID().asResource();

        // Association.create(rnNew, RDF.TYPE, entity.getID(), null);

        for (IRBField field : entity.getAllFields()) {
            for (Association assoc : rnNew
                    .getAssociations(new SimpleResourceID(field.getFieldName()))) {
                rnNew.revoke(assoc);
                System.out.println("0");
            }
            for (Object o : field.getFieldValues()) {
                Association.create(rnNew,
                        new SimpleResourceID(field.getFieldName()),
                        new SNValue(field.getDataType(), o), null);
                System.out.println("1");
            }

        }

        mc.attach(rnNew);

        return null;
    }
}
