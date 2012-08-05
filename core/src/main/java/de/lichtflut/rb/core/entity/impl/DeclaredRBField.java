/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity.impl;

import java.util.Locale;
import java.util.Set;

import de.lichtflut.rb.core.RB;
import org.apache.commons.lang3.Validate;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;

/**
 * <p>
 * Default implementation of {@link RBField}.
 * </p> 
 *
 * Created: Aug 8, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class DeclaredRBField extends AbstractRBField {
	
	private final PropertyDeclaration declaration;
	
	//------------------------------------------------------------

	/**
	 * Constructor.
	 * @param declaration - {@link PropertyDeclaration}. <code>null</code> if assertion is not known to Schema
	 * @param values - values of this field
	 */
	public DeclaredRBField(final PropertyDeclaration declaration, final Set<SemanticNode> values) {
		super(values, declaration.getDatatype().name().equals(Datatype.RESOURCE.name()));
		Validate.notNull(declaration, "The property declaration may never be null.");
		this.declaration = declaration;
	}
	
	//------------------------------------------------------------

	@Override
	public String getLabel(Locale locale) {
		return declaration.getFieldLabelDefinition().getLabel(locale);
	}
	
	@Override
	public ResourceID getPredicate() {
		return declaration.getPropertyDescriptor();
	}
	
	@Override
	public Datatype getDataType() {
		return declaration.getDatatype();
	}

	@Override
	public Cardinality getCardinality() {
		return declaration.getCardinality();
	}

	@Override
	public boolean isResourceReference() {
		return (declaration.getDatatype() == Datatype.RESOURCE);
	}

    @Override
    public boolean isEmbedded() {
        if (getConstraint() == null || getConstraint().isLiteral()) {
            return false;
        }
        // for testing always embed addresses
        return RB.ADDRESS.equals(getConstraint().getReference());
    }

    @Override
	public Constraint getConstraint() {
		return declaration.getConstraint();
	}
	
}
