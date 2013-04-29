/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.entity.impl;

import java.util.Locale;
import java.util.Set;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.VisualizationInfo;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.PlainVisualizationInfo;

/**
 * <p>
 *  A field not declared by a schema's property delcaration.
 * </p>
 *
 * Created: Aug 8, 2011
 *
 * @author Ravi Knox
 */
public class UndeclaredRBField extends AbstractRBField {

	private final ResourceID predicate;

	//------------------------------------------------------------

	/**
	 * Constructor to be used when no property declaration exists.
	 * @param predicate - the predicate
	 * @param statements - statements of this field
	 */
	public UndeclaredRBField(final ResourceID predicate, final Set<Statement> statements) {
		super(statements);
		this.predicate = predicate;
	}

	//------------------------------------------------------------

	@Override
	public ResourceID getPredicate() {
		return predicate;
	}

	@Override
	public String getLabel(final Locale locale) {
		return ResourceLabelBuilder.getInstance().getFieldLabel(predicate, locale);
	}

	@Override
	public Datatype getDataType() {
		return Datatype.STRING;
	}

	@Override
	public Cardinality getCardinality() {
		return CardinalityBuilder.hasOptionalOneToMany();
	}

	@Override
	public boolean isResourceReference() {
		return false;
	}

	@Override
	public VisualizationInfo getVisualizationInfo() {
		return new PlainVisualizationInfo();
	}

	@Override
	public Constraint getConstraint() {
		return null;
	}

}
