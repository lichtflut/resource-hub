package de.lichtflut.rb.core.schema.persistence;

import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.Datatype;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNBoolean;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.naming.QualifiedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  Semantic node representing a constraint.
 * </p>
 *
 * <p>
 * Created 31.08.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNConstraint extends ResourceView {

    public SNConstraint() {
        super();
    }

    public SNConstraint(QualifiedName qn) {
        super(qn);
    }

    public SNConstraint(ResourceNode resource) {
        super(resource);
    }

    // ----------------------------------------------------

    public String getName() {
        return stringValue(RBSchema.HAS_NAME);
    }

    public SNConstraint setName(String name) {
        if (name != null) {
            setValue(RBSchema.HAS_NAME, name);
        } else {
            removeValues(RBSchema.HAS_NAME);
        }
        return this;
    }

    // ----------------------------------------------------

    public String getLiteralConstraint() {
        return stringValue(RBSchema.HAS_LITERAL_CONSTRAINT);
    }

    public void setLiteralConstraint(final String pattern) {
        if ((pattern != null) && !pattern.isEmpty()) {
            setValue(RBSchema.HAS_LITERAL_CONSTRAINT, new SNText(pattern));
        } else {
            removeValues(RBSchema.HAS_LITERAL_CONSTRAINT);
        }
    }

    // ----------------------------------------------------

    public ResourceID getTypeConstraint() {
        SemanticNode referenceNode = SNOPS.singleObject(this, RBSchema.HAS_TYPE_CONSTRAINT);
        if (null == referenceNode) {
            return null;
        }
        return referenceNode.asResource();
    }

    public void setTypeConstraint(final ResourceID type) {
        setValue(RBSchema.HAS_TYPE_CONSTRAINT, type);
    }

    // ----------------------------------------------------

    public boolean isPublic() {
        return Boolean.TRUE.equals(booleanValue(RBSchema.IS_PUBLIC_CONSTRAINT));
    }

    public void setPublic(final boolean isPublic) {
        setValue(RBSchema.IS_PUBLIC_CONSTRAINT, new SNBoolean(isPublic));
    }

    // ----------------------------------------------------

    public List<Datatype> getApplicableDatatypes() {
        List<Datatype> datatypes = new ArrayList<Datatype>();
        for (SemanticNode snType : SNOPS.objects(this, RBSchema.HAS_DATATYPE)) {
            datatypes.add(Datatype.valueOf(snType.asValue().getStringValue()));
        }
        return datatypes;
    }

    public void setApplicableDatatypes(final List<Datatype> datatypes) {
        Collection<SNText> typeNodes = new ArrayList<SNText>();
        for (Datatype datatype : datatypes) {
            typeNodes.add(new SNText(datatype.name()));
        }
        SNOPS.assure(this, RBSchema.HAS_DATATYPE, typeNodes);
    }

    // ----------------------------------------------------


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Constraint '").append(getQualifiedName()).append("'");
        sb.append(" name: ").append(getName());
        sb.append(" is public: ").append(isPublic());
        return sb.toString();
    }
}
