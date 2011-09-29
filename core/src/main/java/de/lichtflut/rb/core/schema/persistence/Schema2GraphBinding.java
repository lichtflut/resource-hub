/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import java.math.BigInteger;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNScalar;

import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Sep 29, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class Schema2GraphBinding {
	
	public ResourceSchema toModelObject(final SNResourceSchema snSchema) {
		if(snSchema == null) {
			return null;
		}
		final ResourceSchemaImpl schema = new ResourceSchemaImpl(snSchema);
		schema.setDescribedType(snSchema.getDescribedClass());
		for (SNPropertyDeclaration snDecl : snSchema.getPropertyDeclarations()){
			final PropertyDeclarationImpl decl = new PropertyDeclarationImpl(SNOPS.id(snDecl));
			decl.setPropertyType(snDecl.getPropertyDescriptor());
			decl.setCardinality(buildCardinality(snDecl));
			decl.setTypeDefinition(toModelObject(snDecl.getTypeDefinition()));
		}

		return schema;
	}
	
	public TypeDefinition toModelObject(final SNPropertyTypeDefinition snTypeDef) {
		if(snTypeDef == null) {
			return null;
		}
		final TypeDefinitionImpl typeDef = new TypeDefinitionImpl();
		return typeDef;
	}
	
	// -----------------------------------------------------
	
	private Cardinality buildCardinality(final SNPropertyDeclaration snDecl) {
		int min = snDecl.getMinOccurs().getIntegerValue().intValue();
		int max = snDecl.getMaxOccurs().getIntegerValue().intValue();
		if (max > 0) {
			return CardinalityBuilder.between(max, min);
		} else {
			return CardinalityBuilder.hasAtLeast(min);
		}
	}

}
