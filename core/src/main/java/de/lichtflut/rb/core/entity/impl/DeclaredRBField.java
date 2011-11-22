/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity.impl;

import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
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
		super(values, declaration.getTypeDefinition().isResourceReference());
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
	public ElementaryDataType getDataType() {
		return declaration.getTypeDefinition().getElementaryDataType();
	}

	@Override
	public Cardinality getCardinality() {
		return declaration.getCardinality();
	}

	@Override
	public boolean isKnownToSchema() {
		return true;
	}

	@Override
	public boolean isResourceReference() {
		return declaration.getTypeDefinition().isResourceReference();
	}

	@Override
	public Set<Constraint> getConstraints() {
		return declaration.getTypeDefinition().getConstraints();
	}
	
}
